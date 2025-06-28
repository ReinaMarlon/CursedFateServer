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

// WebSocket handling
wss.on('connection', (ws) => {
  console.log("📡 Nuevo WebSocket conectado");

  ws.on('message', (message) => {
    console.log("📩 Mensaje RAW:", message.toString()); // Asegúrate de convertir a string
    const connectionTimeout = setTimeout(() => {
      if (!ws.cfgame_id) {
          console.log("⌛ Timeout: Conexión no identificada");
          ws.close();
      }
  }, 5000);
    try {
      const data = JSON.parse(message.toString());

      // Mensaje de identificación
      if (data.type === 'identify') {
        if (!data.cfgame_id || !data.room_code) {
          throw new Error('Faltan campos requeridos');
        }

        console.log(`🎯 Identificando cliente ${data.cfgame_id} en sala ${data.room_code}`);

        // Asignar identificadores
        ws.cfgame_id = data.cfgame_id;
        ws.room_code = data.room_code;

        // Registrar en las estructuras de datos
        clients.set(data.cfgame_id, ws);
        addPlayerToRoom(data.room_code, data.cfgame_id);

        // Respuesta de confirmación
        ws.send(JSON.stringify({
          type: 'connection_ack',
          status: 'success',
          cfgame_id: data.cfgame_id,
          room_code: data.room_code,
          timestamp: Date.now(),
          keepalive: true
        }));

        // Notificar a otros jugadores
        broadcastToRoom(data.room_code, clients, {
          type: 'player_joined',
          cfgame_id: data.cfgame_id,
          timestamp: Date.now()
        });

        return;
      }

      // Otros tipos de mensajes...

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
      // Notificar que el jugador se fue ANTES de eliminarlo
      broadcastToRoom(ws.room_code, clients, {
        type: "player_left",
        cfgame_id: ws.cfgame_id,
        timestamp: Date.now()
      });

      // Luego limpiar los datos
      clients.delete(ws.cfgame_id);
      removePlayerFromRooms(ws.cfgame_id);
    }
  });
});

const PORT = process.env.PORT || 3000;
server.listen(PORT, () => {
  console.log(`🚀 Servidor WebSocket corriendo en puerto ${PORT}`);
});
