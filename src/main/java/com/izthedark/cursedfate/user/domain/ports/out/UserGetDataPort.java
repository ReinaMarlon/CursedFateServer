package com.izthedark.cursedfate.user.domain.ports.out;

import com.izthedark.cursedfate.user.domain.model.User;

import java.util.Optional;

public interface UserGetDataPort {
    Optional<User> loadUserByToken(String token);
}
