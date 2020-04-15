package com.github.cssrumi.rchat.channel.process;

import com.github.cssrumi.rchat.channel.dto.ChannelInfo;
import com.github.cssrumi.rchat.channel.ChannelConverter;
import com.github.cssrumi.rchat.channel.model.ChannelStatus;
import io.quarkus.cache.CacheInvalidate;
import io.quarkus.cache.CacheResult;
import io.smallrye.mutiny.Uni;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import static java.util.stream.Collectors.toList;

@ApplicationScoped
public class ChannelQuery {

    static final String IS_CHANNEL_ACTIVATED = "isChannelActivated";
    static final String IS_CHANNEL_EXISTS = "isChannelExists";
    private final ChannelRepository channelRepository;

    @Inject
    public ChannelQuery(ChannelRepository channelRepository) {
        this.channelRepository = channelRepository;
    }


    public Uni<ChannelInfo> find(String name) {
        return channelRepository.find("name", name)
                                .firstResult()
                                .map(ChannelConverter::toChannelInfo);
    }

    public Uni<List<ChannelInfo>> getAll() {
        return channelRepository.findAll()
                                .list()
                                .map(channels -> channels.stream()
                                                         .map(ChannelConverter::toChannelInfo)
                                                         .collect(toList()));
    }

    public Uni<List<ChannelInfo>> getAll(ChannelStatus status) {
        return channelRepository.find("status", status)
                                .list()
                                .map(channels -> channels.stream()
                                                         .map(ChannelConverter::toChannelInfo)
                                                         .collect(toList()));
    }

    @CacheResult(cacheName = IS_CHANNEL_EXISTS)
    public Uni<Boolean> isChannelExists(String name) {
        return channelRepository.isChannelExist(name);
    }

    @CacheResult(cacheName = IS_CHANNEL_ACTIVATED)
    public Uni<Boolean> isChannelActivated(String name) {
        return channelRepository.isChannelActive(name);
    }

    @CacheInvalidate(cacheName = IS_CHANNEL_ACTIVATED)
    void invalidateChannelActivated(String name) {
    }

    @CacheInvalidate(cacheName = IS_CHANNEL_EXISTS)
    void invalidateChannelExists(String name) {
    }
}
