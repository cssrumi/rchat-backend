package com.github.cssrumi.rchat.message;

import com.github.cssrumi.rchat.message.model.Message;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.operators.multi.processors.UnicastProcessor;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MessagePublisher {

    private final UnicastProcessor<Message> messageStream = UnicastProcessor.create();

    public void emmit(Message message) {
        messageStream.onNext(message);
    }

    public Multi<Message> stream() {
        return messageStream;
    }

    public Multi<Message> stream(String channel) {
        return messageStream.transform()
                            .byFilteringItemsWith(message -> message.channel.equals(channel));
    }
}
