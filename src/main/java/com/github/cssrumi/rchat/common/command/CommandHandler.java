package com.github.cssrumi.rchat.common.command;

import com.github.cssrumi.rchat.common.RchatEventBus;
import com.github.cssrumi.rchat.common.event.EventFactory;
import io.smallrye.mutiny.Uni;
import java.util.concurrent.CompletableFuture;

public abstract class CommandHandler<T> {

    protected final RchatEventBus eventBus;

    public CommandHandler(RchatEventBus eventBus) {
        this.eventBus = eventBus;
    }

    public <C extends Command> Uni<Void> sendEvent(String topic, C command, EventFactory<T> eventFactory) {
        return Uni.createFrom()
                  .completionStage(CompletableFuture.runAsync(() -> eventBus.publish(topic, eventFactory.from(command))));
    }
}
