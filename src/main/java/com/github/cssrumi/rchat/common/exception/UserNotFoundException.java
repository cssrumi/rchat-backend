package com.github.cssrumi.rchat.common.exception;

import javax.ws.rs.core.Response;

public class UserNotFoundException extends DomainException {

    private static final long serialVersionUID = -2489718508186492931L;
    public static final Response.Status STATUS = Response.Status.NOT_FOUND;

    public UserNotFoundException() {
        super("User not found.", STATUS);
    }

    public UserNotFoundException(String message) {
        super(message, STATUS);
    }

    public static UserNotFoundException fromUsername(String username) {
        return new UserNotFoundException(String.format("User: %s not found.", username));
    }
}
