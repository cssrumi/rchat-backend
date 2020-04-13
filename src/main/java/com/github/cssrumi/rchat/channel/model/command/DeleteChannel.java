package com.github.cssrumi.rchat.channel.model.command;

import com.github.cssrumi.rchat.channel.model.payload.ChannelPayload;
import com.github.cssrumi.rchat.model.Command;
import java.time.OffsetDateTime;

public class DeleteChannel extends Command<ChannelPayload> {
    public DeleteChannel(OffsetDateTime dateTime, ChannelPayload payload) {
        super(dateTime, payload, DeleteChannel.class.getName());
    }
}
