package com.izthedark.cursedfate.game.domain.model;

import lombok.*;

import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class MatchHistory {
    private Long id;
    private Long matchId;
    private int score;
    private Long coinsEarned;
    private List<String> teammates;
}