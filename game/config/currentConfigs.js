import express from 'express';
import db from '../../db.js';

const router = express.Router();
console.log("🧪 Tipo de db:", typeof db);
console.log("🧪 Claves de db:", Object.keys(db));
router.get('/api/config/', async (req, res) => {

  try {
    const [rows] = await db.execute(`
      SELECT CurrentVersion, maxPlayersPerRoom, DeploymentType FROM configs LIMIT 1`);

    if (rows.length > 0) {
      res.json({ Config: rows[0] });
    } else {
      res.status(404).json({ error: "No se encontró configuración" });
    }
  } catch (err) {
    console.error("Error en /api/config:", err);
    res.status(500).json({ error: "Error en el servidor" });
  }
});

export default router;
