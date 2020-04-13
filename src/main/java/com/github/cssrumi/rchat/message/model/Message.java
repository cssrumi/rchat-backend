package com.github.cssrumi.rchat.message.model;

import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;

@MongoEntity(collection = "Messages")
public class Message extends ReactivePanacheMongoEntity {

    public String sentBy;
    public Long sentAt;
    public String message;
    public String channel;

}
