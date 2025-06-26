import { WebSocketServer } from 'ws';

const wss = new WebSocketServer({ port: process.env.PORT || 3000 });

const clients = new Map();

wss.on('connection', (ws) => {
  const id = crypto.randomUUID();
  clients.set(id, ws);

  console.log("Cliente conectado:", id);

  ws.on('message', (message) => {
    console.log("Mensaje recibido:", message.toString());

    // Reenviar a todos
    for (let [otherId, otherWs] of clients.entries()) {
      if (otherId !== id && otherWs.readyState === 1) {
        otherWs.send(message);
      }
    }
  });

  ws.on('close', () => {
    clients.delete(id);
    console.log("Cliente desconectado:", id);
  });
});
