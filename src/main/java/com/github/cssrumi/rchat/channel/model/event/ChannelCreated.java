package com.github.cssrumi.rchat.channel.model.event;

import com.github.cssrumi.rchat.channel.model.payload.ChannelPayload;
import com.github.cssrumi.rchat.common.Event;
import java.time.OffsetDateTime;

public class ChannelCreated extends Event<ChannelPayload> {

    public ChannelCreated(OffsetDateTime dateTime, ChannelPayload payload) {
        super(dateTime, payload, ChannelCreated.class.getName());
    }
}
