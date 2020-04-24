package com.github.cssrumi.rchat.message;

import com.github.cssrumi.rchat.message.dto.MessageDto;
import com.github.cssrumi.rchat.message.model.Message;
import java.util.UUID;

public class MessageConverter {

    private MessageConverter() {
    }

    public static MessageDto dto(Message message) {
        MessageDto dto = new MessageDto();
        dto.id = message.id != null ? message.id.toString() : UUID.randomUUID().toString();
        dto.message = message.message;
        dto.sendAt = message.sentAt;
        dto.sendBy = message.sentBy;

        return dto;
    }
}
