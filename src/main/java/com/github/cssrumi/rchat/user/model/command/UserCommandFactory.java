package com.github.cssrumi.rchat.user.model.command;

import com.github.cssrumi.rchat.user.dto.UserModification;
import com.github.cssrumi.rchat.user.dto.UserRegistration;
import com.github.cssrumi.rchat.user.model.payload.DeleteUserPayload;
import com.github.cssrumi.rchat.user.model.payload.ModifyUserPayload;
import com.github.cssrumi.rchat.user.model.payload.RegisterUserPayload;
import java.time.OffsetDateTime;

public class UserCommandFactory {

    private UserCommandFactory() {
    }

    public static RegisterUser fromUserRegistration(UserRegistration dto) {
        RegisterUserPayload payload = new RegisterUserPayload(dto.displayName, dto.username, dto.password, dto.email);
        return new RegisterUser(OffsetDateTime.now(), payload);
    }

    public static ModifyUser fromUserModification(UserModification dto) {
        ModifyUserPayload payload = new ModifyUserPayload(dto.displayName, dto.username, dto.password, dto.status, dto.email);
        return new ModifyUser(OffsetDateTime.now(), payload);
    }

    public static DeleteUser delete(String username) {
        DeleteUserPayload payload = new DeleteUserPayload(username);
        return new DeleteUser(OffsetDateTime.now(), payload);
    }
}
