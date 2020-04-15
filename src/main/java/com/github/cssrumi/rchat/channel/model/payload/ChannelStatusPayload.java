package com.github.cssrumi.rchat.channel.model.payload;

import com.github.cssrumi.rchat.channel.model.ChannelStatus;
import com.github.cssrumi.rchat.common.Payload;

public class ChannelStatusPayload implements Payload {

    public String channel;
    public ChannelStatus newStatus;

    public ChannelStatusPayload(String channel, ChannelStatus newStatus) {
        this.channel = channel;
        this.newStatus = newStatus;
    }
}
