package com.github.cssrumi.rchat.message.model.command;

import com.github.cssrumi.rchat.common.Command;
import com.github.cssrumi.rchat.message.model.MessagePayload;
import java.time.OffsetDateTime;

public class SendMessage extends Command<MessagePayload> {

    public SendMessage(OffsetDateTime dateTime, MessagePayload payload) {
        super(dateTime, payload, SendMessage.class.getName());
    }
}
