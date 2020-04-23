package com.github.cssrumi.rchat.channel.model.command;

import com.github.cssrumi.rchat.channel.dto.ChannelRequest;
import com.github.cssrumi.rchat.channel.model.ChannelStatus;
import com.github.cssrumi.rchat.channel.model.payload.ChannelPayload;
import com.github.cssrumi.rchat.channel.model.payload.ChannelStatusPayload;
import java.time.OffsetDateTime;

public class ChannelCommandFactory {

    private ChannelCommandFactory() {
    }

    public static CreateChannel createChannel(ChannelRequest dto) {
        ChannelPayload payload = new ChannelPayload(dto.name);
        return new CreateChannel(OffsetDateTime.now(), payload);
    }

    public static DeleteChannel deleteChannel(String channel) {
        ChannelPayload payload = new ChannelPayload(channel);
        return new DeleteChannel(OffsetDateTime.now(), payload);
    }

    public static ChangeChannelStatus changeChannelStatus(String channel, ChannelStatus newStatus) {
        ChannelStatusPayload payload = new ChannelStatusPayload(channel, newStatus);
        return new ChangeChannelStatus(OffsetDateTime.now(), payload);
    }
}
