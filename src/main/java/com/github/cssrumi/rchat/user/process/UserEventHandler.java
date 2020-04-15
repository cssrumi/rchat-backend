package com.github.cssrumi.rchat.user.process;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.mutiny.core.eventbus.EventBus;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
class UserEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserEventHandler.class);
    private final EventBus eventBus;

    @Inject
    UserEventHandler(EventBus eventBus) {
        this.eventBus = eventBus;
    }
}
