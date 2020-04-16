package com.github.cssrumi.rchat.user.model.event;

import com.github.cssrumi.rchat.common.Event;
import com.github.cssrumi.rchat.user.model.payload.DeleteUserPayload;
import java.time.OffsetDateTime;

public class UserDeleted extends Event<DeleteUserPayload> {

    public UserDeleted(OffsetDateTime dateTime, DeleteUserPayload payload) {
        super(dateTime, payload, UserDeleted.class.getName());
    }

    @Override
    public String toString() {
        return "UserDeleted{" +
                "dateTime=" + dateTime +
                ", payload=" + payload +
                ", eventType='" + eventType + '\'' +
                '}';
    }
}
