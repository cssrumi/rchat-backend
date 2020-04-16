package com.github.cssrumi.rchat.security.model.command;

import com.github.cssrumi.rchat.security.dto.LoginDto;
import com.github.cssrumi.rchat.security.model.payload.LoginPayload;
import com.github.cssrumi.rchat.security.model.payload.LogoutPayload;
import java.time.OffsetDateTime;

public class SecurityCommandFactory {

    private SecurityCommandFactory() {
    }

    public static Login from(LoginDto dto) {
        LoginPayload payload = new LoginPayload(dto.username, dto.password);
        Login command = new Login(OffsetDateTime.now(), payload);

        return command;
    }

    public static Logout from(String username) {
        LogoutPayload payload = new LogoutPayload(username);
        Logout command = new Logout(OffsetDateTime.now(), payload);

        return command;
    }

}
