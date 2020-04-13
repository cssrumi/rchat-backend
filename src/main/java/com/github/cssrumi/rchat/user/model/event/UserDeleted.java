package com.github.cssrumi.rchat.user.model.event;

import com.github.cssrumi.rchat.model.Event;
import com.github.cssrumi.rchat.user.model.payload.DeleteUserPayload;
import java.time.OffsetDateTime;

public class UserDeleted extends Event {

    public UserDeleted(OffsetDateTime dateTime, DeleteUserPayload payload) {
        super(dateTime, payload, UserDeleted.class.getName());
    }

    @Override
    public DeleteUserPayload getPayload() {
        return (DeleteUserPayload) super.getPayload();
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
