package com.github.cssrumi.rchat.user.model.event;

import com.github.cssrumi.rchat.common.event.Event;
import com.github.cssrumi.rchat.user.model.payload.ModifyUserPayload;
import java.time.OffsetDateTime;

public class UserModified extends Event<ModifyUserPayload> {

    public UserModified(OffsetDateTime dateTime, ModifyUserPayload payload) {
        super(dateTime, payload, UserModified.class.getName());
    }

    @Override
    public String toString() {
        return "UserModified{" +
                "dateTime=" + dateTime +
                ", payload=" + payload +
                ", eventType='" + eventType + '\'' +
                '}';
    }
}
