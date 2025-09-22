package com.izthedark.cursedfate.character.domain.ports.out;

import com.izthedark.cursedfate.character.domain.model.UserCharacter;

import java.util.List;
import java.util.Optional;

public interface UserCharacterPersistencePort {
    List<UserCharacter> findByUserId(Long userId);
    Optional<UserCharacter> findById(Long id);
    UserCharacter save(UserCharacter userCharacter);
    void deleteById(Long id);
}