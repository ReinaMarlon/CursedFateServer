package com.izthedark.cursedfate.character.application.dto;

import com.izthedark.cursedfate.character.domain.model.Character;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CharacterDTO {
    private Long id;
    private String fullName;
    private String shortName;
    private Long level;
    private Long atk;
    private Long spd;
    private Long hp;
    private Long def;
    private String description;
    private String closePic;
    private String fullPic;

    public CharacterDTO(Character character) {
        this.id = character.getId();
        this.fullName = character.getFullName();
        this.shortName = character.getShortName();
        this.level = character.getLevel();
        this.atk = character.getAtk();
        this.spd = character.getSpd();
        this.hp = character.getHp();
        this.def = character.getDef();
        this.description = character.getDescription();
        this.closePic = character.getClosePic();
        this.fullPic = character.getFullPic();
    }
}