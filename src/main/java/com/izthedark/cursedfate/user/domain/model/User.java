package com.izthedark.cursedfate.user.domain.model;

import com.izthedark.cursedfate.game.domain.model.MatchHistory;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class User {
    private Long id;
    private String username;
    private String email;
    private String password;
    private Long level;
    private Long coins;
    private String profilePicture;

    private List<UserCharacter> characters;
    private List<MatchHistory> matchHistory;

    private String temporalToken;

    public User(Long id, String username, String email, String password, Long level, Long coins, String token, String profilePicture) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.level = level;
        this.coins = coins;
        this.characters = new ArrayList<>();
        this.matchHistory = new ArrayList<>();
        this.temporalToken = token;
        this.profilePicture = profilePicture;
    }
}
