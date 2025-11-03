package com.skillbarter.backend.repository;

import com.skillbarter.backend.model.Message;
import com.skillbarter.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySender(User sender);
    List<Message> findByReceiver(User receiver);
    List<Message> findBySenderOrReceiver(User user1, User user2);
    List<Message> findBySenderAndReceiver(User sender, User receiver);
    List<Message> findByReceiverAndReadFalse(User receiver);
}

