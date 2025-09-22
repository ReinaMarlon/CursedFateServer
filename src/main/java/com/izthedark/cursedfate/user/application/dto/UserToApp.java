package com.izthedark.cursedfate.user.application.dto;

import com.izthedark.cursedfate.character.application.dto.UserCharacterDTO;
import com.izthedark.cursedfate.character.domain.model.UserCharacter;
import com.izthedark.cursedfate.user.domain.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class UserToApp {
    private Long id;
    private String username;
    private String email;
    private Long level;
    private Long coins;
    private String profilePicture;
    private List<UserCharacterDTO> characters;

    public UserToApp(User user, List<UserCharacter> userCharacters) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.level = user.getLevel();
        this.coins = user.getCoins();
        this.profilePicture = user.getProfilePicture();
        this.characters = userCharacters.stream()
                .map(UserCharacterDTO::new)
                .collect(Collectors.toList());
    }
}
