package com.izthedark.cursedfate.character.domain.ports.out;

import com.izthedark.cursedfate.character.domain.model.Character;

import java.util.List;
import java.util.Optional;

public interface CharacterPersistencePort {
    List<Character> findAll();
    Optional<Character> findCharacterById(Long id);
}