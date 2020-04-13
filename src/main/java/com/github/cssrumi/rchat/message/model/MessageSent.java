package com.github.cssrumi.rchat.message.model;

import com.github.cssrumi.rchat.model.Event;
import java.time.OffsetDateTime;

public class MessageSent extends Event<MessagePayload> {

    public MessageSent(OffsetDateTime dateTime, MessagePayload payload) {
        super(dateTime, payload, MessageSent.class.getName());
    }
}
