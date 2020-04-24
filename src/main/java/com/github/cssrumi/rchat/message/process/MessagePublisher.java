package com.github.cssrumi.rchat.message.process;

import com.github.cssrumi.rchat.message.model.Message;
import io.reactivex.subjects.PublishSubject;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.converters.multi.MultiRxConverters;
import javax.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class MessagePublisher {

    public static final Logger LOGGER = LoggerFactory.getLogger(MessagePublisher.class);
    private final PublishSubject<Message> messageSubject = PublishSubject.create();

    void emmit(Message message) {
        LOGGER.info("New message has been emitted");
        messageSubject.onNext(message);
    }

    public Multi<Message> stream() {
        return Multi.createFrom().converter(MultiRxConverters.fromObservable(), messageSubject);
    }

    public Multi<Message> stream(String channel) {
        return stream().transform()
                       .byFilteringItemsWith(message -> message.channel.equals(channel));
    }
}
