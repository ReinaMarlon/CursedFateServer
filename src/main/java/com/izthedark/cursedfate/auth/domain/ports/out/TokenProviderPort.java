package com.izthedark.cursedfate.auth.domain.ports.out;

import com.izthedark.cursedfate.auth.domain.model.AuthToken;
import com.izthedark.cursedfate.user.domain.model.User;

public interface TokenProviderPort {
    AuthToken generateToken(User user);
    boolean validateToken(String token);
}