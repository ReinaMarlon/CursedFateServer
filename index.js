import express from 'express';
import http from 'http';
import { WebSocketServer } from 'ws';
import crypto from 'crypto';

import dotenv from 'dotenv';
dotenv.config();

import db from './db.js';
import auth from './game/login/auth.js';
import currentConfigs from './game/config/currentConfigs.js';
import createRoomRoute from './game/room/create.js';
import joinRoomRoute from './game/room/joinRoom.js';
import getPlayersRoom from './game/room/getPlayersRoom.js';
import mailRoutes from './game/mails/allMails.js';
import {
  addPlayerToRoom,
  broadcastToRoom,
  removePlayerFromRooms
} from './game/room/roomManager.js';

const app = express();
const server = http.createServer(app);
const wss = new WebSocketServer({ server });

export const clients = new Map();


// Middleware
app.use(express.json());
app.use(express.urlencoded({ extended: true }));


// Ruta simple de test
app.get('/', (req, res) => {
  res.send('Servidor WebSocket activo');
});


// Montar ruta de configuración
// app.use('/api/config', currentConfigs);
app.use(auth);
app.use(currentConfigs);
app.use(createRoomRoute);
app.use(joinRoomRoute);
app.use(getPlayersRoom);
app.use(mailRoutes);

// WebSocket handling
wss.on('connection', (ws) => {
  console.log("📡 Nuevo WebSocket conectado");

  ws.on('message', (message) => {
    console.log("📩 Mensaje RAW:", message.toString());

    try {
      const data = JSON.parse(message.toString());

      if (data.type === 'identify') {
        if (!data.cfgame_id || !data.room_code) {
          throw new Error('Faltan campos requeridos');
        }

        console.log(`🎯 Identificando cliente ${data.cfgame_id} en sala ${data.room_code}`);

        ws.cfgame_id = data.cfgame_id;
        ws.room_code = data.room_code;

        clients.set(data.cfgame_id, ws);
        addPlayerToRoom(data.room_code, data.cfgame_id);

        ws.send(JSON.stringify({
          type: 'connection_ack',
          status: 'success',
          cfgame_id: data.cfgame_id,
          room_code: data.room_code,
          timestamp: Date.now(),
          keepalive: true
        }));

        broadcastToRoom(data.room_code, clients, {
          type: 'player_joined',
          cfgame_id: data.cfgame_id,
          timestamp: Date.now()
        });
      } else if (data.type === 'greeting') {
        console.log(`🤝 Mensaje de saludo: ${data.content}`);
        // puedes responder si quieres
        ws.send(JSON.stringify({ type: "ack", message: "Hola Unity!" }));
      }

    } catch (err) {
      console.error("❌ Error procesando mensaje:", err);
      ws.send(JSON.stringify({
        type: 'error',
        message: err.message || 'Error procesando mensaje'
      }));
    }
  });

  ws.on('close', () => {
    if (ws.cfgame_id && ws.room_code) {
      broadcastToRoom(ws.room_code, clients, {
        type: "player_left",
        cfgame_id: ws.cfgame_id,
        timestamp: Date.now()
      });

      clients.delete(ws.cfgame_id);
      removePlayerFromRooms(ws.cfgame_id);
    }
  });
});


const PORT = process.env.PORT || 3000;
server.listen(PORT, () => {
  console.log(`🚀 Servidor WebSocket corriendo en puerto ${PORT}`);
});
