package com.izthedark.cursedfate.auth.domain.model;

import lombok.*;

@Getter @AllArgsConstructor
public class AuthToken {
    private final String token;
    private final long expiresIn;
}
