package com.purchaseOrders.api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.purchaseOrders.api.domain.model.Direction;

public interface DirectionRepository extends JpaRepository<Direction, Long>{

}
