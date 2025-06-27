const express = require('express');
const http = require('http');
const { WebSocketServer } = require('ws');
const crypto = require('crypto');

const app = express();
const server = http.createServer(app);
const wss = new WebSocketServer({ server });

const clients = new Map();

app.get('/', (req, res) => {
  res.send('Servidor WebSocket activo');
});

wss.on('connection', (ws) => {
  const id = crypto.randomUUID();
  clients.set(id, ws);

  console.log("✅ Cliente conectado:", id);

  ws.on('message', (message) => {
    console.log("📩", message.toString());
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
  console.log(`🚀 Servidor WebSocket corriendo en puerto ${PORT}`);
});
