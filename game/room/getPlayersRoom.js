import express from 'express';
import db from '../../db.js';

const router = express.Router();

router.post('/api/room/players', async (req, res) => {
  const { room_code } = req.body;

  if (!room_code) {
    return res.status(400).json({ status: 'error', message: 'Código de sala requerido' });
  }

  try {
    // Verificamos si existe la sala
    const [roomResult] = await db.execute('SELECT id FROM rooms WHERE code = ?', [room_code]);
    if (roomResult.length === 0) {
      return res.status(404).json({ status: 'error', message: 'Sala no encontrada' });
    }

    const room_id = roomResult[0].id;

    // Obtener los jugadores de esa sala con información adicional
    const [players] = await db.execute(`
      SELECT 
        p.cfgameID AS cfgame_id,
        p.UserName AS username,
        pd.PictureProfile AS picture_profile,
        rp.is_host,
        rp.is_ready,
        rp.selected_character_id
      FROM room_players rp
      INNER JOIN players p ON rp.player_id = p.cfgameID
      LEFT JOIN players_data pd ON p.id = pd.player_id
      WHERE rp.room_id = ?
    `, [room_id]);

    return res.json({ players });

  } catch (err) {
    console.error("❌ Error en /api/room/players:", err);
    return res.status(500).json({ status: 'error', message: 'Error interno del servidor' });
  }
});

export default router;
