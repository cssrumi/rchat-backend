package com.github.cssrumi.rchat.common.exception;

import java.io.Serializable;
import javax.ws.rs.core.Response;

public class ChannelNotFoundException extends DomainException implements Serializable {

    private static final long serialVersionUID = 1582250642204608325L;
    public static final Response.Status STATUS = Response.Status.NOT_FOUND;

    public ChannelNotFoundException() {
        super("Channel not found.", STATUS);
    }

    public ChannelNotFoundException(String message) {
        super(message, STATUS);
    }

    public static ChannelNotFoundException fromChannel(String channel) {
        return new ChannelNotFoundException(String.format("Channel: %s not found.", channel));
    }
}
