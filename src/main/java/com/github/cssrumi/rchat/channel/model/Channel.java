package com.github.cssrumi.rchat.channel.model;

import io.quarkus.mongodb.panache.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;

@MongoEntity(collection = "Channel")
public class Channel extends ReactivePanacheMongoEntity {

    public String name;
    public Long createdAt;
    public ChannelStatus status;

}
