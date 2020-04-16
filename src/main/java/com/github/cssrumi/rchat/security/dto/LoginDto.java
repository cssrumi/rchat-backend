package com.github.cssrumi.rchat.security.dto;

import javax.validation.constraints.NotEmpty;

public class LoginDto {

    @NotEmpty
    public String username;

    @NotEmpty
    public String password;

    @Override
    public String toString() {
        return "LoginDto{" +
                "username='" + username + '\'' +
                ", password='******'" +
                '}';
    }
}
