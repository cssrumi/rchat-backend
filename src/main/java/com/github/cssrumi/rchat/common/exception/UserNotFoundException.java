package com.github.cssrumi.rchat.common.exception;

public class UserNotFoundException extends SecurityException {

    public UserNotFoundException() {
        super("User not found.");
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public static UserNotFoundException fromUsername(String username) {
        return new UserNotFoundException(String.format("User: %s not found.", username));
    }
}
