package com.github.cssrumi.rchat.user.model.event;

import com.github.cssrumi.rchat.model.Command;
import com.github.cssrumi.rchat.model.Event;
import com.github.cssrumi.rchat.user.model.command.DeleteUser;
import com.github.cssrumi.rchat.user.model.command.RegisterUser;
import java.time.OffsetDateTime;

public class UserEventFactory {

    private UserEventFactory() {
    }

    public static UserCreated created(RegisterUser command) {
        return new UserCreated(OffsetDateTime.now(), command.getPayload());
    }

    public static UserDeleted deleted(DeleteUser command) {
        return new UserDeleted(OffsetDateTime.now(), command.getPayload());
    }

    public static <C extends Command> Event from(C command) {
        if (command instanceof RegisterUser) return created((RegisterUser) command);
        if (command instanceof DeleteUser) return deleted((DeleteUser) command);

        throw new RuntimeException("Command not found for: " + command);
    }
}
