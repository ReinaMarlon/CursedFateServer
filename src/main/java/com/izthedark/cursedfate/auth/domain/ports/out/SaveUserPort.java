package com.izthedark.cursedfate.auth.domain.ports.out;

import com.izthedark.cursedfate.auth.application.dto.Credentials;
import com.izthedark.cursedfate.user.domain.model.User;

public interface SaveUserPort {
    User save(Credentials credentials);
}