package com.izthedark.cursedfate.room.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Room {
    private String id;
    private String roomCode;
    private String hostId;
    private Integer maxPlayers;
    private Timestamp createdAt;
}
