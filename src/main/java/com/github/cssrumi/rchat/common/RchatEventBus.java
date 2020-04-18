package com.github.cssrumi.rchat.common;

import com.github.cssrumi.rchat.channel.model.command.ChangeChannelStatus;
import com.github.cssrumi.rchat.common.Try;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.eventbus.EventBus;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

@Dependent
public class RchatEventBus {

    private final EventBus eventBus;

    @Inject
    public RchatEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public <T> Uni<T> request(String address, Object message) {
        return eventBus.request(address, message)
                       .onItem().apply(result -> ((Try<T>) result.body()).get());
    }

    public void publish(String address, Object message) {
        eventBus.publish(address, message);
    }

    public void sendAndForget(String address, Object message) {
        eventBus.sendAndForget(address, message);
    }
}
