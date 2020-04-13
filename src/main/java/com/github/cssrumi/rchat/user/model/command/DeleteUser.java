package com.github.cssrumi.rchat.user.model.command;

import com.github.cssrumi.rchat.model.Command;
import com.github.cssrumi.rchat.user.model.payload.DeleteUserPayload;
import java.time.OffsetDateTime;

public class DeleteUser extends Command {

    public DeleteUser(OffsetDateTime dateTime, DeleteUserPayload payload) {
        super(dateTime, payload, DeleteUser.class.getName());
    }

    @Override
    public DeleteUserPayload getPayload() {
        return (DeleteUserPayload) super.getPayload();
    }
}
