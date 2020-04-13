package com.github.cssrumi.rchat.user.model.command;

import com.github.cssrumi.rchat.model.Command;
import com.github.cssrumi.rchat.user.model.payload.RegisterUserPayload;
import java.time.OffsetDateTime;

public class RegisterUser extends Command {

    public RegisterUser(OffsetDateTime dateTime, RegisterUserPayload payload) {
        super(dateTime, payload, RegisterUser.class.getName());
    }

    @Override
    public RegisterUserPayload getPayload() {
        return (RegisterUserPayload) super.getPayload();
    }
}
