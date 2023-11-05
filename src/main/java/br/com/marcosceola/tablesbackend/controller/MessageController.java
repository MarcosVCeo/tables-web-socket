package br.com.marcosceola.tablesbackend.controller;

import br.com.marcosceola.tablesbackend.dto.MessageToSendDTO;
import br.com.marcosceola.tablesbackend.service.MessageService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("/messages")
    public ResponseEntity<?> listMessages(@PathParam("roomId") String roomId, @PageableDefault(size = 10) Pageable pageable) {
        var page = messageService
                .findRoomMessages(roomId, pageable)
                .map(MessageToSendDTO::valueOf);

        return ResponseEntity.ok(page);
    }
}
