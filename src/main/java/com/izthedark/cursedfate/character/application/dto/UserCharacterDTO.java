package com.izthedark.cursedfate.character.application.dto;

import com.izthedark.cursedfate.character.domain.model.UserCharacter;
import com.izthedark.cursedfate.user.domain.model.User;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UserCharacterDTO {
    private Long id;
    private CharacterDTO character;
    private Long userId;
    private Long level;
    private Long atk;
    private Long spd;
    private Long hp;
    private Long def;
    private String closePic;
    private String fullPic;

    public UserCharacterDTO(UserCharacter userCharacter) {
        this.id = userCharacter.getId();
        this.character = new CharacterDTO(userCharacter.getCharacter());
        this.userId = userCharacter.getUserId();
        this.level = userCharacter.getLevel();
        this.atk = userCharacter.getAtk();
        this.spd = userCharacter.getSpd();
        this.hp = userCharacter.getHp();
        this.def = userCharacter.getDef();
        this.closePic = userCharacter.getClosePic();
        this.fullPic = userCharacter.getFullPic();
    }
}