package com.github.cssrumi.rchat.model;

import java.time.OffsetDateTime;

public abstract class Event {

    protected final OffsetDateTime dateTime;
    protected final Payload payload;
    protected final String eventType;

    public Event(OffsetDateTime dateTime, Payload payload, String eventType) {
        this.dateTime = dateTime;
        this.payload = payload;
        this.eventType = eventType;
    }

    public OffsetDateTime getDateTime() {
        return dateTime;
    }

    public Payload getPayload() {
        return payload;
    }

    public String getEventType() {
        return eventType;
    }
}
