package com.github.cssrumi.rchat.user.handler;

import com.github.cssrumi.rchat.model.Command;
import com.github.cssrumi.rchat.user.UserRepository;
import com.github.cssrumi.rchat.user.model.Status;
import com.github.cssrumi.rchat.user.model.User;
import com.github.cssrumi.rchat.user.model.command.DeleteUser;
import com.github.cssrumi.rchat.user.model.command.RegisterUser;
import com.github.cssrumi.rchat.user.model.event.UserEventFactory;
import com.github.cssrumi.rchat.user.model.payload.RegisterUserPayload;
import io.quarkus.vertx.ConsumeEvent;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.tuples.Tuple2;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.mutiny.core.eventbus.EventBus;
import java.time.OffsetDateTime;
import java.util.concurrent.CompletableFuture;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import static com.github.cssrumi.rchat.user.model.UserConstants.DELETE_USER_TOPIC;
import static com.github.cssrumi.rchat.user.model.UserConstants.REGISTER_USER_TOPIC;
import static com.github.cssrumi.rchat.user.model.UserConstants.USER_CREATED_TOPIC;
import static com.github.cssrumi.rchat.user.model.UserConstants.USER_DELETED_TOPIC;

@ApplicationScoped
class UserCommandHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserCommandHandler.class);
    private final EventBus eventBus;
    private final UserRepository userRepository;

    @Inject
    UserCommandHandler(EventBus eventBus, UserRepository userRepository) {
        this.eventBus = eventBus;
        this.userRepository = userRepository;
    }

    @ConsumeEvent(REGISTER_USER_TOPIC)
    Uni<Void> registerUser(RegisterUser registerUserCommand) {
        final RegisterUserPayload payload = registerUserCommand.getPayload();
        return userRepository.isUserExist(payload.username)
                             .and(userRepository.isEmailExist(payload.email))
                             .map(result -> createUser(result, payload))
                             .onItem().produceUni(user -> userRepository.persist(user))
                             .onItem().produceUni(result -> sendEvent(registerUserCommand, USER_CREATED_TOPIC));
    }

    @ConsumeEvent(DELETE_USER_TOPIC)
    Uni<Void> deleteUser(DeleteUser deleteUserCommand) {
        return userRepository.findByUsername(deleteUserCommand.getPayload().username)
                             .onItem().produceUni(user -> userRepository.delete(user))
                             .onItem().produceUni(result -> sendEvent(deleteUserCommand, USER_DELETED_TOPIC));
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

    <C extends Command> Uni<Void> sendEvent(C command, String topic) {
        return Uni.createFrom()
                  .completionStage(CompletableFuture.runAsync(() -> eventBus.publish(topic, UserEventFactory.from(command))));
    }
}
