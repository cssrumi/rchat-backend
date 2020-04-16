package com.github.cssrumi.rchat.security.process;

import com.github.cssrumi.rchat.common.Command;
import com.github.cssrumi.rchat.common.Event;
import com.github.cssrumi.rchat.common.EventFactory;
import com.github.cssrumi.rchat.security.model.command.Login;
import com.github.cssrumi.rchat.security.model.command.Logout;
import com.github.cssrumi.rchat.security.model.event.LoggedIn;
import com.github.cssrumi.rchat.security.model.event.LoggedOut;
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
