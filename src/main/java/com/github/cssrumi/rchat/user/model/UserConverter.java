package com.github.cssrumi.rchat.user.model;

import com.github.cssrumi.rchat.user.dto.UserInfo;

public class UserConverter {

    public static UserInfo toUserInfo(User user) {
        UserInfo userInfo = new UserInfo();
        userInfo.createdAt = user.createdAt;
        userInfo.displayName = user.displayName;
        userInfo.status = user.status;
        userInfo.username = user.username;

        return userInfo;
    }

}
