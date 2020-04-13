package com.github.cssrumi.rchat.model;

import java.time.OffsetDateTime;

public abstract class Command<P extends Payload> extends Event<P> {

    public Command(OffsetDateTime dateTime, P payload, String eventType) {
        super(dateTime, payload, eventType);
    }
}
