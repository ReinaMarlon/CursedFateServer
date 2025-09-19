package com.izthedark.cursedfate.auth.domain.ports.out;

import com.izthedark.cursedfate.auth.application.dto.Register;
import com.izthedark.cursedfate.user.domain.model.User;

public interface SaveUserPort {
    User save(Register register);
    void updateToken(Long userId, String token);
}