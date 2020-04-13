package com.github.cssrumi.rchat.model;

public interface EventFactory<T> {

    <C extends Command> Event from(C command);
}
