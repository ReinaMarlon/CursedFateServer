package com.izthedark.cursedfate.auth.application.dto;

import com.izthedark.cursedfate.auth.domain.model.Origin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Register {
    private String username;
    private String email;
    private String password;
    private Origin origin;
    private String sessionId;
}
