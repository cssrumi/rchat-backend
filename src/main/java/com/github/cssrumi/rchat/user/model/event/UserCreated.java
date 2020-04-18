package com.github.cssrumi.rchat.user.model.event;

import com.github.cssrumi.rchat.common.event.Event;
import com.github.cssrumi.rchat.user.model.payload.RegisterUserPayload;
import java.time.OffsetDateTime;

public class UserCreated extends Event<RegisterUserPayload> {

    public UserCreated(OffsetDateTime dateTime, RegisterUserPayload payload) {
        super(dateTime, payload, UserCreated.class.getName());
    }

    @Override
    public String toString() {
        return "UserCreated{" +
                "dateTime=" + dateTime +
                ", payload=" + payload +
                ", eventType='" + eventType + '\'' +
                '}';
    }
}
