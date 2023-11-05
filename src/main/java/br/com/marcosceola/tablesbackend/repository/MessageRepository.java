package br.com.marcosceola.tablesbackend.repository;

import br.com.marcosceola.tablesbackend.model.Message;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends MongoRepository<Message, ObjectId> {

    Page<Message> findByRoomId(String roomId, Pageable pageable);

}
