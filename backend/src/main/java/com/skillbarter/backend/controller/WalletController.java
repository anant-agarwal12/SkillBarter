package com.skillbarter.backend.controller;

import com.skillbarter.backend.model.Transaction;
import com.skillbarter.backend.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wallet")
@CrossOrigin(origins = "*")
public class WalletController {
    
    @Autowired
    private WalletService walletService;
    
    @GetMapping("/{userId}/balance")
    public Integer getBalance(@PathVariable Long userId) {
        return walletService.getBalance(userId);
    }
    
    @GetMapping("/{userId}/transactions")
    public List<Transaction> getTransactionHistory(@PathVariable Long userId) {
        return walletService.getTransactionHistory(userId);
    }
    
    @GetMapping("/{userId}/earnings")
    public List<Transaction> getEarnings(@PathVariable Long userId) {
        return walletService.getEarnings(userId);
    }
    
    @GetMapping("/{userId}/spending")
    public List<Transaction> getSpending(@PathVariable Long userId) {
        return walletService.getSpending(userId);
    }
}

