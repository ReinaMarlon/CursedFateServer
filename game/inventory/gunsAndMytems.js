import express from 'express';
import db from '../../db.js';
const gunsRouter = express.Router();

gunsRouter.get('/api/guns-and-mytems', async (req, res) => {
    try {
        const [guns] = await db.query(`SELECT * FROM guns`);
        const [mytems] = await db.query(`SELECT * FROM mytem`);

        res.json({
            guns,
            mytems
        });
    } catch (err) {
        console.error('Error al obtener guns/mytems:', err);
        res.status(500).json({ error: 'Error interno del servidor' });
    }
});

export default gunsRouter;
