package com.izthedark.cursedfate.character.application.services;


import com.izthedark.cursedfate.character.domain.model.Character;
import com.izthedark.cursedfate.character.domain.model.UserCharacter;
import com.izthedark.cursedfate.character.domain.ports.out.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CharacterService implements LoadAllCharacters, LoadCharacterPerUser {

    private final CharacterPersistencePort characterPersistencePort;
    private final UserCharacterPersistencePort userCharacterPersistencePort;

    @Override
    public List<Character> findAll() {
        return characterPersistencePort.findAll();
    }

    public Optional<Character> findById(Long id) {
        return characterPersistencePort.findCharacterById(id);
    }

    @Override
    public List<UserCharacter> findByUserId(Long userId) {
        return userCharacterPersistencePort.findByUserId(userId);
    }
}

