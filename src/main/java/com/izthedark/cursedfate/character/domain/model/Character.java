package com.izthedark.cursedfate.character.domain.model;

import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Character {
    private Long id;
    private String fullName;
    private String shortName;
    private Long level;
    private Long atk;
    private Long spd;
    private Long hp;
    private Long def;
}
