package com.github.cssrumi.rchat.security.process;

import com.github.cssrumi.rchat.security.model.event.Unauthorized;
import com.github.cssrumi.rchat.security.model.payload.UnauthorizedPayload;
import com.github.cssrumi.rchat.user.model.payload.RegisterUserPayload;
import io.quarkus.security.UnauthorizedException;
import io.smallrye.mutiny.Uni;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.mutiny.core.eventbus.EventBus;
import java.time.OffsetDateTime;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import static com.github.cssrumi.rchat.common.TopicConstants.UNAUTHORIZED_TOPIC;

@ApplicationScoped
public class SecurityService {

    static final int TOKEN_SIZE = 32;
    static final String AUTHORIZATION_HEADER = "Authorization";
    static final String BEARER = "Bearer";
    static final String INVALID_AUTHORIZATION_TYPE_MESSAGE = "Invalid authorization type...";

    private final EventBus eventBus;
    private final UserSecurityQuery userSecurityQuery;

    @Inject
    public SecurityService(EventBus eventBus, UserSecurityQuery userSecurityQuery) {
        this.eventBus = eventBus;
        this.userSecurityQuery = userSecurityQuery;
    }

    public Uni<Void> generateUserSecurityAndPersist(RegisterUserPayload payload) {
        return Uni.createFrom()
                  .item(createUserSecurity(payload))
                  .onItem()
                  .produceUni(userSecurity -> userSecurity.persist());
    }

    public Uni<Void> authorize(HttpServerRequest request, String username) {
        return Uni.createFrom()
                  .item(getToken(request))
                  .onItem()
                  .produceUni(token -> verifyToken(username, token));
    }

    Uni<Void> authenticate(String username, String password) {
        return verifyPassword(username, password);
    }

    String generateToken() {
        return RandomStringUtils.randomAlphanumeric(TOKEN_SIZE);
    }

    Uni<String> generateTokenAsync() {
        return Uni.createFrom().item(generateToken());
    }

    private UserSecurity createUserSecurity(RegisterUserPayload payload) {
        UserSecurity userSecurity = new UserSecurity();
        userSecurity.username = payload.username;
        userSecurity.password = payload.password;
        userSecurity.token = generateToken();
        userSecurity.tokenCreationTimestamp = OffsetDateTime.now().toEpochSecond();

        return userSecurity;
    }

    private String getToken(HttpServerRequest request) {
        String token = request.getHeader(AUTHORIZATION_HEADER);
        if (!token.startsWith(BEARER)) {
            unauthorized(INVALID_AUTHORIZATION_TYPE_MESSAGE);
        }

        return StringUtils.stripStart(token, BEARER).strip();
    }

    private Uni<Void> verifyToken(String username, String token) {
        return userSecurityQuery.getToken(username)
                                .onItem()
                                .apply(userToken -> {
                                    if (!token.equals(userToken)) {
                                        throw new UnauthorizedException();
                                    }

                                    return null;
                                });
    }

    private Uni<Void> verifyPassword(String username, String password) {
        return userSecurityQuery.getPassword(username)
                                .onItem()
                                .apply(userPassword -> {
                                    if (!password.equals(userPassword)) {
                                        throw new UnauthorizedException();
                                    }

                                    return null;
                                });
    }

    private void unauthorized(String message) {
        UnauthorizedPayload payload = new UnauthorizedPayload(message);
        Unauthorized event = new Unauthorized(OffsetDateTime.now(), payload);
        eventBus.publish(UNAUTHORIZED_TOPIC, event);

        throw new UnauthorizedException();
    }
}
