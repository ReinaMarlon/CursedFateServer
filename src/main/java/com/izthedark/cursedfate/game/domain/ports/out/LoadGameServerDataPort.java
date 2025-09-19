package com.izthedark.cursedfate.game.domain.ports.out;

import com.izthedark.cursedfate.game.application.dto.Game;

import java.util.Optional;

public interface LoadGameServerDataPort {
    Optional<Game> loadGameServerData(String sessionGameToken);

}
