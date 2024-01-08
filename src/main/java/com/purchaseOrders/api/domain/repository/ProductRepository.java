package com.purchaseOrders.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.purchaseOrders.api.domain.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
