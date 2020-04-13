package com.github.cssrumi.rchat.channel.model.event;

import com.github.cssrumi.rchat.channel.model.payload.ChannelPayload;
import com.github.cssrumi.rchat.model.Event;
import java.time.OffsetDateTime;

public class ChannelDeleted extends Event<ChannelPayload> {

    public ChannelDeleted(OffsetDateTime dateTime, ChannelPayload payload) {
        super(dateTime, payload, ChannelDeleted.class.getName());
    }
}
