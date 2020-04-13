package com.github.cssrumi.rchat.user.dto;

import com.github.cssrumi.rchat.user.model.Status;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class UserModification {

    public String displayName;
    public String username;
    public String password;
    public Status status;
    public String email;

}
