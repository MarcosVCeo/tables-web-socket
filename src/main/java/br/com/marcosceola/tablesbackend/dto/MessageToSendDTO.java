package br.com.marcosceola.tablesbackend.dto;

import br.com.marcosceola.tablesbackend.model.Message;

public record MessageToSendDTO(
        String id,
        String roomId,
        String senderUsername,
        String textMessage
) {
    public static MessageToSendDTO valueOf(Message messageToSendDTO) {
        return new MessageToSendDTO(
                messageToSendDTO.getId().toString(),
                messageToSendDTO.getRoomId(),
                messageToSendDTO.getSenderUsername(),
                messageToSendDTO.getTextMessage());
    }
}
