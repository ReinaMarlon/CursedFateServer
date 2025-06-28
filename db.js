
// import mysql from 'mysql2/promise';

// const db = await mysql.createConnection({
//   host: process.env.DB_HOST,
//   user: "root",
//   // user: process.env.DB_USER,
//   password: process.env.DB_PASS,
//   database: "cf_game",
//   // database: process.env.DB_NAME,
//   port: 3306 
// });

// export default db;

import mysql from 'mysql2/promise';

const db = mysql.createPool({
  host: process.env.DB_HOST,
  user: process.env.DB_USER,
  password: process.env.DB_PASS,
  database: process.env.DB_NAME,
  port: 3306,
  waitForConnections: true,
  connectionLimit: 10,
  queueLimit: 0
});

export default db;
