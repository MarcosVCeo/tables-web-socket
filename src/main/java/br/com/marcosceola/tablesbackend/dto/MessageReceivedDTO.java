package br.com.marcosceola.tablesbackend.dto;

import br.com.marcosceola.tablesbackend.model.Message;

public record MessageReceivedDTO(
        String roomId,
        String senderUsername,
        String textMessage
) {

    public Message toMessage() {
        return new Message(this.senderUsername(), this.textMessage(), this.roomId());
    }
}
