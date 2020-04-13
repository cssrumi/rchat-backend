package com.github.cssrumi.rchat.channel;

import com.github.cssrumi.rchat.channel.dto.ChannelInfo;
import com.github.cssrumi.rchat.channel.model.ChannelStatus;
import io.smallrye.mutiny.Uni;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import static java.util.stream.Collectors.toList;

@ApplicationScoped
public class ChannelQuery {

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
}
