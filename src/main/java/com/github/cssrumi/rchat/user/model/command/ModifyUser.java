package com.github.cssrumi.rchat.user.model.command;

import com.github.cssrumi.rchat.model.Command;
import com.github.cssrumi.rchat.user.model.payload.ModifyUserPayload;
import java.time.OffsetDateTime;

public class ModifyUser extends Command<ModifyUserPayload> {

    public ModifyUser(OffsetDateTime dateTime, ModifyUserPayload payload) {
        super(dateTime, payload, ModifyUser.class.getName());
    }
}
