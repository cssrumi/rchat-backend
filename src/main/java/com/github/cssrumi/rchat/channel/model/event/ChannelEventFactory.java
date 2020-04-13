package com.github.cssrumi.rchat.channel.model.event;

import com.github.cssrumi.rchat.channel.model.Channel;
import com.github.cssrumi.rchat.channel.model.command.ChangeChannelStatus;
import com.github.cssrumi.rchat.channel.model.command.CreateChannel;
import com.github.cssrumi.rchat.channel.model.command.DeleteChannel;
import com.github.cssrumi.rchat.model.Command;
import com.github.cssrumi.rchat.model.Event;
import com.github.cssrumi.rchat.model.EventFactory;
import java.time.OffsetDateTime;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ChannelEventFactory implements EventFactory<Channel> {

    @Override
    public Event from(Command command) {
        if (command instanceof CreateChannel) {
            return channelCreated((CreateChannel) command);
        }

        if (command instanceof DeleteChannel) {
            return channelDeleted((DeleteChannel) command);
        }

        if (command instanceof ChangeChannelStatus) {
            return channelStatusChanged((ChangeChannelStatus) command);
        }

        return null;
    }

    private ChannelCreated channelCreated(CreateChannel command) {
        return new ChannelCreated(OffsetDateTime.now(), command.getPayload());
    }

    private ChannelDeleted channelDeleted(DeleteChannel command) {
        return new ChannelDeleted(OffsetDateTime.now(), command.getPayload());
    }

    private ChannelStatusChanged channelStatusChanged(ChangeChannelStatus command) {
        return new ChannelStatusChanged(OffsetDateTime.now(), command.getPayload());
    }
}