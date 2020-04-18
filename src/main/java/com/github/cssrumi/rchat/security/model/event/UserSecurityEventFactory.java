package com.github.cssrumi.rchat.security.model.event;

import com.github.cssrumi.rchat.common.command.Command;
import com.github.cssrumi.rchat.common.event.Event;
import com.github.cssrumi.rchat.common.event.EventFactory;
import com.github.cssrumi.rchat.security.model.UserSecurity;
import com.github.cssrumi.rchat.security.model.command.Login;
import com.github.cssrumi.rchat.security.model.command.Logout;
import java.time.OffsetDateTime;
import javax.inject.Singleton;

@Singleton
public class UserSecurityEventFactory implements EventFactory<UserSecurity> {

    @Override
    public <C extends Command> Event from(C command) {
        if (command instanceof Login) {
            return login((Login) command);
        }

        if (command instanceof Logout) {
            return deleted((Logout) command);
        }

        throw new RuntimeException("Command not found: " + command);
    }

    private LoggedIn login(Login command) {
        return new LoggedIn(OffsetDateTime.now(), command.getPayload());
    }

    private LoggedOut deleted(Logout command) {
        return new LoggedOut(OffsetDateTime.now(), command.getPayload());
    }
}
