package com.github.cssrumi.rchat.security;

import com.github.cssrumi.rchat.common.RchatEventBus;
import com.github.cssrumi.rchat.security.dto.LoginDto;
import com.github.cssrumi.rchat.security.dto.LoginResponse;
import com.github.cssrumi.rchat.security.model.command.SecurityCommandFactory;
import com.github.cssrumi.rchat.security.process.SecurityService;
import io.smallrye.mutiny.Uni;
import io.vertx.core.http.HttpServerRequest;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import static com.github.cssrumi.rchat.common.TopicConstants.LOGIN_USER_TOPIC;
import static com.github.cssrumi.rchat.common.TopicConstants.LOGOUT_USER_TOPIC;

@Path("/v1")
public class AuthorizationEndpoint {

    private final RchatEventBus eventBus;
    private final SecurityService securityService;

    @Inject
    public AuthorizationEndpoint(RchatEventBus eventBus, SecurityService securityService) {
        this.eventBus = eventBus;
        this.securityService = securityService;
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> login(@Valid LoginDto dto) {
        return eventBus.request(LOGIN_USER_TOPIC, SecurityCommandFactory.from(dto))
                       .map(message -> new LoginResponse((String) message))
                       .map(response -> Response.ok(response).build());
    }

    @GET
    @Path("/logout")
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> logout(@Context HttpServerRequest request) {
        return securityService.authorize(request)
                              .onItem().produceUni(username -> eventBus.request(LOGOUT_USER_TOPIC, SecurityCommandFactory.from(username)))
                              .map(ignore -> Response.ok().build());
    }
}
