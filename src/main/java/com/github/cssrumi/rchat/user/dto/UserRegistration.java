package com.github.cssrumi.rchat.user.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;
import javax.validation.constraints.Email;
import org.hibernate.validator.constraints.Length;

@RegisterForReflection
public class UserRegistration {

    @Length(min = 3, max = 30, message = "Display name must contain at least {min} and less than {max} characters")
    public String displayName;
    @Length(min = 6, max = 15, message = "Username must contain at least {min} and less than {max} characters")
    public String username;
    @Length(min = 4, max = 16, message = "Display name must contain at least {min} and less than {max} characters")
    public String password;
    @Email
    public String email;

}
