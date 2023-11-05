package br.com.marcosceola.tablesbackend.service;

import br.com.marcosceola.tablesbackend.dto.MessageReceivedDTO;
import br.com.marcosceola.tablesbackend.model.Message;
import br.com.marcosceola.tablesbackend.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private MessageRepository repository;

    public Message save(MessageReceivedDTO messageReceived) {
        var message = repository.save(messageReceived.toMessage());

        return message;
    }

    public Page<Message> findRoomMessages(String roomId, Pageable pageable) {
        return repository.findByRoomId(roomId, pageable);
    }

}
