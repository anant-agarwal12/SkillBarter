package com.skillbarter.backend.service;

import com.skillbarter.backend.model.Transaction;
import com.skillbarter.backend.model.User;
import com.skillbarter.backend.repository.TransactionRepository;
import com.skillbarter.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WalletService {
    
    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public Integer getBalance(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(User::getPoints).orElse(0);
    }
    
    public List<Transaction> getTransactionHistory(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(u -> transactionRepository.findByUserOrderByTimestampDesc(u)).orElse(List.of());
    }
    
    public List<Transaction> getEarnings(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(u -> transactionRepository.findByUserAndType(u, "EARNED")).orElse(List.of());
    }
    
    public List<Transaction> getSpending(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(u -> transactionRepository.findByUserAndType(u, "SPENT")).orElse(List.of());
    }
}

