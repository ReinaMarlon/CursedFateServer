// routes/api/shopData.js
import express from 'express';
import db from '../../db.js';

const shopDataRouter = express.Router();

shopDataRouter.get('/api/shop/full-data', async (req, res) => {
    try {
        const [crurefythaPacks] = await db.query(`SELECT * FROM crurefytha_packs`);
        const [nyversPacks] = await db.query(`SELECT * FROM nyvers_packs`);
        const [shopFeatures] = await db.query(`SELECT * FROM shop_features`);
        const [shopProfilePics] = await db.query(`SELECT * FROM shop_profile_pics`);
        const [shopSkins] = await db.query(`SELECT * FROM shop_skins`);

        res.json({
            crurefythaPacks,
            nyversPacks,
            shopFeatures,
            shopProfilePics,
            shopSkins
        });
    } catch (err) {
        console.error("❌ Error al obtener datos de tienda:", err);
        res.status(500).json({ error: "Error al obtener datos de tienda" });
    }
});

export default shopDataRouter;
