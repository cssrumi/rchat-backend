package com.github.cssrumi.rchat.security.process;

import com.github.cssrumi.rchat.common.RchatEventBus;
import com.github.cssrumi.rchat.security.model.UserSecurity;
import com.github.cssrumi.rchat.security.model.event.Unauthorized;
import com.github.cssrumi.rchat.security.model.payload.UnauthorizedPayload;
import com.github.cssrumi.rchat.user.model.payload.RegisterUserPayload;
import io.quarkus.security.UnauthorizedException;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.tuples.Tuple2;
import io.vertx.core.http.HttpServerRequest;
import java.time.OffsetDateTime;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.github.cssrumi.rchat.common.TopicConstants.UNAUTHORIZED_TOPIC;

@ApplicationScoped
public class SecurityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityService.class);
    static final int TOKEN_SIZE = 32;
    static final String AUTHORIZATION_HEADER = "Authorization";
    static final String USERNAME_HEADER = "Username";
    static final String BEARER = "Bearer";
    static final String INVALID_AUTHORIZATION_TYPE_MESSAGE = "Invalid authorization type...";
    static final String INVALID_TOKEN_MESSAGE = "Unauthorized request occurred. Invalid token.";
    static final String INVALID_PASSWORD_MESSAGE = "Unauthorized request occurred. Invalid password.";
    static final String INVALID_USERNAME_MESSAGE = "Empty username occurred.";

    private final RchatEventBus eventBus;
    private final UserSecurityQuery userSecurityQuery;

    @Inject
    public SecurityService(RchatEventBus eventBus, UserSecurityQuery userSecurityQuery) {
        this.eventBus = eventBus;
        this.userSecurityQuery = userSecurityQuery;
    }

    public Uni<Void> generateUserSecurityAndPersist(RegisterUserPayload payload) {
        return Uni.createFrom()
                  .item(createUserSecurity(payload))
                  .onItem()
                  .produceUni(userSecurity -> userSecurity.persist());
    }

    public Uni<String> authorize(HttpServerRequest request) {
        return Uni.createFrom()
                  .item(getTokenAndUsername(request))
                  .onItem()
                  .produceUni(tokenAndUsername -> verifyToken(tokenAndUsername));
    }

    Uni<Void> authenticate(String username, String password) {
        return verifyPassword(username, password);
    }

    String generateToken() {
        return RandomStringUtils.randomAlphanumeric(TOKEN_SIZE);
    }

    Uni<String> generateTokenAsync() {
        return Uni.createFrom().item(generateToken());
    }

    private UserSecurity createUserSecurity(RegisterUserPayload payload) {
        UserSecurity userSecurity = new UserSecurity();
        userSecurity.username = payload.username;
        userSecurity.password = payload.password;
        userSecurity.token = generateToken();
        userSecurity.tokenCreationTimestamp = OffsetDateTime.now().toEpochSecond();

        return userSecurity;
    }

    private Tuple2<String, String> getTokenAndUsername(HttpServerRequest request) {
        return Tuple2.of(getToken(request), getUsername(request));
    }

    public String getUsername(HttpServerRequest request) {
        final String username = request.getHeader(USERNAME_HEADER).strip();
        if (StringUtils.isEmpty(username)) {
            unauthorized(INVALID_USERNAME_MESSAGE);
        }

        return username;
    }

    private String getToken(HttpServerRequest request) {
        final String rawToken = request.getHeader(AUTHORIZATION_HEADER);
        if (!rawToken.startsWith(BEARER)) {
            unauthorized(INVALID_AUTHORIZATION_TYPE_MESSAGE);
        }

        return StringUtils.stripStart(rawToken, BEARER).strip();
    }

    private Uni<String> verifyToken(Tuple2<String, String> tokenAndUsername) {
        return userSecurityQuery.getToken(tokenAndUsername.getItem2())
                                .onItem()
                                .apply(userToken -> {
                                    if (!tokenAndUsername.getItem1().equals(userToken)) {
                                        unauthorized(INVALID_TOKEN_MESSAGE);
                                    }

                                    return tokenAndUsername.getItem2();
                                });
    }

    private Uni<Void> verifyPassword(String username, String password) {
        return userSecurityQuery.getPassword(username)
                                .onItem()
                                .apply(userPassword -> {
                                    if (!password.equals(userPassword)) {
                                        unauthorized(INVALID_PASSWORD_MESSAGE);
                                    }

                                    return null;
                                });
    }

    private void unauthorized(String message) {
        UnauthorizedPayload payload = new UnauthorizedPayload(message);
        Unauthorized event = new Unauthorized(OffsetDateTime.now(), payload);
        eventBus.publish(UNAUTHORIZED_TOPIC, event);

        throw new UnauthorizedException();
    }
}
