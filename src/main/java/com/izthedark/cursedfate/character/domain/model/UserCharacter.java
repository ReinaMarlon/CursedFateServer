package com.izthedark.cursedfate.character.domain.model;

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
    private String closePic;
    private String fullPic;
}
