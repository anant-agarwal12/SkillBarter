package com.skillbarter.backend.controller;

import com.skillbarter.backend.model.Message;
import com.skillbarter.backend.model.User;
import com.skillbarter.backend.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin(origins = "*")
public class MessageController {
    
    @Autowired
    private MessageService messageService;
    
    @PostMapping
    public Message sendMessage(@RequestParam Long senderId,
                               @RequestParam Long receiverId,
                               @RequestParam String content) {
        return messageService.sendMessage(senderId, receiverId, content);
    }
    
    @GetMapping("/conversation")
    public List<Message> getConversation(@RequestParam Long userId1, @RequestParam Long userId2) {
        return messageService.getConversation(userId1, userId2);
    }
    
    @GetMapping("/user/{userId}/unread")
    public List<Message> getUnreadMessages(@PathVariable Long userId) {
        return messageService.getUnreadMessages(userId);
    }
    
    @PostMapping("/{messageId}/read")
    public void markAsRead(@PathVariable Long messageId) {
        messageService.markAsRead(messageId);
    }
    
    @GetMapping("/user/{userId}/partners")
    public List<User> getConversationPartners(@PathVariable Long userId) {
        return messageService.getConversationPartners(userId);
    }
}

