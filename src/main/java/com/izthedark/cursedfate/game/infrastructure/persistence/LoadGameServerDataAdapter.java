package com.izthedark.cursedfate.game.infrastructure.persistence;

import com.izthedark.cursedfate.game.application.dto.Game;
import com.izthedark.cursedfate.game.domain.ports.out.LoadGameServerDataPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LoadGameServerDataAdapter implements LoadGameServerDataPort {
    @Override
    public Optional<Game> loadGameServerData(String sessionGameToken) {
        Game game = new Game();
        game.setSessionGameToken(sessionGameToken);
        return Optional.of(game);
    }
}
