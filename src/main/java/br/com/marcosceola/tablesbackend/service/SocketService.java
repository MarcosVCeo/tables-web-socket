package br.com.marcosceola.tablesbackend.service;

import br.com.marcosceola.tablesbackend.dto.MessageToSendDTO;
import br.com.marcosceola.tablesbackend.model.Message;
import com.corundumstudio.socketio.SocketIOClient;
import org.springframework.stereotype.Service;

@Service
public class SocketService {

    public void sendRoomMessage(String eventName, SocketIOClient senderClient, Message message) {
        var messageToSend = MessageToSendDTO.valueOf(message);

        senderClient
                .getNamespace()
                .getRoomOperations(message.getRoomId())
                .getClients()
                .forEach(client -> client.sendEvent(eventName, messageToSend));
    }
}
