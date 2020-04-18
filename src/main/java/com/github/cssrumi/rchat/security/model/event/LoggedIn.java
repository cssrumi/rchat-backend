package com.github.cssrumi.rchat.security.model.event;

import com.github.cssrumi.rchat.common.event.Event;
import com.github.cssrumi.rchat.security.model.payload.LoginPayload;
import java.time.OffsetDateTime;

public class LoggedIn extends Event<LoginPayload> {

    public LoggedIn(OffsetDateTime dateTime, LoginPayload payload) {
        super(dateTime, payload, LoggedIn.class.getName());
    }

    @Override
    public String toString() {
        return "LoggedIn{" +
                "dateTime=" + dateTime +
                ", payload=" + payload +
                ", eventType='" + eventType + '\'' +
                '}';
    }
}
