package com.github.cssrumi.rchat.security.model.command;

import com.github.cssrumi.rchat.common.Command;
import com.github.cssrumi.rchat.security.model.payload.LoginPayload;
import java.time.OffsetDateTime;

public class Login extends Command<LoginPayload> {

    public Login(OffsetDateTime dateTime, LoginPayload payload) {
        super(dateTime, payload, Login.class.getName());
    }

    @Override
    public String toString() {
        return "Login{" +
                "dateTime=" + dateTime +
                ", payload=" + payload +
                ", eventType='" + eventType + '\'' +
                '}';
    }
}
