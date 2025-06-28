
import mysql from 'mysql2/promise';

const db = await mysql.createConnection({
  host: process.env.DB_HOST,
  user: "root",
  // user: process.env.DB_USER,
  password: process.env.DB_PASS,
  database: "cf_game",
  // database: process.env.DB_NAME,
  port: 3306 
});

export default db;
