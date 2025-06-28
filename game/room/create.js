import express from 'express';
import db from '../../db.js';
import { createRoomInstance } from './roomManager.js';
import { clients } from '../../index.js';

const router = express.Router();



router.post('/api/room/create', async (req, res) => {
    const { cfgame_id } = req.body;

    if (!cfgame_id) {
        return res.status(400).json({ status: 'error', message: 'ID del jugador requerido' });
    }

    try {
        // Obtener ID real del jugador
        const [playerResult] = await db.execute("SELECT id FROM players WHERE cfgameID = ?", [cfgame_id]);
        if (playerResult.length === 0) {
            return res.status(404).json({ status: 'error', message: 'Jugador no encontrado' });
        }

        const player_id = playerResult[0].id;

        // Generar código único (ej: sala de 6 caracteres)
        const code = Math.random().toString(36).substring(2, 8).toUpperCase();
        const now = new Date();

        const [roomInsert] = await db.execute(
            "INSERT INTO rooms (code, host_id, host_ip, status, created_at) VALUES (?, ?, ?, ?, ?)",
            [code, cfgame_id, req.ip, 'Waiting', now]
        );

        const room_id = roomInsert.insertId;

        // Insertar en room_players como host
        await db.execute(
            "INSERT INTO room_players (room_id, player_id, is_host, is_ready) VALUES (?, ?, 1, 1)",
            [room_id, cfgame_id]
        );

        const hostSocket = clients.get(cfgame_id);
        if (hostSocket) {
            hostSocket.send(JSON.stringify({
                type: 'room_created',
                room_code: code,
                message: 'Has sido unido a tu sala como host.'
            }));
            console.log(`🧩 Host ${cfgame_id} unido a sala ${code}`);
        }

        createRoomInstance(code, cfgame_id);

        return res.json({
            status: 'success',
            message: 'Sala creada exitosamente',
            room_code: code
        });
    } catch (err) {
        console.error("❌ Error al crear sala:", err);
        return res.status(500).json({ status: 'error', message: 'Error interno del servidor' });
    }
});

export default router;
