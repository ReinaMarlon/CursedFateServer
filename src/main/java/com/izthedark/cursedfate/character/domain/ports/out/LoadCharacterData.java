package com.izthedark.cursedfate.character.domain.ports.out;

import java.util.Optional;

public interface LoadCharacterData {
    Optional<Character> findById(Long id);
}
