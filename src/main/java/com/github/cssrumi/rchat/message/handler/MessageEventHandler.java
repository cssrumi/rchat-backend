package com.github.cssrumi.rchat.message.handler;

import com.github.cssrumi.rchat.message.model.MessageSent;
import io.quarkus.vertx.ConsumeEvent;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.mutiny.core.eventbus.EventBus;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import static com.github.cssrumi.rchat.model.TopicConstants.MESSAGE_SENT_TOPIC;

@ApplicationScoped
public class MessageEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageEventHandler.class);
    private final EventBus eventBus;

    @Inject
    public MessageEventHandler(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @ConsumeEvent(MESSAGE_SENT_TOPIC)
    void consumeMessageSent(MessageSent event) {
        LOGGER.info("New message sent: " + event);
    }
}
