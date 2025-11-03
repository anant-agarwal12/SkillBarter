package com.skillbarter.backend.repository;

import com.skillbarter.backend.model.Transaction;
import com.skillbarter.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);
    List<Transaction> findByUserOrderByTimestampDesc(User user);
    List<Transaction> findByType(String type);
    List<Transaction> findByUserAndType(User user, String type);
}

