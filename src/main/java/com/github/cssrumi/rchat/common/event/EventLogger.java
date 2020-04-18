package com.github.cssrumi.rchat.common.event;

import com.github.cssrumi.rchat.channel.model.event.ChannelCreated;
import com.github.cssrumi.rchat.channel.model.event.ChannelDeleted;
import com.github.cssrumi.rchat.channel.model.event.ChannelStatusChanged;
import com.github.cssrumi.rchat.message.model.event.MessageSent;
import com.github.cssrumi.rchat.security.model.event.LoggedIn;
import com.github.cssrumi.rchat.security.model.event.LoggedOut;
import com.github.cssrumi.rchat.security.model.event.Unauthorized;
import com.github.cssrumi.rchat.user.model.event.UserCreated;
import com.github.cssrumi.rchat.user.model.event.UserDeleted;
import com.github.cssrumi.rchat.user.model.event.UserModified;
import io.quarkus.vertx.ConsumeEvent;
import io.vertx.mutiny.core.eventbus.EventBus;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.github.cssrumi.rchat.common.TopicConstants.*;
import static com.github.cssrumi.rchat.common.TopicConstants.USER_DELETED_TOPIC;

@Singleton
public final class EventLogger {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventLogger.class);
    private final EventBus eventBus;

    @Inject
    public EventLogger(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @ConsumeEvent(CHANNEL_CREATED_TOPIC)
    void consumeChannelCreated(ChannelCreated event) {
        LOGGER.info("New channel created. Event: " + event);
    }

    @ConsumeEvent(CHANNEL_DELETED_TOPIC)
    void consumeChannelDeleted(ChannelDeleted event) {
        LOGGER.info("Channel deleted. Event: " + event);
    }

    @ConsumeEvent(CHANNEL_STATUS_CHANGED_TOPIC)
    void consumeChannelStatusChanged(ChannelStatusChanged event) {
        LOGGER.info("Channel deleted. Event: " + event);
    }

    @ConsumeEvent(MESSAGE_SENT_TOPIC)
    void consumeMessageSent(MessageSent event) {
        LOGGER.info("New message sent: " + event);
    }

    @ConsumeEvent(USER_CREATED_TOPIC)
    void consumeUserCreated(UserCreated event) {
        LOGGER.info("New user created. Event: " + event);
    }

    @ConsumeEvent(USER_DELETED_TOPIC)
    void consumeUserDeleted(UserDeleted event) {
        LOGGER.info("User deleted. Event: " + event);
    }

    @ConsumeEvent(USER_MODIFIED_TOPIC)
    void consumeUserModified(UserModified event) {
        LOGGER.info("User modified. Event: " + event);
    }

    @ConsumeEvent(UNAUTHORIZED_TOPIC)
    void consumeUnauthorized(Unauthorized event) {
        LOGGER.info("Unauthorized. Event: " + event);
    }

    @ConsumeEvent(USER_LOGGED_IN_TOPIC)
    void consumeLoggedIn(LoggedIn event) {
        LOGGER.info("LoggedIn. Event: " + event);
    }

    @ConsumeEvent(USER_LOGGED_OUT_TOPIC)
    void consumeLoggedOut(LoggedOut event) {
        LOGGER.info("LoggedOut. Event: " + event);
    }
}
