package com.github.cssrumi.rchat.model;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.core.eventbus.EventBus;
import java.util.concurrent.CompletableFuture;

public abstract class CommandHandler<T> {

    protected final EventBus eventBus;

    public CommandHandler(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public <C extends Command> Uni<Void> sendEvent(String topic, C command, EventFactory<T> eventFactory) {
        return Uni.createFrom()
                  .completionStage(CompletableFuture.runAsync(() -> eventBus.publish(topic, eventFactory.from(command))));
    }
}
