package com.izthedark.cursedfate.auth.domain.ports.in;

import com.izthedark.cursedfate.auth.application.dto.Register;
import com.izthedark.cursedfate.auth.domain.model.AuthToken;

public interface UserRegisterUseCase {
    AuthToken register(Register register);
}
