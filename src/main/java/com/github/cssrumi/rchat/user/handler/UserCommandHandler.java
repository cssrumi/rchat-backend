package com.github.cssrumi.rchat.user.handler;

import com.github.cssrumi.rchat.model.CommandHandler;
import com.github.cssrumi.rchat.model.EventFactory;
import com.github.cssrumi.rchat.user.UserRepository;
import com.github.cssrumi.rchat.user.model.Status;
import com.github.cssrumi.rchat.user.model.User;
import com.github.cssrumi.rchat.user.model.command.DeleteUser;
import com.github.cssrumi.rchat.user.model.command.RegisterUser;
import com.github.cssrumi.rchat.user.model.payload.RegisterUserPayload;
import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.tuples.Tuple2;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.mutiny.core.eventbus.EventBus;
import java.time.OffsetDateTime;
import javax.inject.Inject;
import javax.inject.Singleton;

import static com.github.cssrumi.rchat.model.TopicConstants.DELETE_USER_TOPIC;
import static com.github.cssrumi.rchat.model.TopicConstants.REGISTER_USER_TOPIC;
import static com.github.cssrumi.rchat.model.TopicConstants.USER_CREATED_TOPIC;
import static com.github.cssrumi.rchat.model.TopicConstants.USER_DELETED_TOPIC;

@Singleton
class UserCommandHandler extends CommandHandler<User> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserCommandHandler.class);
    private final EventFactory<User> eventFactory;
    private final UserRepository userRepository;

    @Inject
    UserCommandHandler(EventBus eventBus, EventFactory<User> eventFactory, UserRepository userRepository) {
        super(eventBus);
        this.eventFactory = eventFactory;
        this.userRepository = userRepository;
    }

    @ConsumeEvent(REGISTER_USER_TOPIC)
    Uni<Void> registerUserHandler(RegisterUser registerUserCommand) {
        final RegisterUserPayload payload = registerUserCommand.getPayload();
        return userRepository.isUserExist(payload.username)
                             .and(userRepository.isEmailExist(payload.email))
                             .map(result -> createUser(result, payload))
                             .onItem().produceUni(user -> userRepository.persist(user))
                             .onItem().produceUni(result -> sendEvent(USER_CREATED_TOPIC, registerUserCommand, eventFactory));
    }

    @ConsumeEvent(DELETE_USER_TOPIC)
    Uni<Void> deleteUserHandler(DeleteUser deleteUserCommand) {
        return userRepository.findByUsername(deleteUserCommand.getPayload().username)
                             .onItem().produceUni(user -> userRepository.delete(user))
                             .onItem().produceUni(result -> sendEvent(USER_DELETED_TOPIC, deleteUserCommand, eventFactory));
    }

    User createUser(Tuple2<Boolean, Boolean> usernameAndEmailExists, RegisterUserPayload payload) {
        if (usernameAndEmailExists.getItem1()) {
            throw new RuntimeException("User already exists");
        }

        if (usernameAndEmailExists.getItem2()) {
            throw new RuntimeException("Email is already taken");
        }

        return User.builder()
                   .createdAt(OffsetDateTime.now())
                   .username(payload.username)
                   .password(payload.password)
                   .displayName(payload.displayName)
                   .status(Status.CREATED)
                   .email(payload.email)
                   .build();
    }
}
