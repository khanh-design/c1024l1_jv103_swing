package com.example.bai_tap_lon_back_end.service.impl;

import com.example.bai_tap_lon_back_end.model.Transaction;
import com.example.bai_tap_lon_back_end.repository.ITransactionRepository;
import com.example.bai_tap_lon_back_end.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService implements ITransactionService {
    @Autowired
    private ITransactionRepository iTransactionRepository;

    @Override
    public Iterable<Transaction> findAll() {
        return iTransactionRepository.findAll();
    }

    @Override
    public Optional<Transaction> findById(Long id) {
        return iTransactionRepository.findById(id);
    }

    @Override
    public Transaction save(Transaction transaction) {
        return iTransactionRepository.save(transaction);
    }

    @Override
    public void delete(Long id) {
        iTransactionRepository.deleteById(id);
    }
}
