package com.github.cssrumi.rchat.security.model.payload;

import com.github.cssrumi.rchat.common.Payload;

public class LoginPayload implements Payload {

    public String username;
    public String password;

    public LoginPayload(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginPayload{" +
                "username='" + username + '\'' +
                ", password='******'" +
                '}';
    }
}
