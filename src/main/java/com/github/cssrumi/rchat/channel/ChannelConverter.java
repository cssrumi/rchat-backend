package com.github.cssrumi.rchat.channel;

import com.github.cssrumi.rchat.channel.dto.ChannelInfo;
import com.github.cssrumi.rchat.channel.model.Channel;

public class ChannelConverter {

    private ChannelConverter() {
    }

    public static ChannelInfo toChannelInfo(Channel channel) {
        ChannelInfo channelInfo = new ChannelInfo();
        channelInfo.createdAt = channel.createdAt;
        channelInfo.name = channel.name;
        channelInfo.status = channel.status;

        return channelInfo;
    }
}
