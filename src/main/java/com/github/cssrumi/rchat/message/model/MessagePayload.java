package com.github.cssrumi.rchat.message.model;

import com.github.cssrumi.rchat.model.Payload;

public class MessagePayload implements Payload {

    public String sendBy;
    public Long sendAt;
    public String message;
    public String channel;

}
