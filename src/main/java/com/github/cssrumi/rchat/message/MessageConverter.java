package com.github.cssrumi.rchat.message;

import com.github.cssrumi.rchat.message.dto.MessageDto;
import com.github.cssrumi.rchat.message.model.Message;

public class MessageConverter {

    private MessageConverter() {
    }

    public static MessageDto dto(Message message) {
        MessageDto dto = new MessageDto();
        dto.message = message.message;
        dto.channel = message.channel;
        dto.sendAt = message.sentAt;
        dto.sendBy = message.sentBy;

        return dto;
    }
}
