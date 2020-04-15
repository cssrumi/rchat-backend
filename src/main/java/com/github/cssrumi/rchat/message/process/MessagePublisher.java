package com.github.cssrumi.rchat.message.process;

import com.github.cssrumi.rchat.message.model.Message;
import io.reactivex.subjects.PublishSubject;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.converters.multi.MultiRxConverters;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MessagePublisher {

    private final PublishSubject<Message> messageSubject = PublishSubject.create();

    public void emmit(Message message) {
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
