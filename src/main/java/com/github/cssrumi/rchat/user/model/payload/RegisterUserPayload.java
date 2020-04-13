package com.github.cssrumi.rchat.user.model.payload;

import com.github.cssrumi.rchat.model.Payload;

public class RegisterUserPayload implements Payload {

    public final String displayName;
    public final String username;
    public final String password;
    public final String email;

    public RegisterUserPayload(String displayName, String username, String password, String email) {
        this.displayName = displayName;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @Override
    public String toString() {
        return "RegisterUserPayload{" +
                "displayName='" + displayName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
