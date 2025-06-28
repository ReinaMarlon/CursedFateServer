const roomInstances = new Map(); // clave: room_code, valor: { players: Set, host: cfgame_id }

export function createRoomInstance(roomCode, hostId) {
    if (!roomInstances.has(roomCode)) {
        roomInstances.set(roomCode, {
            host: hostId,
            players: new Set([hostId])
        });

        console.log(`✅ Instancia creada para la sala ${roomCode}`);
    }
}

export function addPlayerToRoom(roomCode, playerId) {
    const room = roomInstances.get(roomCode);
    if (room) {
        room.players.add(playerId);
        console.log(`👤 Jugador ${playerId} unido a sala ${roomCode}`);
    }
}

export function broadcastToRoom(roomCode, clients, payload, excludeId = null) {
    const room = roomInstances.get(roomCode);
    if (room) {
        for (const playerId of room.players) {
            if (playerId !== excludeId && clients.has(playerId)) {
                clients.get(playerId).send(JSON.stringify(payload));
            }
        }
    }
}

export function getRoomInstance(roomCode) {
    return roomInstances.get(roomCode);
}

export function removePlayerFromRooms(playerId) {
    for (const [roomCode, room] of roomInstances.entries()) {
        if (room.players.has(playerId)) {
            room.players.delete(playerId);
            console.log(`❌ Jugador ${playerId} eliminado de sala ${roomCode}`);
            if (room.players.size === 0) {
                roomInstances.delete(roomCode);
                console.log(`🧹 Instancia de sala ${roomCode} eliminada (vacía)`);
            }
        }
    }
}