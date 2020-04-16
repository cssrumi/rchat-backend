package com.github.cssrumi.rchat.security.model.payload;

import com.github.cssrumi.rchat.common.Payload;

public class LogoutPayload implements Payload {

    public String username;

    public LogoutPayload(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "LogoutPayload{" +
                "username='" + username + '\'' +
                '}';
    }
}
