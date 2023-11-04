package br.com.marcosceola.tablesbackend.config;

import br.com.marcosceola.tablesbackend.model.Mensagem;
import br.com.marcosceola.tablesbackend.service.SocketService;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import jakarta.annotation.PreDestroy;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class SocketModule {

    private final SocketIOServer server;
    private final SocketService socketService;

    public SocketModule(SocketIOServer server, SocketService socketService) {
        this.server = server;
        this.socketService = socketService;

        server.addConnectListener(onConnect());
        server.addDisconnectListener(onDisconnect());
        server.addEventListener("enviar_mensagem", Mensagem.class, onReceiveMessage());
    }


    private ConnectListener onConnect() {
        return client -> {
            var mesa = client.getHandshakeData().getSingleUrlParam("mesa");
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

    private DataListener<Mensagem> onReceiveMessage() {
        return (client, message, ackSender) -> {
            socketService.sendRoomMessage(message.getMesa(), "receber_mensagem", client, message.getMensagem());
        };
    }

    @PreDestroy
    public void destroy() {
        server.stop();
    }
}
