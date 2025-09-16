package com.izthedark.cursedfate.auth.domain.ports.in;

import com.izthedark.cursedfate.auth.domain.model.AuthToken;
import com.izthedark.cursedfate.auth.application.dto.Credentials;

public interface UserLoginUseCase {
    AuthToken login(Credentials credentials);
}
