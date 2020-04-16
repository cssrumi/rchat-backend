package com.github.cssrumi.rchat.security.model.command;

import com.github.cssrumi.rchat.common.Command;
import com.github.cssrumi.rchat.security.model.payload.LogoutPayload;
import java.time.OffsetDateTime;

public class Logout extends Command<LogoutPayload> {

    public Logout(OffsetDateTime dateTime, LogoutPayload payload) {
        super(dateTime, payload, Logout.class.getName());
    }

    @Override
    public String toString() {
        return "Logout{" +
                "dateTime=" + dateTime +
                ", payload=" + payload +
                ", eventType='" + eventType + '\'' +
                '}';
    }
}
