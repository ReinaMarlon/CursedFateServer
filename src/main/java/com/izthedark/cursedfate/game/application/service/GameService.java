package com.izthedark.cursedfate.game.application.service;

import com.izthedark.cursedfate.game.application.dto.Game;
import com.izthedark.cursedfate.game.domain.model.Status;
import com.izthedark.cursedfate.game.domain.ports.out.LoadGameServerDataPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameService {

    @Value("${GAME_VERSION:1.0.0}")
    private String gameVersion;

    @Value("${GAME_STATUS:ON}")
    private String gameStatus;

    private final LoadGameServerDataPort loadGameServerDataPort;

    public GameService(LoadGameServerDataPort loadGameServerDataPort) {
        this.loadGameServerDataPort = loadGameServerDataPort;
    }

    public Optional<Game> loadGameServerData(String sessionGameToken){
        var maybeGame = loadGameServerDataPort.loadGameServerData(sessionGameToken);
        if (maybeGame.isEmpty()) {
            return Optional.empty();
        }
        Game game = maybeGame.get();
        game.setVersion(gameVersion);
        game.setStatus(Status.valueOf(gameStatus));

        return Optional.of(game);
    }
}
