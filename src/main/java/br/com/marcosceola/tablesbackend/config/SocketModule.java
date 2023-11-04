package br.com.marcosceola.tablesbackend.config;

import br.com.marcosceola.tablesbackend.model.Mensagem;
import br.com.marcosceola.tablesbackend.service.SocketService;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

@Component
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
            client.joinRoom(client.getHandshakeData().getSingleUrlParam("mesa"));
            client.set("username", client.getHandshakeData().getSingleUrlParam("username"));
            System.out.println(String.format("Socket ID [%s] connected to socket", client.getSessionId()));
        };
    }

    private DisconnectListener onDisconnect() {
        return client -> {
            System.out.println(String.format("Socket ID [%s] disconnected from socket", client.getSessionId()));
        };
    }

    private DataListener<Mensagem> onReceiveMessage() {
        return (client, message, ackSender) -> {
            System.out.println("mensagem recebida " + message.getMensagem());
            socketService.sendRoomMessage(message.getMesa(), "receber_mensagem", client, message.getMensagem());
        };
    }

    @PreDestroy
    public void destroy() {
        server.stop();
    }
}
