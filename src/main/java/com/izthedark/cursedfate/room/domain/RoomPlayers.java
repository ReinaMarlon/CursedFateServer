package com.izthedark.cursedfate.room.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class RoomPlayers {
    private String id;
    private String roomCode;
    private String playerId;
    private String selectedCharacterId;
}
