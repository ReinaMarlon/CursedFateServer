package com.izthedark.cursedfate.character.infrastructure.web;

import com.izthedark.cursedfate.character.application.dto.CharacterDTO;
import com.izthedark.cursedfate.character.application.dto.UserCharacterDTO;
import com.izthedark.cursedfate.character.application.services.CharacterService;
import com.izthedark.cursedfate.character.application.services.UserCharacterService;
import com.izthedark.cursedfate.character.domain.model.Character;
import com.izthedark.cursedfate.character.domain.model.UserCharacter;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/characters/v1")
@RequiredArgsConstructor
public class CharacterController {
    //Test
    private final CharacterService characterService;
    private final UserCharacterService userCharacterService;

    @GetMapping
    public ResponseEntity<List<CharacterDTO>> getAllCharacters() {
        List<Character> characters = characterService.findAll();
        List<CharacterDTO> characterDTOs = characters.stream()
                .map(CharacterDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(characterDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CharacterDTO> getCharacterById(@PathVariable Long id) {
        return characterService.findById(id)
                .map(character -> ResponseEntity.ok(new CharacterDTO(character)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserCharacterDTO>> getCharactersByUserId(@PathVariable Long userId) {
        List<UserCharacter> userCharacters = userCharacterService.findByUserId(userId);
        List<UserCharacterDTO> userCharacterDTOs = userCharacters.stream()
                .map(UserCharacterDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userCharacterDTOs);
    }

    @GetMapping("/user-character/{id}")
    public ResponseEntity<UserCharacterDTO> getUserCharacterById(@PathVariable Long id) {
        return userCharacterService.findById(id)
                .map(userCharacter -> ResponseEntity.ok(new UserCharacterDTO(userCharacter)))
                .orElse(ResponseEntity.notFound().build());
    }
}