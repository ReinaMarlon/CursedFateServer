// ================= SERVER =================
// Archivo: routes/mailRoutes.js
import express from 'express';
import db from '../../db.js';

const router = express.Router();

// Obtener correos del jugador
router.get('/api/mails/:playerId', async (req, res) => {
  const playerId = parseInt(req.params.playerId);

  try {
    // 1. Correos globales (send_to_all = true)
    const [globalMails] = await db.execute(`
      SELECT *, NULL AS is_read, NULL AS is_claimed
      FROM mails
      WHERE send_to_all = TRUE
      ORDER BY created_at DESC
    `);

    // 2. Correos dirigidos al jugador
    const [personalMails] = await db.execute(`
      SELECT m.*, mt.is_read, mt.is_claimed
      FROM mails m
      JOIN mail_targets mt ON m.id = mt.mail_id
      WHERE mt.player_id = ?
      ORDER BY m.created_at DESC
    `, [playerId]);

    const allMails = [...globalMails, ...personalMails];

    res.json({ status: 'success', mails: allMails });
  } catch (err) {
    console.error("❌ Error al obtener mails:", err);
    res.status(500).json({ status: 'error', message: 'Error en el servidor' });
  }
});

export default router;
