package com.purchaseOrders.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.purchaseOrders.api.domain.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

}
