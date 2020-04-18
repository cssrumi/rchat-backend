package com.github.cssrumi.rchat.security.process;

import com.github.cssrumi.rchat.common.RchatEventBus;
import com.github.cssrumi.rchat.common.Try;
import com.github.cssrumi.rchat.common.command.CommandHandler;
import com.github.cssrumi.rchat.common.event.EventFactory;
import com.github.cssrumi.rchat.security.model.UserSecurity;
import com.github.cssrumi.rchat.security.model.command.Login;
import com.github.cssrumi.rchat.security.model.command.Logout;
import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.mutiny.Uni;
import javax.inject.Inject;
import javax.inject.Singleton;

import static com.github.cssrumi.rchat.common.TopicConstants.LOGIN_USER_TOPIC;
import static com.github.cssrumi.rchat.common.TopicConstants.LOGOUT_USER_TOPIC;
import static com.github.cssrumi.rchat.common.TopicConstants.USER_LOGGED_IN_TOPIC;
import static com.github.cssrumi.rchat.common.TopicConstants.USER_LOGGED_OUT_TOPIC;

@Singleton
class SecurityCommandHandler extends CommandHandler<UserSecurity> {

    private final SecurityService securityService;
    private final UserSecurityQuery userSecurityQuery;
    private final EventFactory<UserSecurity> eventFactory;

    @Inject
    public SecurityCommandHandler(RchatEventBus eventBus, SecurityService securityService,
                                  UserSecurityQuery userSecurityQuery, EventFactory<UserSecurity> eventFactory) {
        super(eventBus);
        this.securityService = securityService;
        this.userSecurityQuery = userSecurityQuery;
        this.eventFactory = eventFactory;
    }

    @ConsumeEvent(LOGIN_USER_TOPIC)
    Uni<Try> loginUserHandler(Login command) {
        return Try.raw(securityService.authenticate(command.getPayload().username, command.getPayload().password)
                                      .onItem().produceUni(ignore -> sendEvent(USER_LOGGED_IN_TOPIC, command, eventFactory))
                                      .onItem().produceUni(ignore -> userSecurityQuery.getToken(command.getPayload().username)));
    }

    @ConsumeEvent(LOGOUT_USER_TOPIC)
    Uni<Try> logoutUserHandler(Logout command) {
        return Try.raw(sendEvent(USER_LOGGED_OUT_TOPIC, command, eventFactory));
    }
}
