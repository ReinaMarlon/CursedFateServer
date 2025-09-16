package com.izthedark.cursedfate.auth.application.dto;

import lombok.*;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class Credentials {
    private String email;
    private String password;

    public Credentials toDomain() {
        return new Credentials(email, password);
    }
}
