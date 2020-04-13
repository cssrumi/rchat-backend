package com.github.cssrumi.rchat.message.model;

import com.github.cssrumi.rchat.model.Command;
import java.time.OffsetDateTime;

public class SendMessage extends Command<MessagePayload> {

    public SendMessage(OffsetDateTime dateTime, MessagePayload payload) {
        super(dateTime, payload, SendMessage.class.getName());
    }
}
