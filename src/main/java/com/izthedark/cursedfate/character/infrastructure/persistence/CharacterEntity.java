package com.izthedark.cursedfate.character.infrastructure.persistence;

import com.izthedark.cursedfate.character.domain.model.Character;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "character")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CharacterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fullname", nullable = false)
    private String fullName;

    @Column(name = "shortname", nullable = false)
    private String shortName;

    private Long level;
    private Long atk;
    private Long spd;
    private Long hp;
    private Long def;

    @Column(length = 500)
    private String description;

    @Column(name = "closepic")
    private String closePic;

    @Column(name = "fullpic")
    private String fullPic;

    public Character toDomain() {
        return new Character(id, fullName, shortName, level, atk, spd, hp, def, description, closePic, fullPic);
    }

    public static CharacterEntity fromDomain(Character character) {
        return CharacterEntity.builder()
                .id(character.getId())
                .fullName(character.getFullName())
                .shortName(character.getShortName())
                .level(character.getLevel())
                .atk(character.getAtk())
                .spd(character.getSpd())
                .hp(character.getHp())
                .def(character.getDef())
                .description(character.getDescription())
                .closePic(character.getClosePic())
                .fullPic(character.getFullPic())
                .build();
    }
}
