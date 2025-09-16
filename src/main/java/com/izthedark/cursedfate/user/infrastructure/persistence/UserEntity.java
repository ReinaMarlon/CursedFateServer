package com.izthedark.cursedfate.user.infrastructure.persistence;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.izthedark.cursedfate.game.infrastructure.persistence.MatchHistoryEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;

    @Column(name = "password")
    private String password;

    private Long level;
    private Long coins;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<UserCharacterEntity> characters = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<MatchHistoryEntity> matchHistory = new ArrayList<>();
}
