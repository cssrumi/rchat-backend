package com.github.cssrumi.rchat.message.process;

import com.github.cssrumi.rchat.message.model.Message;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
class MessageRepository implements ReactivePanacheMongoRepository<Message> {
}
