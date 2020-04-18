package com.github.cssrumi.rchat.common.exception;

import java.io.Serializable;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;

public class DomainException extends ClientErrorException implements Serializable {

    private static final long serialVersionUID = 466149293985649359L;
    public static final String DEFAULT_MESSAGE = "Server Exception occurred";
    public static final Response.Status DEFAULT_STATUS = Response.Status.INTERNAL_SERVER_ERROR;

    public DomainException() {
        super(DEFAULT_MESSAGE, DEFAULT_STATUS);
    }

    public DomainException(String message) {
        super(message, DEFAULT_STATUS);
    }

    public DomainException(String message, int code) {
        super(message, code);
    }

    public DomainException(String message, Response.Status status) {
        super(message, status);
    }
}
