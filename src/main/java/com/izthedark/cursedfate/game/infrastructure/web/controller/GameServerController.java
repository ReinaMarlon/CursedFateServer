package com.izthedark.cursedfate.game.infrastructure.web.controller;

import com.izthedark.cursedfate.game.application.dto.Game;
import com.izthedark.cursedfate.game.application.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/server/v1")
public class GameServerController {

    private final GameService gameService;

    public GameServerController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/getGameData")
    public ResponseEntity<Game> getUserData(@RequestParam String sessionGameToken) {
        return gameService.loadGameServerData(sessionGameToken)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}


