package com.izthedark.cursedfate.character.infrastructure.persistence;

import com.izthedark.cursedfate.character.domain.model.Character;
import com.izthedark.cursedfate.character.domain.ports.out.CharacterPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CharacterJpaAdapter implements CharacterPersistencePort {

    private final CharacterJpaRepository characterJpaRepository;

    @Override
    public List<Character> findAll() {
        return characterJpaRepository.findAll()
                .stream()
                .map(CharacterEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<com.izthedark.cursedfate.character.domain.model.Character> findCharacterById(Long id) {
        return characterJpaRepository.findById(id)
                .map(CharacterEntity::toDomain);
    }
}