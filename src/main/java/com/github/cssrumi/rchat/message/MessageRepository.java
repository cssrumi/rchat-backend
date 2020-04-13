package com.github.cssrumi.rchat.message;

import com.github.cssrumi.rchat.message.model.Message;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MessageRepository implements ReactivePanacheMongoRepository<Message> {
}
