package com.github.cssrumi.rchat.message.model.event;

import com.github.cssrumi.rchat.common.Event;
import com.github.cssrumi.rchat.message.model.MessagePayload;
import java.time.OffsetDateTime;

public class MessageSent extends Event<MessagePayload> {

    public MessageSent(OffsetDateTime dateTime, MessagePayload payload) {
        super(dateTime, payload, MessageSent.class.getName());
    }

    @Override
    public String toString() {
        return "MessageSent{" +
                "dateTime=" + dateTime +
                ", payload=" + payload +
                ", eventType='" + eventType + '\'' +
                '}';
    }
}
