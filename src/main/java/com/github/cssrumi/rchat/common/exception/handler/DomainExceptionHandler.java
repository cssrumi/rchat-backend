package com.github.cssrumi.rchat.common.exception.handler;

import com.github.cssrumi.rchat.common.exception.DomainException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DomainExceptionHandler implements ExceptionMapper<DomainException> {

    @Override
    public Response toResponse(DomainException e) {
        return Response.fromResponse(e.getResponse())
                       .entity(e.getMessage())
                       .build();
    }
}
