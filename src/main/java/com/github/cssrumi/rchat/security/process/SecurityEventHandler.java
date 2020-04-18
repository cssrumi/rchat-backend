package com.github.cssrumi.rchat.security.process;

import com.github.cssrumi.rchat.common.RchatEventBus;
import com.github.cssrumi.rchat.user.model.event.UserDeleted;
import com.github.cssrumi.rchat.user.model.event.UserModified;
import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.mutiny.Uni;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.github.cssrumi.rchat.common.TopicConstants.USER_DELETED_TOPIC;
import static com.github.cssrumi.rchat.common.TopicConstants.USER_MODIFIED_TOPIC;

@ApplicationScoped
class SecurityEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityEventHandler.class);
    private final RchatEventBus eventBus;
    private final UserSecurityQuery userSecurityQuery;

    @Inject
    SecurityEventHandler(RchatEventBus eventBus, UserSecurityQuery userSecurityQuery) {
        this.eventBus = eventBus;
        this.userSecurityQuery = userSecurityQuery;
    }

    @ConsumeEvent(USER_MODIFIED_TOPIC)
    Uni<Void> userModifiedHandler(UserModified event) {
        return Uni.createFrom().item(() -> {
            String username = event.getPayload().username;
            userSecurityQuery.invalidatePasswordCache(username);
            userSecurityQuery.invalidateTokenCache(username);
            LOGGER.info(String.format("Password and Token caches for %s user have been invalidated.", username));

            return null;
        });
    }

    @ConsumeEvent(USER_DELETED_TOPIC)
    Uni<Void> userDeletedHandler(UserDeleted event) {
        return Uni.createFrom().item(() -> {
            String username = event.getPayload().username;
            userSecurityQuery.invalidatePasswordCache(username);
            userSecurityQuery.invalidateTokenCache(username);
            LOGGER.info(String.format("Password and Token caches for %s user have been invalidated.", username));

            return null;
        });
    }
}
