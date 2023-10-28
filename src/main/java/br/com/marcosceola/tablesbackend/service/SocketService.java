package br.com.marcosceola.tablesbackend.service;

import br.com.marcosceola.tablesbackend.model.Mensagem;
import com.corundumstudio.socketio.SocketIOClient;
import org.springframework.stereotype.Service;

@Service
public class SocketService {

    public void sendMessage(String mesa, String eventName, SocketIOClient senderClient, String message) {
        for (SocketIOClient client : senderClient.getNamespace().getRoomOperations(mesa).getClients()) {
            client.sendEvent(eventName, new Mensagem(senderClient.get("username"), message));
        }
    }
}
