package com.github.cssrumi.rchat.channel.model.command;

import com.github.cssrumi.rchat.channel.model.payload.ChannelPayload;
import com.github.cssrumi.rchat.model.Command;
import java.time.OffsetDateTime;

public class CreateChannel extends Command<ChannelPayload> {

    public CreateChannel(OffsetDateTime dateTime, ChannelPayload payload) {
        super(dateTime, payload, CreateChannel.class.getName());
    }
}
