package com.github.cssrumi.rchat.security.model.event;

import com.github.cssrumi.rchat.common.Event;
import com.github.cssrumi.rchat.security.model.payload.LoginPayload;
import com.github.cssrumi.rchat.security.model.payload.LogoutPayload;
import java.time.OffsetDateTime;

public class LoggedOut extends Event<LogoutPayload> {

    public LoggedOut(OffsetDateTime dateTime, LogoutPayload payload) {
        super(dateTime, payload, LoggedOut.class.getName());
    }

    @Override
    public String toString() {
        return "LoggedOut{" +
                "dateTime=" + dateTime +
                ", payload=" + payload +
                ", eventType='" + eventType + '\'' +
                '}';
    }
}
