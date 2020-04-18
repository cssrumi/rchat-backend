package com.github.cssrumi.rchat.security.model;

import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;

@MongoEntity(collection = "Security")
public class UserSecurity extends ReactivePanacheMongoEntity {

    public String username;
    public String password;
    public String token;
    public Long tokenCreationTimestamp;

}
