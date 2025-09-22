package com.izthedark.cursedfate.character.domain.ports.out;

import com.izthedark.cursedfate.character.domain.model.UserCharacter;

import java.util.List;

public interface LoadCharacterPerUser {
    List<UserCharacter> findByUserId(Long userId);
}
