package com.github.cssrumi.rchat.common.event;

import com.github.cssrumi.rchat.common.command.Command;

public interface EventFactory<T> {

    <C extends Command> Event from(C command);
}
