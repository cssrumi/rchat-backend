package com.github.cssrumi.rchat.message;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntity;
import java.time.LocalDateTime;

public class Message extends ReactivePanacheMongoEntity {

    String sendBy;
    LocalDateTime sendAt;
    String message;

}
