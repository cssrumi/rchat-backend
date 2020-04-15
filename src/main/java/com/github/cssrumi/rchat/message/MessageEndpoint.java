package com.github.cssrumi.rchat.message;

import com.github.cssrumi.rchat.message.dto.MessageDto;
import com.github.cssrumi.rchat.message.model.command.MessageCommandFactory;
import com.github.cssrumi.rchat.message.process.MessagePublisher;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.eventbus.EventBus;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.annotations.SseElementType;
import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.github.cssrumi.rchat.common.TopicConstants.SEND_MESSAGE_TOPIC;

@Path("/v1/message")
public class MessageEndpoint {

    private final static Logger LOGGER = LoggerFactory.getLogger(MessageEndpoint.class);
    private final EventBus eventBus;
    private final MessagePublisher messagePublisher;

    @Inject
    public MessageEndpoint(EventBus eventBus, MessagePublisher messagePublisher) {
        this.eventBus = eventBus;
        this.messagePublisher = messagePublisher;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Uni<Response> sendMessage(@Valid MessageDto dto) {
        return eventBus.request(SEND_MESSAGE_TOPIC, MessageCommandFactory.sendMessage(dto))
                .map(result -> Response.status(201).build());
    }

    @GET
    @Path("/{channel}")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType(MediaType.APPLICATION_JSON)
    public Multi<MessageDto> stream(@PathParam String channel) {
        return messagePublisher.stream(channel)
                               .map(MessageConverter::dto)
                               .map(messageDto -> {
                                   LOGGER.info(messageDto.toString());
                                   return messageDto;
                               });
    }
}
