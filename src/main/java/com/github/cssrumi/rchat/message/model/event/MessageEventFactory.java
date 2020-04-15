package com.github.cssrumi.rchat.message.model.event;

import com.github.cssrumi.rchat.common.Command;
import com.github.cssrumi.rchat.common.Event;
import com.github.cssrumi.rchat.common.EventFactory;
import com.github.cssrumi.rchat.message.model.Message;
import com.github.cssrumi.rchat.message.model.command.SendMessage;
import java.time.OffsetDateTime;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MessageEventFactory implements EventFactory<Message> {

    @Override
    public <C extends Command> Event from(C command) {
        if (command instanceof SendMessage) {
            return messageSent((SendMessage) command);
        }

        throw new RuntimeException("Command not found: " + command);
    }

    private MessageSent messageSent(SendMessage command) {
        return new MessageSent(OffsetDateTime.now(), command.getPayload());
    }
}
