import express from 'express';
import http from 'http';
import { WebSocketServer } from 'ws';
import crypto from 'crypto';

const app = express();
const server = http.createServer(app);
const wss = new WebSocketServer({ server });

app.get('/health', (req, res) => res.send('OK'));

const clients = new Map();

wss.on('connection', (ws) => {
  const id = crypto.randomUUID();
  clients.set(id, ws);

  console.log("✅ Cliente conectado:", id);

  ws.on('message', (message) => {
    console.log("📩 Mensaje:", message.toString());

    for (let [otherId, otherWs] of clients.entries()) {
      if (otherId !== id && otherWs.readyState === 1) {
        otherWs.send(message);
      }
    }
  });

  ws.on('close', () => {
    clients.delete(id);
    console.log("❌ Cliente desconectado:", id);
  });
});

const PORT = process.env.PORT || 3000;
server.listen(PORT, () => {
  console.log(`🚀 Servidor WebSocket activo en puerto ${PORT}`);
});
