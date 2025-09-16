package com.izthedark.cursedfate.user.domain.model;

import com.izthedark.cursedfate.character.domain.model.Character;
import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class UserCharacter {
    private Long id;
    private Character character;
    private Long userId;
    private Long level;
    private Long atk;
    private Long spd;
    private Long hp;
    private Long def;
}
