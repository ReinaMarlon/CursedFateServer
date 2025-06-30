import express from 'express';
import db from '../../db.js';
const allCharacterData = express.Router();

allCharacterData.get('/api/characters/fulldata/', async (req, res) => {
  try {
    const [rows] = await db.query(`
      SELECT 
        c.id, c.name, c.health, c.attack, c.defense, c.picture, c.closePic,
        fd.fullname, fd.age, fd.description, fd.birthday,
        fd.galleryPic, fd.fullbodyPic, fd.splashArt, fd.fragments, fd.gunInfo
      FROM characters c
      JOIN characters_fulldata fd ON fd.id_character = c.id
    `);
//really..
    const result = rows.map(row => ({
      id: row.id,
      name: row.name,
      health: row.health,
      attack: row.attack,
      defense: row.defense,
      picture: row.picture,
      closePic: row.closePic,

      fullname: row.fullname,
      age: row.age,
      description: row.description,
      birthday: row.birthday,
      galleryPic: row.galleryPic,
      fullbodyPic: row.fullbodyPic,

      fragments: JSON.parse(row.fragments),
      gunInfo: JSON.parse(row.gunInfo)
    }));

    res.json(result);
  } catch (err) {
    console.error('Error al obtener personajes:', err);
    res.status(500).json({ error: 'Error interno del servidor' });
  }
});

export default allCharacterData;
