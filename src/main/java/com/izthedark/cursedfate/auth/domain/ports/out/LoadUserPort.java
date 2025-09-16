package com.izthedark.cursedfate.auth.domain.ports.out;

import com.izthedark.cursedfate.user.domain.model.User;

import java.util.Optional;

public interface LoadUserPort {
    Optional<User> loadUserByEmail(String email);
}
