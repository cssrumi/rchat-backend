package com.github.cssrumi.rchat.common.exception;

import javax.ws.rs.core.Response;

public class ResourceAlreadyExistsException extends DomainException {

    private static final long serialVersionUID = 3472527748128655029L;
    public static final Response.Status STATUS = Response.Status.CONFLICT;
    private static final String DEFAULT_MESSAGE = "Resource already exists.";

    public ResourceAlreadyExistsException() {
        super(DEFAULT_MESSAGE, STATUS);
    }

    public ResourceAlreadyExistsException(String message) {
        super(message, STATUS);
    }

    public static ResourceAlreadyExistsException fromResource(Class clazz) {
        return new ResourceAlreadyExistsException(String.format("%s already exists.", clazz.getSimpleName()));
    }

    public static ResourceAlreadyExistsException fromResource(String message) {
        return new ResourceAlreadyExistsException(String.format("%s already exists.", message));
    }
}
