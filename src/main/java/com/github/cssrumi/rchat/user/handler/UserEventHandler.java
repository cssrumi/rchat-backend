package com.github.cssrumi.rchat.user.handler;

import com.github.cssrumi.rchat.user.model.event.UserCreated;
import com.github.cssrumi.rchat.user.model.event.UserDeleted;
import io.quarkus.vertx.ConsumeEvent;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.mutiny.core.eventbus.EventBus;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import static com.github.cssrumi.rchat.user.model.UserConstants.USER_CREATED_TOPIC;
import static com.github.cssrumi.rchat.user.model.UserConstants.USER_DELETED_TOPIC;

@ApplicationScoped
public class UserEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserEventHandler.class);
    private final EventBus eventBus;

    @Inject
    public UserEventHandler(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @ConsumeEvent(USER_CREATED_TOPIC)
    void consumeUserCreated(UserCreated event) {
        LOGGER.info("New user created. Event: " + event);
    }

    @ConsumeEvent(USER_DELETED_TOPIC)
    void consumeUserDeleted(UserDeleted event) {
        LOGGER.info("User deleted. Event: " + event);
    }
}
