package com.izthedark.cursedfate.auth.application.dto;

import com.izthedark.cursedfate.auth.domain.model.Origin;
import lombok.*;

@Setter @Getter @AllArgsConstructor @NoArgsConstructor
public class Credentials {
    private String email;
    private String password;
    private Origin origin;
}
