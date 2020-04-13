package com.github.cssrumi.rchat.user;

import com.github.cssrumi.rchat.user.dto.UserInfo;
import com.github.cssrumi.rchat.user.model.User;

public class UserConverter {

    private UserConverter() {
    }

    public static UserInfo toUserInfo(User user) {
        UserInfo userInfo = new UserInfo();
        userInfo.createdAt = user.createdAt;
        userInfo.displayName = user.displayName;
        userInfo.status = user.status;
        userInfo.username = user.username;

        return userInfo;
    }

}
