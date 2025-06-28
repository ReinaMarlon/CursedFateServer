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

        // Guardar cfgameID
        await db.execute("UPDATE players SET cfgameID = ? WHERE id = ?", [cfgameID, newId]);

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
        "SELECT id, Password, cfgameID FROM players WHERE UserName = ?", [Username]
      );

      if (users.length === 1) {
        const user = users[0];
        const match = await bcrypt.compare(Password, user.Password);

        if (match) {
          const now = new Date();
          await db.execute("UPDATE players SET last_login = ? WHERE id = ?", [now, user.id]);

          return res.json({
            status: 'success',
            message: 'Access Ok',
            cfgame_id: user.cfgameID,
            username: Username
          });
        } else {
          return res.json({ status: 'error', message: 'Contraseña incorrecta' });
        }
      } else {
        return res.json({ status: 'error', message: 'Usuario no encontrado' });
      }

    } else {
      return res.status(400).json({ status: 'error', message: 'Operación no válida' });
    }
  } catch (err) {
    console.error("❌ Error en /api/login:", err);
    return res.status(500).json({ status: 'error', message: 'Error en el servidor' });
  }
});

export default router;
