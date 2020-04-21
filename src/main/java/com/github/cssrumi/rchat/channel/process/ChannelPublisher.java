package com.github.cssrumi.rchat.channel.process;

import com.github.cssrumi.rchat.common.event.Event;
import io.reactivex.subjects.PublishSubject;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.converters.multi.MultiRxConverters;
import javax.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class ChannelPublisher {

    public static final Logger LOGGER = LoggerFactory.getLogger(ChannelPublisher.class);
    private final PublishSubject<Event> channelSubject = PublishSubject.create();

    void emmit(Event event) {
        channelSubject.onNext(event);
        LOGGER.info(String.format("%s event emitted successfully", event.getEventType()));
    }

    public Multi<Event> stream() {
        return Multi.createFrom()
                    .converter(MultiRxConverters.fromObservable(), channelSubject);
    }
}
