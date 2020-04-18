package com.github.cssrumi.rchat.common.command;

import com.github.cssrumi.rchat.common.Try;
import io.smallrye.mutiny.Uni;

public interface Respond<C extends Command> {

    Uni<Try> respond(C command);

}
