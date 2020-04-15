package com.github.cssrumi.rchat.common;

public interface EventFactory<T> {

    <C extends Command> Event from(C command);
}
