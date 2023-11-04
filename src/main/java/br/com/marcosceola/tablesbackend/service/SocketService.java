package br.com.marcosceola.tablesbackend.service;

import br.com.marcosceola.tablesbackend.model.Mensagem;
import com.corundumstudio.socketio.SocketIOClient;
import org.springframework.stereotype.Service;

@Service
public class SocketService {

    public void sendRoomMessage(String mesa, String eventName, SocketIOClient senderClient, String message) {
        for (var client : senderClient.getNamespace().getRoomOperations(mesa).getClients()) {
            client.sendEvent(eventName, new Mensagem(senderClient.get("username"), message, mesa));
        }
    }
}
