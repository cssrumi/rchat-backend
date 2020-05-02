package com.github.cssrumi.rchat.user;

import com.github.cssrumi.rchat.common.RchatEventBus;
import com.github.cssrumi.rchat.user.dto.UserRegistration;
import com.github.cssrumi.rchat.user.model.command.UserCommandFactory;
import com.github.cssrumi.rchat.user.process.UserQuery;
import io.smallrye.mutiny.Uni;
import java.util.Objects;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;
import org.wildfly.common.annotation.Nullable;

import static com.github.cssrumi.rchat.common.TopicConstants.DELETE_USER_TOPIC;
import static com.github.cssrumi.rchat.common.TopicConstants.REGISTER_USER_TOPIC;

@Path("/v1/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserEndpoint {

    private final RchatEventBus eventBus;
    private final UserQuery userQuery;

    @Inject
    UserEndpoint(RchatEventBus eventBus, UserQuery userQuery) {
        this.eventBus = eventBus;
        this.userQuery = userQuery;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> registerUser(@Valid UserRegistration dto) {
        return eventBus.request(REGISTER_USER_TOPIC, UserCommandFactory.fromUserRegistration(dto))
                       .map(result -> Response.status(201).build());
    }

    @DELETE
    @Path("/{username}")
    public Uni<Response> deleteUser(@PathParam String username) {
        return eventBus.request(DELETE_USER_TOPIC, UserCommandFactory.delete(username))
                       .map(result -> Response.status(200).build());
    }

    @GET
    @Path("/{username}")
    public Uni<Response> findUserByUsername(@PathParam String username) {
        return userQuery.findByUsername(username)
                        .map(userInfo -> Response.ok(userInfo).build());
    }

    @GET
    public Uni<Response> findUserByDisplayName(@Nullable @QueryParam String displayName) {
        if (Objects.nonNull(displayName)) {
            return userQuery.findByDisplayName(displayName)
                            .map(userInfo -> Response.ok(userInfo).build());
        }

        return userQuery.findAll()
                        .map(userInfos -> Response.ok(userInfos).build());
    }
}
