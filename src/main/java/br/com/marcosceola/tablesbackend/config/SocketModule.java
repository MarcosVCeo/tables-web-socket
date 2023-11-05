package br.com.marcosceola.tablesbackend.config;

import br.com.marcosceola.tablesbackend.dto.MessageReceivedDTO;
import br.com.marcosceola.tablesbackend.service.MessageService;
import br.com.marcosceola.tablesbackend.service.SocketService;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import jakarta.annotation.PreDestroy;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class SocketModule {

    private final SocketIOServer server;

    @Autowired
    private SocketService socketService;

    @Autowired
    private MessageService messageService;

    public SocketModule(SocketIOServer server, SocketService socketService) {
        this.server = server;

        server.addConnectListener(onConnect());
        server.addDisconnectListener(onDisconnect());
        server.addEventListener("enviar_mensagem", MessageReceivedDTO.class, onReceiveMessage());
    }


    private ConnectListener onConnect() {
        return client -> {
            var mesa = client.getHandshakeData().getSingleUrlParam("roomId");
            var username = client.getHandshakeData().getSingleUrlParam("username");

            log.info(String.format("Socket ID [%s] username[%s] connected to socket at room [%s]", client.getSessionId(), username, mesa));

            client.joinRoom(mesa);
            client.set("username", username);
        };
    }

    private DisconnectListener onDisconnect() {
        return client -> {
            log.info(String.format("Socket ID [%s] disconnected from socket", client.getSessionId()));
        };
    }

    private DataListener<MessageReceivedDTO> onReceiveMessage() {
        return (client, messageDTO, ackSender) -> {
            var message = messageService.save(messageDTO);

            socketService.sendRoomMessage("receber_mensagem", client, message);
        };
    }

    @PreDestroy
    public void destroy() {
        server.stop();
    }
}
