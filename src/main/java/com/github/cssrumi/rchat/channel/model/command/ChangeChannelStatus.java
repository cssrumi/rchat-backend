package com.github.cssrumi.rchat.channel.model.command;

import com.github.cssrumi.rchat.channel.model.payload.ChannelStatusPayload;
import com.github.cssrumi.rchat.common.command.Command;
import java.time.OffsetDateTime;

public class ChangeChannelStatus extends Command<ChannelStatusPayload> {

    public ChangeChannelStatus(OffsetDateTime dateTime, ChannelStatusPayload payload) {
        super(dateTime, payload, ChangeChannelStatus.class.getName());
    }
}
