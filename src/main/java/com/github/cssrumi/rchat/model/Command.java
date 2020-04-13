package com.github.cssrumi.rchat.model;

import java.time.OffsetDateTime;

public class Command extends Event {

    public Command(OffsetDateTime dateTime, Payload payload, String eventType) {
        super(dateTime, payload, eventType);
    }
}
