package com.github.cssrumi.rchat.channel.process;

import com.github.cssrumi.rchat.channel.model.ChannelStatus;
import com.github.cssrumi.rchat.channel.model.command.ChangeChannelStatus;
import com.github.cssrumi.rchat.channel.model.event.ChannelCreated;
import com.github.cssrumi.rchat.channel.model.event.ChannelDeleted;
import com.github.cssrumi.rchat.channel.model.event.ChannelStatusChanged;
import com.github.cssrumi.rchat.channel.model.payload.ChannelStatusPayload;
import com.github.cssrumi.rchat.common.RchatEventBus;
import com.github.cssrumi.rchat.message.model.event.MessageSent;
import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.mutiny.Uni;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import java.time.OffsetDateTime;
import java.util.concurrent.CompletableFuture;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import static com.github.cssrumi.rchat.common.TopicConstants.*;

@ApplicationScoped
class ChannelEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelEventHandler.class);
    private final RchatEventBus eventBus;
    private final ChannelQuery channelQuery;
    private final ChannelPublisher channelPublisher;

    @Inject
    public ChannelEventHandler(RchatEventBus eventBus, ChannelQuery channelQuery,
                               ChannelPublisher channelPublisher) {
        this.eventBus = eventBus;
        this.channelQuery = channelQuery;
        this.channelPublisher = channelPublisher;
    }

    @ConsumeEvent(MESSAGE_SENT_TOPIC)
    Uni<Void> activateChannel(MessageSent event) {
        String channel = event.getPayload().channel;

        return channelQuery.isChannelActivated(channel)
                           .onItem()
                           .produceCompletionStage(isActive ->
                                   CompletableFuture.runAsync(() ->
                                           notifyIfChannelIsActive(isActive, channel)));
    }

    private void notifyIfChannelIsActive(Boolean isActive, String channel) {
        if (isActive) {
            return;
        }
        ChannelStatusPayload payload = new ChannelStatusPayload(channel, ChannelStatus.ACTIVE);
        ChangeChannelStatus command = new ChangeChannelStatus(OffsetDateTime.now(), payload);
        eventBus.sendAndForget(CHANGE_CHANNEL_STATUS_TOPIC, command);
        LOGGER.info(String.format("Channel %s was deactivated. Activate command was sent.", channel));
    }

    @ConsumeEvent(CHANNEL_CREATED_TOPIC)
    void channelCreatedHandler(ChannelCreated event) {
        channelPublisher.emmit(event);
        LOGGER.info(String.format("ChannelCreated event emitted for %s", event.getPayload().name));
    }

    @ConsumeEvent(CHANNEL_DELETED_TOPIC)
    void channelDeletedHandler(ChannelDeleted event) {
        String channel = event.getPayload().name;
        channelQuery.invalidateChannelActivated(channel);
        channelQuery.invalidateChannelExists(channel);
        channelPublisher.emmit(event);
        LOGGER.info(String.format("Channel %s caches have been invalidated after ChannelDeleted event.", channel));
    }

    @ConsumeEvent(CHANNEL_STATUS_CHANGED_TOPIC)
    void channelStatusChangedHandler(ChannelStatusChanged event) {
        String channel = event.getPayload().channel;
        channelQuery.isChannelActivated(channel);
        channelPublisher.emmit(event);
        LOGGER.info(String.format("Channel %s activity cache has been invalidated after ChannelStatusChanged event.", channel));
    }
}
