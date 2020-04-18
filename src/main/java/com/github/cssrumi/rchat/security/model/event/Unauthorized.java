package com.github.cssrumi.rchat.security.model.event;

import com.github.cssrumi.rchat.common.event.Event;
import com.github.cssrumi.rchat.security.model.payload.UnauthorizedPayload;
import java.time.OffsetDateTime;

public class Unauthorized extends Event<UnauthorizedPayload> {

    public Unauthorized(OffsetDateTime dateTime, UnauthorizedPayload payload) {
        super(dateTime, payload, Unauthorized.class.getName());
    }

    @Override
    public String toString() {
        return "Unauthorized{" +
                "dateTime=" + dateTime +
                ", payload=" + payload +
                ", eventType='" + eventType + '\'' +
                '}';
    }
}
