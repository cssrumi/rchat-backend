package com.github.cssrumi.rchat.message.model;

import com.github.cssrumi.rchat.common.Payload;

public class MessagePayload implements Payload {

    public String sendBy;
    public Long sendAt;
    public String message;
    public String channel;

    @Override
    public String toString() {
        return "MessagePayload{" +
                "sendBy='" + sendBy + '\'' +
                ", sendAt=" + sendAt +
                ", message='" + message + '\'' +
                ", channel='" + channel + '\'' +
                '}';
    }
}
