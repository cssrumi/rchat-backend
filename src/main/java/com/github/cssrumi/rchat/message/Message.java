package com.github.cssrumi.rchat.message;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;

public class Message extends ReactivePanacheMongoEntity {

    public String sentBy;
    public Long sentAt;
    public String message;
    public String channel;

}
