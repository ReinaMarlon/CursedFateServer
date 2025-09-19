package com.izthedark.cursedfate.user.domain.ports.out;

import com.izthedark.cursedfate.user.application.dto.UserToApp;
import com.izthedark.cursedfate.user.domain.model.User;

import java.util.Optional;

public interface UserGetDataPort {
    Optional<UserToApp> loadUserByToken(String token);
}
