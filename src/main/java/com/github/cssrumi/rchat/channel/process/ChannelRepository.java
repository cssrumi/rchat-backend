package com.github.cssrumi.rchat.channel.process;

import com.github.cssrumi.rchat.channel.model.Channel;
import com.github.cssrumi.rchat.channel.model.ChannelStatus;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import io.smallrye.mutiny.Uni;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
class ChannelRepository implements ReactivePanacheMongoRepository<Channel> {

    Uni<Boolean> isChannelExist(String channel) {
        return find("name", channel).firstResultOptional().map(Optional::isPresent);
    }

    Uni<Channel> findByName(String name) {
        return find("name", name).firstResult();
    }

    Uni<Boolean> isChannelActive(String name) {
        return findByName(name).map(channel -> channel.status == ChannelStatus.ACTIVE);
    }
}
