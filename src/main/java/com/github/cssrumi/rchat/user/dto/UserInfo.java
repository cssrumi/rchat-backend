package com.github.cssrumi.rchat.user.dto;

import com.github.cssrumi.rchat.user.model.Status;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class UserInfo {

    public String displayName;
    public String username;
    public Long createdAt;
    public Status status;

}
