package com.github.cssrumi.rchat.common.event;

import io.smallrye.mutiny.Uni;

public interface Consume<E extends Event> {

    Uni<Void> consume(E event);
}
