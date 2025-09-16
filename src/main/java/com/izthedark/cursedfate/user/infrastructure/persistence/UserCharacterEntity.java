package com.izthedark.cursedfate.user.infrastructure.persistence;

import jakarta.persistence.*;

@Entity
public class UserCharacterEntity {
    @Id
    private Long characterId;
    private Long level;
    private Long atk;
    private Long spd;
    private Long hp;
    private Long def;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

}

