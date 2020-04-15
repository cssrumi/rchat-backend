package com.github.cssrumi.rchat.message.process;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.mutiny.core.eventbus.EventBus;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
class MessageEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageEventHandler.class);
    private final EventBus eventBus;

    @Inject
    MessageEventHandler(EventBus eventBus) {
        this.eventBus = eventBus;
    }
}
