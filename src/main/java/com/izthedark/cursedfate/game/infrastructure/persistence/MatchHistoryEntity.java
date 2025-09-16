package com.izthedark.cursedfate.game.infrastructure.persistence;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.izthedark.cursedfate.user.infrastructure.persistence.UserEntity;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class MatchHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long matchId;
    private int score;
    private Long coinsEarned;
    private List<String> teammates;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserEntity user;
}