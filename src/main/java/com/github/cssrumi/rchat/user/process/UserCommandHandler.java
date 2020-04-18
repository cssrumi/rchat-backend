package com.github.cssrumi.rchat.user.process;

import com.github.cssrumi.rchat.common.RchatEventBus;
import com.github.cssrumi.rchat.common.Try;
import com.github.cssrumi.rchat.common.command.CommandHandler;
import com.github.cssrumi.rchat.common.event.EventFactory;
import com.github.cssrumi.rchat.common.exception.ResourceAlreadyExistsException;
import com.github.cssrumi.rchat.security.process.SecurityService;
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
import java.time.OffsetDateTime;
import javax.inject.Inject;
import javax.inject.Singleton;

import static com.github.cssrumi.rchat.common.TopicConstants.DELETE_USER_TOPIC;
import static com.github.cssrumi.rchat.common.TopicConstants.REGISTER_USER_TOPIC;
import static com.github.cssrumi.rchat.common.TopicConstants.USER_CREATED_TOPIC;
import static com.github.cssrumi.rchat.common.TopicConstants.USER_DELETED_TOPIC;

@Singleton
class UserCommandHandler extends CommandHandler<User> {

    private static final String UNABLE_TO_CREATE_ENTITY_LOG_TEMPLATE = "Unable to create User entity. %s already exists";
    private static final Logger LOGGER = LoggerFactory.getLogger(UserCommandHandler.class);
    private final EventFactory<User> eventFactory;
    private final UserRepository userRepository;
    private final SecurityService securityService;

    @Inject
    UserCommandHandler(RchatEventBus eventBus, EventFactory<User> eventFactory, UserRepository userRepository,
                       SecurityService securityService) {
        super(eventBus);
        this.eventFactory = eventFactory;
        this.userRepository = userRepository;
        this.securityService = securityService;
    }

    @ConsumeEvent(REGISTER_USER_TOPIC)
    Uni<Try> registerUserHandler(RegisterUser command) {
        final RegisterUserPayload payload = command.getPayload();
        return Try.raw(userRepository.isUserExist(payload.username)
                                      .and(userRepository.isEmailExist(payload.email))
                                      .map(result -> createUser(result, payload))
                                      .onItem().produceUni(user -> userRepository.persist(user))
                                      .onItem().produceUni(user -> securityService.generateUserSecurityAndPersist(payload))
                                      .onItem().produceUni(result -> sendEvent(USER_CREATED_TOPIC, command, eventFactory)));
    }

    @ConsumeEvent(DELETE_USER_TOPIC)
    Uni<Try> deleteUserHandler(DeleteUser command) {
        return Try.raw(userRepository.findByUsername(command.getPayload().username)
                                      .onItem().produceUni(user -> userRepository.delete(user))
                                      .onItem().produceUni(result -> sendEvent(USER_DELETED_TOPIC, command, eventFactory)));
    }

    User createUser(Tuple2<Boolean, Boolean> usernameAndEmailExists, RegisterUserPayload payload) {
        if (usernameAndEmailExists.getItem1()) {
            LOGGER.info(String.format(UNABLE_TO_CREATE_ENTITY_LOG_TEMPLATE, payload.username));
            throw ResourceAlreadyExistsException.fromResource("User");
        }

        if (usernameAndEmailExists.getItem2()) {
            LOGGER.info(String.format(UNABLE_TO_CREATE_ENTITY_LOG_TEMPLATE, payload.email));
            throw ResourceAlreadyExistsException.fromResource("Email");
        }

        return User.builder()
                   .createdAt(OffsetDateTime.now())
                   .username(payload.username)
                   .displayName(payload.displayName)
                   .status(Status.CREATED)
                   .email(payload.email)
                   .build();
    }
}
