package com.github.cssrumi.rchat.message.process;

import com.github.cssrumi.rchat.channel.process.ChannelQuery;
import com.github.cssrumi.rchat.common.CommandHandler;
import com.github.cssrumi.rchat.common.EventFactory;
import com.github.cssrumi.rchat.message.model.Message;
import com.github.cssrumi.rchat.message.model.MessagePayload;
import com.github.cssrumi.rchat.message.model.command.SendMessage;
import com.github.cssrumi.rchat.user.process.UserQuery;
import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.tuples.Tuple2;
import io.vertx.mutiny.core.eventbus.EventBus;
import javax.inject.Inject;
import javax.inject.Singleton;

import static com.github.cssrumi.rchat.common.TopicConstants.MESSAGE_SENT_TOPIC;
import static com.github.cssrumi.rchat.common.TopicConstants.SEND_MESSAGE_TOPIC;

@Singleton
public class MessageCommandHandler extends CommandHandler<Message> {

    private final MessageRepository messageRepository;
    private final UserQuery userQuery;
    private final ChannelQuery channelQuery;
    private final EventFactory<Message> eventFactory;
    private final MessagePublisher messagePublisher;

    @Inject
    public MessageCommandHandler(EventBus eventBus, MessageRepository messageRepository,
                                 UserQuery userRepository, ChannelQuery channelQuery,
                                 EventFactory<Message> eventFactory, MessagePublisher messagePublisher) {
        super(eventBus);
        this.messageRepository = messageRepository;
        this.userQuery = userRepository;
        this.channelQuery = channelQuery;
        this.eventFactory = eventFactory;
        this.messagePublisher = messagePublisher;
    }

    @ConsumeEvent(SEND_MESSAGE_TOPIC)
    Uni<Void> sendMessageHandler(SendMessage command) {
        final MessagePayload payload = command.getPayload();
        return userQuery.isUserExist(payload.sendBy)
                        .and(channelQuery.isChannelExists(payload.channel))
                        .map(result -> createMessage(result, payload))
                        .onItem().apply(this::emmitMessage)
                        .onItem().produceUni(message -> messageRepository.persist(message))
                        .onItem().produceUni(result -> sendEvent(MESSAGE_SENT_TOPIC, command, eventFactory));
    }

    Message createMessage(Tuple2<Boolean, Boolean> userAndChannelExists, MessagePayload payload) {
        if (!userAndChannelExists.getItem1()) {
            throw new RuntimeException("User not found");
        }

        if (!userAndChannelExists.getItem2()) {
            throw new RuntimeException("Channel not found");
        }

        return toMessage(payload);
    }

    private Message emmitMessage(Message message) {
        messagePublisher.emmit(message);
        return message;
    }

    private Message toMessage(MessagePayload payload) {
        Message message = new Message();
        message.channel = payload.channel;
        message.message = payload.message;
        message.sentAt = payload.sendAt;
        message.sentBy = payload.sendBy;

        return message;
    }
}
