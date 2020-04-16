package com.github.cssrumi.rchat.user.model;

import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import java.time.OffsetDateTime;

@MongoEntity(collection = "User")
public class User extends ReactivePanacheMongoEntity {

    public Long createdAt;
    public String username;
    public String displayName;
    public Status status;
    public String email;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Long createdAt;
        private String username;
        private String displayName;
        private Status status;
        private String email;

        Builder() {
        }

        public Builder createdAt(OffsetDateTime createdAt) {
            this.createdAt = createdAt.toEpochSecond();
            return this;
        }

        public Builder createdAt(Long epochSecond) {
            this.createdAt = epochSecond;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder displayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder status(Status status) {
            this.status = status;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public User build() {
            User user = new User();
            user.createdAt = createdAt;
            user.displayName = displayName;
            user.username = username;
            user.email = email;
            user.status = status;

            return user;
        }
    }

}
