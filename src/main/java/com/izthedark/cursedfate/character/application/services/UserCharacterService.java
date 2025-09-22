package com.izthedark.cursedfate.character.application.services;

import com.izthedark.cursedfate.character.domain.model.UserCharacter;
import com.izthedark.cursedfate.character.domain.ports.out.LoadCharacterPerUser;
import com.izthedark.cursedfate.character.domain.ports.out.UserCharacterPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserCharacterService implements LoadCharacterPerUser {

    private final UserCharacterPersistencePort userCharacterPersistencePort;

    @Override
    public List<UserCharacter> findByUserId(Long userId) {
        return userCharacterPersistencePort.findByUserId(userId);
    }

    public Optional<UserCharacter> findById(Long id) {
        return userCharacterPersistencePort.findById(id);
    }

    public UserCharacter save(UserCharacter userCharacter) {
        return userCharacterPersistencePort.save(userCharacter);
    }

    public void deleteById(Long id) {
        userCharacterPersistencePort.deleteById(id);
    }
}