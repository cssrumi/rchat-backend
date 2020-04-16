package com.github.cssrumi.rchat.security.model.payload;

import com.github.cssrumi.rchat.common.Payload;

public class UnauthorizedPayload implements Payload {

    public String message;

    public UnauthorizedPayload(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "UnauthorizedPayload{" +
                "message='" + message + '\'' +
                '}';
    }
}
