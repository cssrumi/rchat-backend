package com.github.cssrumi.rchat.user;

import com.github.cssrumi.rchat.user.model.User;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import io.smallrye.mutiny.Uni;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements ReactivePanacheMongoRepository<User> {

    public Uni<User> findByUsername(String username) {
        return find("username", username).firstResult();
    }

    public Uni<User> findByDisplayName(String displayName) {
        return find("displayName", displayName).firstResult();
    }

    public Uni<Boolean> isUserExist(String username) {
        return find("username", username).firstResultOptional().map(Optional::isPresent);
    }

    public Uni<Boolean> isEmailExist(String email) {
        return find("email", email).firstResultOptional().map(Optional::isPresent);
    }
}
