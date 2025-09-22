package com.izthedark.cursedfate.user.application.dto;

import com.izthedark.cursedfate.character.infrastructure.persistence.UserCharacterEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class UserToApp {
    private Long id;
    private String username;
    private String email;
    private Long level;
    private Long coins;
    private String profilePicture;
    private List<UserCharacterEntity> characters;

}
