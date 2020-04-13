package com.github.cssrumi.rchat.channel;

import com.github.cssrumi.rchat.channel.dto.ChannelCreation;
import com.github.cssrumi.rchat.channel.model.ChannelStatus;
import com.github.cssrumi.rchat.channel.model.command.ChannelCommandFactory;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.eventbus.EventBus;
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

import static com.github.cssrumi.rchat.model.TopicConstants.CREATE_CHANNEL_TOPIC;
import static com.github.cssrumi.rchat.model.TopicConstants.DELETE_CHANNEL_TOPIC;

@Path("/v1/channel")
@Produces(MediaType.APPLICATION_JSON)
public class ChannelEndpoint {

    private final ChannelQuery query;
    private final EventBus eventBus;

    @Inject
    public ChannelEndpoint(ChannelQuery query, EventBus eventBus) {
        this.query = query;
        this.eventBus = eventBus;
    }

    @GET
    public Uni<Response> getAll(@Nullable @QueryParam ChannelStatus status) {
        if (Objects.isNull(status)) {
            return query.getAll()
                        .map(channelInfos -> Response.ok(channelInfos).build());
        }

        return query.getAll(status)
                    .map(channelInfos -> Response.ok(channelInfos).build());
    }

    @GET
    @Path("/{channel}")
    public Uni<Response> getChannel(@PathParam String channel) {
        return query.find(channel).map(channelInfo -> Response.ok(channelInfo).build());
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> createNewChannel(@Valid ChannelCreation dto) {
        return eventBus.request(CREATE_CHANNEL_TOPIC, ChannelCommandFactory.createChannel(dto))
                       .map(result -> Response.status(201).build());
    }

    @DELETE
    @Path("/{channel}")
    public Uni<Response> deleteChannel(@PathParam String channel) {
        return eventBus.request(DELETE_CHANNEL_TOPIC, ChannelCommandFactory.deleteChannel(channel))
                       .map(result -> Response.ok().build());
    }
}
