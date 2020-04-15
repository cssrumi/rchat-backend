package com.github.cssrumi.rchat.message.model.command;

import com.github.cssrumi.rchat.message.dto.MessageDto;
import com.github.cssrumi.rchat.message.model.MessagePayload;
import java.time.OffsetDateTime;

public class MessageCommandFactory {

    private MessageCommandFactory() {
    }

    public static SendMessage sendMessage(MessageDto dto) {
        MessagePayload payload = new MessagePayload();
        payload.channel = dto.channel;
        payload.message = dto.message;
        payload.sendAt = dto.sendAt;
        payload.sendBy = dto.sendBy;

        return new SendMessage(OffsetDateTime.now(), payload);
    }
}
