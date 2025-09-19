package com.izthedark.cursedfate.user.application.dto;

import com.izthedark.cursedfate.game.domain.model.MatchHistory;
import com.izthedark.cursedfate.user.domain.model.UserCharacter;
import com.izthedark.cursedfate.user.infrastructure.persistence.UserCharacterEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class UserToApp {
    private Long id;
    private String username;
    private String email;
    private Long level;
    private Long coins;
    private List<UserCharacterEntity> characters;

}
