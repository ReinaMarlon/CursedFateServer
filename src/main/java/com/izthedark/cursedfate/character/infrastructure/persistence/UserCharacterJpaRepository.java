package com.izthedark.cursedfate.character.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface UserCharacterJpaRepository extends JpaRepository<UserCharacterEntity, Long> {
    List<UserCharacterEntity> findAllByUserId(Long userId);
}

