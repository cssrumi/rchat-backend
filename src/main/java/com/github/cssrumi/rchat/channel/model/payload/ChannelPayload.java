package com.github.cssrumi.rchat.channel.model.payload;

import com.github.cssrumi.rchat.common.Payload;

public class ChannelPayload implements Payload {

    public String name;

    public ChannelPayload(String name) {
        this.name = name;
    }
}
