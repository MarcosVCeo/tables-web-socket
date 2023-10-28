package br.com.marcosceola.tablesbackend.config;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@org.springframework.context.annotation.Configuration
@EnableWebSocket
public class WebSocketConfig {

    @Value("${web-socket-server.host}")
    private String hostName;

    @Value("${web-socket-server.port}")
    private Integer port;

    @Bean
    public SocketIOServer socketIOServer() {
        var config = new Configuration();
        config.setHostname(hostName);
        config.setPort(port);

        return new SocketIOServer(config);
    }
}
