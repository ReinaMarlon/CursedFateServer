package com.izthedark.cursedfate.game.domain.ports.out;

import com.izthedark.cursedfate.game.application.dto.Game;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoadGameServerDataPort {
    Optional<Game> loadGameServerData(String sessionGameToken);
}
