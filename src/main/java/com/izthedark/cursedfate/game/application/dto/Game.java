package com.izthedark.cursedfate.game.application.dto;

import com.izthedark.cursedfate.game.domain.model.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Game {
    private String sessionGameToken;
    private String version;
    private Status status;
}
