package com.github.cssrumi.rchat.channel;

import com.github.cssrumi.rchat.channel.model.Channel;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import io.smallrye.mutiny.Uni;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ChannelRepository implements ReactivePanacheMongoRepository<Channel> {

    public Uni<Boolean> isChannelExist(String channel) {
        return find("name", channel).firstResultOptional().map(Optional::isPresent);
    }

    public Uni<Channel> findByName(String name) {
        return find("name", name).firstResult();
    }

}
