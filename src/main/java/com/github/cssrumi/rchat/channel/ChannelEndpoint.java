package com.github.cssrumi.rchat.channel;

import com.github.cssrumi.rchat.channel.dto.ChannelRequest;
import com.github.cssrumi.rchat.channel.model.ChannelStatus;
import com.github.cssrumi.rchat.channel.model.command.ChannelCommandFactory;
import com.github.cssrumi.rchat.channel.process.ChannelPublisher;
import com.github.cssrumi.rchat.channel.process.ChannelQuery;
import com.github.cssrumi.rchat.common.RchatEventBus;
import com.github.cssrumi.rchat.common.event.Event;
import io.smallrye.mutiny.Multi;
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
import org.jboss.resteasy.annotations.SseElementType;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wildfly.common.annotation.Nullable;

import static com.github.cssrumi.rchat.common.TopicConstants.CREATE_CHANNEL_TOPIC;
import static com.github.cssrumi.rchat.common.TopicConstants.DELETE_CHANNEL_TOPIC;

@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
public class ChannelEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelEndpoint.class);
    private final ChannelQuery query;
    private final RchatEventBus eventBus;
    private final ChannelPublisher channelPublisher;

    @Inject
    public ChannelEndpoint(ChannelQuery query, RchatEventBus eventBus,
                           ChannelPublisher channelPublisher) {
        this.query = query;
        this.eventBus = eventBus;
        this.channelPublisher = channelPublisher;
    }

    @GET
    @Path("/channel")
    public Uni<Response> getAll(@Nullable @QueryParam ChannelStatus status) {
        if (Objects.isNull(status)) {
            return query.getAll()
                        .map(channelInfos -> Response.ok(channelInfos).build());
        }

        return query.getAll(status)
                    .map(channelInfos -> Response.ok(channelInfos).build());
    }

    @POST
    @Path("/channel")
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> createNewChannel(@Valid ChannelRequest dto) {
        return eventBus.request(CREATE_CHANNEL_TOPIC, ChannelCommandFactory.createChannel(dto))
                       .map(ignore -> Response.status(201).build());
    }

    @GET
    @Path("/channel-events")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType(MediaType.APPLICATION_JSON)
    public Multi<Event> eventsStream() {
        return channelPublisher.stream();
    }

    @GET
    @Path("/channel/{channel}")
    public Uni<Response> getChannel(@PathParam String channel) {
        return query.find(channel).map(channelInfo -> Response.ok(channelInfo).build());
    }

    @DELETE
    @Path("/channel/{channel}")
    public Uni<Response> deleteChannel(@PathParam String channel) {
        return eventBus.request(DELETE_CHANNEL_TOPIC, ChannelCommandFactory.deleteChannel(channel))
                       .map(ignore -> Response.ok().build());
    }
}
