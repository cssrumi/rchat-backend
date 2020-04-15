package com.github.cssrumi.rchat.user.model.payload;

import com.github.cssrumi.rchat.common.Payload;

public class DeleteUserPayload implements Payload {

    public final String username;

    public DeleteUserPayload(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "DeleteUserPayload{" +
                "username='" + username + '\'' +
                '}';
    }
}
