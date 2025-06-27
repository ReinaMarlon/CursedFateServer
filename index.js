import http from 'http';
import { WebSocketServer } from 'ws';
import crypto from 'crypto';

const server = http.createServer(); // <- Esto permite que Railway enrute tráfico
const wss = new WebSocketServer({ server }); // <- Conecta WebSocket a ese server

const clients = new Map();

wss.on('connection', (ws) => {
  const id = crypto.randomUUID();
  clients.set(id, ws);

  console.log("✅ Cliente conectado:", id);

  ws.on('message', (message) => {
    console.log("📩 Mensaje:", message.toString());

    // Reenviar a todos los demás clientes
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

// ✅ Escuchar por el puerto que Railway asigna
const PORT = process.env.PORT || 3000;
server.listen(PORT, () => {
  console.log(`🚀 Servidor WebSocket activo en puerto ${PORT}`);
});
