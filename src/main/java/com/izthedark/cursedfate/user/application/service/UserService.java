package com.izthedark.cursedfate.user.application.service;

import com.izthedark.cursedfate.user.application.dto.UserToApp;
import com.izthedark.cursedfate.user.domain.model.User;
import com.izthedark.cursedfate.user.domain.ports.out.UserGetDataPort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserGetDataPort UserGetDataPort;

    public UserService(UserGetDataPort UserGetDataPort) {
        this.UserGetDataPort = UserGetDataPort;
    }

    public Optional<UserToApp> loadUserByToken(String token) {
        return UserGetDataPort.loadUserByToken(token);
    }
}
