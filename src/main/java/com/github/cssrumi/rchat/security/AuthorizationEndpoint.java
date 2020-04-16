package com.github.cssrumi.rchat.security;

import com.github.cssrumi.rchat.security.dto.LoginDto;
import com.github.cssrumi.rchat.security.dto.LoginResponse;
import com.github.cssrumi.rchat.security.model.command.SecurityCommandFactory;
import com.github.cssrumi.rchat.security.process.SecurityService;
import io.smallrye.mutiny.Uni;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.mutiny.core.eventbus.EventBus;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import static com.github.cssrumi.rchat.common.TopicConstants.LOGIN_USER_TOPIC;
import static com.github.cssrumi.rchat.common.TopicConstants.LOGOUT_USER_TOPIC;

@Path("/v1")
public class AuthorizationEndpoint {

    private final EventBus eventBus;
    private final SecurityService securityService;

    @Inject
    public AuthorizationEndpoint(EventBus eventBus, SecurityService securityService) {
        this.eventBus = eventBus;
        this.securityService = securityService;
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> login(@Context HttpServerRequest request, @Valid LoginDto dto) {
        return eventBus.request(LOGIN_USER_TOPIC, SecurityCommandFactory.from(dto))
                       .map(message -> new LoginResponse((String) message.body()))
                       .map(response -> Response.ok(response).build());
    }

    @GET
    @Path("/logout/{username}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> logout(@Context HttpServerRequest request, @PathParam String username) {
        return securityService.authorize(request, username)
                .onItem().produceUni(ignore -> eventBus.request(LOGOUT_USER_TOPIC, SecurityCommandFactory.from(username)))
                .map(ignore -> Response.ok().build());
    }
}
