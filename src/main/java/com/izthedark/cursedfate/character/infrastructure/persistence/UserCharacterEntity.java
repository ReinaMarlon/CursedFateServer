package com.izthedark.cursedfate.character.infrastructure.persistence;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.izthedark.cursedfate.character.domain.model.UserCharacter;
import com.izthedark.cursedfate.user.domain.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "user_character")
@Entity @Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class UserCharacterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id", nullable = false)
    private CharacterEntity characterId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    private Long level;
    private Long atk;
    private Long spd;
    private Long hp;
    private Long def;

    @Column(name = "closepic")
    private String closePic;

    @Column(name = "fullpic")
    private String fullPic;

    public UserCharacter toDomain() {
        return new UserCharacter(
                id,
                characterId.toDomain(),
                userId,
                level,
                atk,
                spd,
                hp,
                def,
                closePic,
                fullPic
        );
    }

}

