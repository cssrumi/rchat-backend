package com.github.cssrumi.rchat.security.process;

import com.github.cssrumi.rchat.common.exception.UserNotFoundException;
import com.github.cssrumi.rchat.security.model.UserSecurity;
import io.quarkus.cache.CacheInvalidate;
import io.quarkus.cache.CacheResult;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import io.smallrye.mutiny.Uni;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
class UserSecurityQuery {

    private static final String TOKEN_CACHE = "tokenCache";
    private static final String PASSWORD_CACHE = "passwordCache";
    private final UserSecurityRepository userSecurityRepository;

    UserSecurityQuery() {
        this.userSecurityRepository = new UserSecurityRepository();
    }

    @CacheResult(cacheName = TOKEN_CACHE)
    Uni<String> getToken(String username) {
        return userSecurityRepository.getToken(username);
    }

    @CacheResult(cacheName = PASSWORD_CACHE)
    Uni<String> getPassword(String username) {
        return userSecurityRepository.getPassword(username);
    }

    @CacheInvalidate(cacheName = TOKEN_CACHE)
    void invalidateTokenCache(String username) {
    }

    @CacheInvalidate(cacheName = PASSWORD_CACHE)
    void invalidatePasswordCache(String username) {
    }

    private static class UserSecurityRepository implements ReactivePanacheMongoRepository<UserSecurity> {

        Uni<UserSecurity> findByUsername(String username) {
            return find("username", username).firstResultOptional()
                                             .onItem()
                                             .apply(optionalUserSecurity ->
                                                     optionalUserSecurity.orElseThrow(() -> UserNotFoundException.fromUsername(username)));
        }

        Uni<String> getToken(String username) {
            return findByUsername(username).onItem().apply(userSecurity -> userSecurity.token);
        }

        Uni<String> getPassword(String username) {
            return findByUsername(username).onItem().apply(userSecurity -> userSecurity.password);
        }
    }
}
