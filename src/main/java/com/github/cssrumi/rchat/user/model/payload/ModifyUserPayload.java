package com.github.cssrumi.rchat.user.model.payload;

import com.github.cssrumi.rchat.common.Payload;
import com.github.cssrumi.rchat.user.model.Status;

public class ModifyUserPayload implements Payload {

    public final String displayName;
    public final String username;
    public final String password;
    public final Status status;
    public final String email;

    public ModifyUserPayload(String displayName, String username, String password, Status status, String email) {
        this.displayName = displayName;
        this.username = username;
        this.password = password;
        this.status = status;
        this.email = email;
    }
}
