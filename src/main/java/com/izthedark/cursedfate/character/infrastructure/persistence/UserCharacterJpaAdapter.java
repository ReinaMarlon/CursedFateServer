package com.izthedark.cursedfate.character.infrastructure.persistence;

import com.izthedark.cursedfate.character.domain.model.UserCharacter;
import com.izthedark.cursedfate.character.domain.ports.out.UserCharacterPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserCharacterJpaAdapter implements UserCharacterPersistencePort {

    private final UserCharacterJpaRepository userCharacterJpaRepository;

    @Override
    public List<UserCharacter> findByUserId(Long userId) {
        return userCharacterJpaRepository.findAllByUserId(userId)
                .stream()
                .map(UserCharacterEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserCharacter> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public UserCharacter save(UserCharacter userCharacter) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
