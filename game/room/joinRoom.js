
import express from 'express';
import db from '../../db.js';
import { addPlayerToRoom, broadcastToRoom } from './roomManager.js';

const router = express.Router();

router.post('/api/room/join', async (req, res) => {
  const { room_code, cfgame_id } = req.body;

  if (!room_code || !cfgame_id) {
    return res.status(400).json({ success: false, message: 'Datos insuficientes' });
  }

  try {
    const [roomRows] = await db.execute('SELECT id FROM rooms WHERE code = ?', [room_code]);
    if (roomRows.length === 0) {
      return res.status(404).json({ success: false, message: 'Sala no encontrada' });
    }

    const room_id = roomRows[0].id;

    // Verificamos si ya está unido
    const [exists] = await db.execute(
      'SELECT * FROM room_players WHERE room_id = ? AND player_id = ?',
      [room_id, cfgame_id]
    );

    if (exists.length === 0) {
      await db.execute(
        'INSERT INTO room_players (room_id, player_id, is_host, is_ready, selected_character_id) VALUES (?, ?, 0, 0, null)',
        [room_id, cfgame_id]
      );
    }

    return res.json({ success: true });
  } catch (err) {
    console.error('❌ Error al unir jugador:', err);
    return res.status(500).json({ success: false, message: 'Error del servidor' });
  }
});

export default router;
