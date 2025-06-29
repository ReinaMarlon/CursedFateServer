import express from 'express';
import db from '../../db.js';
import bcrypt from 'bcrypt';

const router = express.Router();

router.post('/api/auth', async (req, res) => {
  const { TypeOperation, Username, Password, Mail } = req.body;

  if (!TypeOperation || !Username || !Password) {
    return res.status(400).json({ status: 'error', message: 'Datos incompletos' });
  }

  try {
    if (TypeOperation === 'register') {
      const [existing] = await db.execute(
        "SELECT id FROM players WHERE UserName = ?", [Username]
      );

      if (existing.length > 0) {
        return res.json({ status: 'error', message: 'Usuario ya existe' });
      }

      const hashed = await bcrypt.hash(Password, 10);
      const now = new Date();

      const [insertResult] = await db.execute(
        "INSERT INTO players (UserName, Password, level, created_at) VALUES (?, ?, 1, ?)",
        [Username, hashed, now]
      );

      if (insertResult.affectedRows === 1) {
        const newId = insertResult.insertId;
        const cfgameID = 10000 + newId;

        await db.execute("UPDATE players SET cfgameID = ? WHERE id = ?", [cfgameID, newId]);

        // Crear también el registro en players_data
        await db.execute(
          `INSERT INTO players_data (player_id, Nyvers, Cruferytha, Characters_Count, Hours_Played,
            Rooms_Created, Max_Wave, Total_Enemy_Kills, Total_Boss_Kills,
            Achievments, SRPity, SSRPity, SSRW, SRW, PictureProfile)
           VALUES (?, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)`,
          [newId]
        );

        return res.json({
          status: 'success',
          message: 'Registro exitoso',
          cfgame_id: cfgameID
        });
      } else {
        return res.json({ status: 'error', message: 'Error al registrar' });
      }

    } else if (TypeOperation === 'login') {
      const [users] = await db.execute(
        "SELECT * FROM players WHERE UserName = ?", [Username]
      );

      if (users.length === 1) {
        const user = users[0];
        const match = await bcrypt.compare(Password, user.Password);

        if (!match) {
          return res.json({ status: 'error', message: 'Contraseña incorrecta' });
        }

        const now = new Date();
        await db.execute("UPDATE players SET last_login = ? WHERE id = ?", [now, user.id]);

        // Obtener también los datos de players_data
        const [data] = await db.execute(
          "SELECT * FROM players_data WHERE player_id = ?", [user.id]
        );

        const pdata = data.length > 0 ? data[0] : {};

        return res.json({
          status: 'success',
          message: 'Access Ok',

          // Datos de players
          player_id: user.id,
          cfgame_id: user.cfgameID,
          username: user.UserName,
          level: user.level,
          last_login: user.last_login,
          created_at: user.created_at,
          status: user.Status,

          // Datos de players_data
          nyvers: pdata.Nyvers || 0,
          cruferytha: pdata.Cruferytha || 0,
          characters_count: pdata.Characters_Count || 0,
          hours_played: pdata.Hours_Played || 0,
          rooms_created: pdata.Rooms_Created || 0,
          max_wave: pdata.Max_Wave || 0,
          total_enemy_kills: pdata.Total_Enemy_Kills || 0,
          total_boss_kills: pdata.Total_Boss_Kills || 0,
          achievments: pdata.Achievments || 0,
          srPity: pdata.SRPity || 0,
          ssrPity: pdata.SSRPity || 0,
          ssrw: pdata.SSRW || 0,
          srw: pdata.SRW || 0,
          pictureProfile: pdata.PictureProfile || "UsefulPack/CursedFate_DefaultUserIcon"
        });
      } else {
        return res.json({ status: 'error', message: 'Usuario no encontrado' });
      }

    } else {
      return res.status(400).json({ status: 'error', message: 'Operación no válida' });
    }
  } catch (err) {
    console.error("❌ Error en /api/auth:", err);
    return res.status(500).json({ status: 'error', message: 'Error en el servidor' });
  }
});

export default router;
