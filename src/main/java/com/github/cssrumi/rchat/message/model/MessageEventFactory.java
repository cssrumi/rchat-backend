package com.github.cssrumi.rchat.message.model;

import com.github.cssrumi.rchat.model.Command;
import com.github.cssrumi.rchat.model.Event;
import com.github.cssrumi.rchat.model.EventFactory;
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
