import express from 'express';
import db from '../../db.js';
const allCharacterData = express.Router();


function safeParseJson(input) {
    if (typeof input === 'string') {
        try {
            return JSON.parse(input);
        } catch (e) {
            console.warn("No se pudo parsear:", input);
            return null;
        }
    }
    return input; // ya es objeto
}

allCharacterData.get('/api/characters/fulldata/', async (req, res) => {
    const playerId = req.query.player_id; 
    try {
        const [rows] = await db.query(`
        SELECT 
            c.id, c.name, c.health, c.attack, c.speed, c.defense, c.picture, c.closePic,
            fd.fullname, fd.age, fd.description, fd.birthday,
            fd.galleryPic, fd.fullbodyPic, fd.splashArt,
            fd.fragments,
            pfc.level, pfc.customStats, pfc.unlockedFragments, 
            pfc.lastUsed, pfc.isFavorite,
            g.name AS gunName, g.mytem_id AS gunMytemID, g.stats AS gunStats, g.history AS gunHistory,
            m.name AS mytemName, m.history AS mytemHistory
        FROM characters c
        JOIN characters_fulldata fd ON fd.id_character = c.id
        LEFT JOIN player_fulldata_characters pfc ON pfc.character_id = c.id AND pfc.player_id = ?
        LEFT JOIN guns g ON g.id = pfc.gun_id
        LEFT JOIN mytem m ON m.id = g.mytem_id
        `, [playerId]);

        const result = rows.map(row => ({
            id: row.id,
            name: row.name,
            health: row.health,
            attack: row.attack,
            speed: row.speed,
            defense: row.defense,
            picture: row.picture,
            closePic: row.closePic,

            fullname: row.fullname,
            age: row.age,
            description: row.description,
            birthday: row.birthday,
            galleryPic: row.galleryPic,
            fullbodyPic: row.fullbodyPic,
            splashArt: row.splashArt,

            fragments: safeParseJson(row.fragments || '[]'),

            playerData: {
                level: row.level ?? 1,
                customStats: safeParseJson(row.customStats || '{}'),
                unlockedFragments: safeParseJson(row.unlockedFragments || '[]'),
                lastUsed: row.lastUsed,
                isFavorite: !!row.isFavorite,
                equippedGunData: row.gunName ? {
                    name: row.gunName,
                    mytemId: row.gunMytemID,
                    stats: safeParseJson(row.gunStats || '{}'),
                    history: row.gunHistory,
                    mytemName: row.mytemName,
                    mytemDesc: row.mytemHistory
                } : null
            }
        }));

        res.json(result);
    } catch (err) {
        console.error('Error al obtener personajes:', err);
        res.status(500).json({ error: 'Error interno del servidor' });
    }
});


export default allCharacterData;
