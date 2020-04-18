package com.github.cssrumi.rchat.channel.model.event;

import com.github.cssrumi.rchat.channel.model.payload.ChannelStatusPayload;
import com.github.cssrumi.rchat.common.event.Event;
import java.time.OffsetDateTime;

public class ChannelStatusChanged extends Event<ChannelStatusPayload> {

    public ChannelStatusChanged(OffsetDateTime dateTime, ChannelStatusPayload payload) {
        super(dateTime, payload, ChannelStatusChanged.class.getName());
    }
}
