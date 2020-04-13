package com.github.cssrumi.rchat.message.handler;

import com.github.cssrumi.rchat.channel.ChannelRepository;
import com.github.cssrumi.rchat.message.MessagePublisher;
import com.github.cssrumi.rchat.message.MessageRepository;
import com.github.cssrumi.rchat.message.model.Message;
import com.github.cssrumi.rchat.message.model.MessagePayload;
import com.github.cssrumi.rchat.message.model.SendMessage;
import com.github.cssrumi.rchat.model.CommandHandler;
import com.github.cssrumi.rchat.model.EventFactory;
import com.github.cssrumi.rchat.user.UserRepository;
import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.tuples.Tuple2;
import io.vertx.mutiny.core.eventbus.EventBus;
import javax.inject.Inject;
import javax.inject.Singleton;

import static com.github.cssrumi.rchat.model.TopicConstants.MESSAGE_SENT_TOPIC;
import static com.github.cssrumi.rchat.model.TopicConstants.SEND_MESSAGE_TOPIC;

@Singleton
public class MessageCommandHandler extends CommandHandler<Message> {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;
    private final EventFactory<Message> eventFactory;
    private final MessagePublisher messagePublisher;

    @Inject
    public MessageCommandHandler(EventBus eventBus, MessageRepository messageRepository,
                                 UserRepository userRepository, ChannelRepository channelRepository,
                                 EventFactory<Message> eventFactory, MessagePublisher messagePublisher) {
        super(eventBus);
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.channelRepository = channelRepository;
        this.eventFactory = eventFactory;
        this.messagePublisher = messagePublisher;
    }

    @ConsumeEvent(SEND_MESSAGE_TOPIC)
    Uni<Void> sendMessageHandler(SendMessage command) {
        final MessagePayload payload = command.getPayload();
        return userRepository.isUserExist(payload.sendBy)
                             .and(channelRepository.isChannelExist(payload.channel))
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
