package br.com.marcosceola.tablesbackend.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Document(collection = "messages")
public class Message {

    @Id
    private ObjectId id;

    @NonNull
    private String senderUsername;

    @NonNull
    private String textMessage;

    @Indexed
    @NonNull
    private String roomId;

    private Instant createdAt = Instant.now();
}
