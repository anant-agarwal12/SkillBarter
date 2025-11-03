package com.skillbarter.backend.service;

import com.skillbarter.backend.model.Message;
import com.skillbarter.backend.model.User;
import com.skillbarter.backend.repository.MessageRepository;
import com.skillbarter.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageService {
    
    @Autowired
    private MessageRepository messageRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public Message sendMessage(Long senderId, Long receiverId, String content) {
        Optional<User> senderOpt = userRepository.findById(senderId);
        Optional<User> receiverOpt = userRepository.findById(receiverId);
        
        if (senderOpt.isEmpty() || receiverOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        
        Message message = new Message(senderOpt.get(), receiverOpt.get(), content);
        return messageRepository.save(message);
    }
    
    public List<Message> getConversation(Long userId1, Long userId2) {
        Optional<User> user1Opt = userRepository.findById(userId1);
        Optional<User> user2Opt = userRepository.findById(userId2);
        
        if (user1Opt.isEmpty() || user2Opt.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        
        return messageRepository.findBySenderOrReceiver(user1Opt.get(), user2Opt.get())
            .stream()
            .sorted((m1, m2) -> m1.getTimestamp().compareTo(m2.getTimestamp()))
            .collect(Collectors.toList());
    }
    
    public List<Message> getUnreadMessages(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(messageRepository::findByReceiverAndReadFalse).orElse(List.of());
    }
    
    public void markAsRead(Long messageId) {
        Optional<Message> messageOpt = messageRepository.findById(messageId);
        if (messageOpt.isPresent()) {
            Message message = messageOpt.get();
            message.setRead(true);
            messageRepository.save(message);
        }
    }
    
    public List<User> getConversationPartners(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return List.of();
        }
        
        List<Message> messages = messageRepository.findBySenderOrReceiver(user.get(), user.get());
        return messages.stream()
            .map(m -> m.getSender().getId().equals(userId) ? m.getReceiver() : m.getSender())
            .distinct()
            .collect(Collectors.toList());
    }
}

