package com.github.cssrumi.rchat.message.model.command;

import com.github.cssrumi.rchat.message.dto.MessageRequest;
import com.github.cssrumi.rchat.message.model.MessagePayload;
import java.time.OffsetDateTime;

public class MessageCommandFactory {

    private MessageCommandFactory() {
    }

    public static SendMessage sendMessage(MessageRequest dto, String channel, String username) {
        MessagePayload payload = new MessagePayload();
        OffsetDateTime now = OffsetDateTime.now();
        payload.sendBy = username;
        payload.channel = channel;
        payload.message = dto.message;
        payload.sendAt = now.toEpochSecond();

        return new SendMessage(now, payload);
    }
}
