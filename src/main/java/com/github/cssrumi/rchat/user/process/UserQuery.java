package com.github.cssrumi.rchat.user.process;

import com.github.cssrumi.rchat.user.UserConverter;
import com.github.cssrumi.rchat.user.dto.UserInfo;
import io.smallrye.mutiny.Uni;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class UserQuery {

    private final UserRepository userRepository;

    @Inject
    public UserQuery(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Uni<UserInfo> findByUsername(String username) {
        return userRepository.findByUsername(username)
                             .map(UserConverter::toUserInfo);
    }

    public Uni<UserInfo> findByDisplayName(String displayName) {
        return userRepository.findByDisplayName(displayName)
                             .map(UserConverter::toUserInfo);
    }

    public Uni<List<UserInfo>> findAll() {
        return userRepository.findAll()
                             .list()
                             .map(list -> list.stream()
                                              .map(UserConverter::toUserInfo)
                                              .collect(Collectors.toList()));
    }

    public Uni<Boolean> isUserExist(String username) {
        return userRepository.isUserExist(username);
    }

    public Uni<Boolean> isEmailExist(String email) {
        return userRepository.isEmailExist(email);
    }

}
