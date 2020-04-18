package com.github.cssrumi.rchat.channel.process;

import com.github.cssrumi.rchat.channel.model.Channel;
import com.github.cssrumi.rchat.channel.model.ChannelStatus;
import com.github.cssrumi.rchat.channel.model.command.ChangeChannelStatus;
import com.github.cssrumi.rchat.channel.model.command.CreateChannel;
import com.github.cssrumi.rchat.channel.model.command.DeleteChannel;
import com.github.cssrumi.rchat.channel.model.payload.ChannelStatusPayload;
import com.github.cssrumi.rchat.common.RchatEventBus;
import com.github.cssrumi.rchat.common.Try;
import com.github.cssrumi.rchat.common.command.CommandHandler;
import com.github.cssrumi.rchat.common.event.EventFactory;
import com.github.cssrumi.rchat.common.exception.ResourceAlreadyExistsException;
import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.mutiny.Uni;
import java.time.OffsetDateTime;
import javax.inject.Inject;
import javax.inject.Singleton;

import static com.github.cssrumi.rchat.common.TopicConstants.*;

@Singleton
class ChannelCommandHandler extends CommandHandler<Channel> {

    private final EventFactory<Channel> eventFactory;
    private final ChannelRepository channelRepository;

    @Inject
    ChannelCommandHandler(RchatEventBus eventBus, EventFactory<Channel> eventFactory, ChannelRepository channelRepository) {
        super(eventBus);
        this.eventFactory = eventFactory;
        this.channelRepository = channelRepository;
    }

    @ConsumeEvent(CREATE_CHANNEL_TOPIC)
    Uni<Try> createChannelHandler(CreateChannel command) {
        final String name = command.getPayload().name;
        return Try.raw(channelRepository.isChannelExist(name)
                                         .map(result -> createChannel(result, name))
                                         .onItem().produceUni(channel -> channelRepository.persist(channel))
                                         .onItem().produceUni(result -> sendEvent(CHANNEL_CREATED_TOPIC, command, eventFactory)));
    }

    @ConsumeEvent(DELETE_CHANNEL_TOPIC)
    Uni<Try> deleteChannelHandler(DeleteChannel command) {
        return Try.raw(channelRepository.findByName(command.getPayload().name)
                                         .onItem().produceUni(channel -> channelRepository.delete(channel))
                                         .onItem().produceUni(result -> sendEvent(CHANNEL_DELETED_TOPIC, command, eventFactory)));
    }

    @ConsumeEvent(CHANGE_CHANNEL_STATUS_TOPIC)
    Uni<Try> changeChannelStatusHandler(ChangeChannelStatus command) {
        final ChannelStatusPayload payload = command.getPayload();
        return Try.raw(channelRepository.findByName(payload.channel)
                                         .onItem()
                                         .produceUni(channel -> {
                                             channel.status = payload.newStatus;
                                             return channelRepository.update(channel);
                                         })
                                         .onItem().produceUni(result -> sendEvent(CHANNEL_STATUS_CHANGED_TOPIC, command, eventFactory)));
    }

    Channel createChannel(Boolean result, String name) {
        if (result) {
            throw ResourceAlreadyExistsException.fromResource(Channel.class);
        }

        Channel channel = new Channel();
        channel.createdAt = OffsetDateTime.now().toEpochSecond();
        channel.status = ChannelStatus.INACTIVE;
        channel.name = name;

        return channel;
    }
}
