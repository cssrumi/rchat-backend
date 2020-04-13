package com.github.cssrumi.rchat.channel.handler;

import com.github.cssrumi.rchat.channel.model.event.ChannelCreated;
import com.github.cssrumi.rchat.channel.model.event.ChannelDeleted;
import com.github.cssrumi.rchat.channel.model.event.ChannelStatusChanged;
import io.quarkus.vertx.ConsumeEvent;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.mutiny.core.eventbus.EventBus;
import javax.inject.Inject;

import static com.github.cssrumi.rchat.model.TopicConstants.CHANNEL_CREATED_TOPIC;
import static com.github.cssrumi.rchat.model.TopicConstants.CHANNEL_DELETED_TOPIC;
import static com.github.cssrumi.rchat.model.TopicConstants.CHANNEL_STATUS_CHANGED_TOPIC;

public class ChannelEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelEventHandler.class);
    private final EventBus eventBus;

    @Inject
    public ChannelEventHandler(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @ConsumeEvent(CHANNEL_CREATED_TOPIC)
    void consumeChannelCreated(ChannelCreated event) {
        LOGGER.info("New channel created. Event: " + event);
    }

    @ConsumeEvent(CHANNEL_DELETED_TOPIC)
    void consumeChannelDeleted(ChannelDeleted event) {
        LOGGER.info("Channel deleted. Event: " + event);
    }

    @ConsumeEvent(CHANNEL_STATUS_CHANGED_TOPIC)
    void consumeChannelStatusChanged(ChannelStatusChanged event) {
        LOGGER.info("Channel deleted. Event: " + event);
    }
}
