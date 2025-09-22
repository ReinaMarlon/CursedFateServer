package com.izthedark.cursedfate.character.domain.ports.out;


import com.izthedark.cursedfate.character.domain.model.Character;
import java.util.List;

public interface LoadAllCharacters {
    List<Character> findAll();
}
