package com.purchaseOrders.api.domain.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.purchaseOrders.api.domain.model.Direction;

import com.purchaseOrders.api.domain.repository.DirectionRepository;

@Service
public class DirectionService {

	private DirectionRepository repository;
	
	public DirectionService(DirectionRepository repository){
		this.repository = repository;
	}
	
	public List<Direction> listDirections(){
		return repository.findAll();
	}
	
	public Direction searchDirection(Long directionId) {
		Optional<Direction> directionCurrent = repository.findById(directionId);
		
		return directionCurrent.get();
	}
	
	public Direction addDirection(Direction direction) {
		return repository.save(direction);
	}
	
	public void removeDirection(Long directionId) {
		Optional<Direction> directionCurrent = repository.findById(directionId);
		
		repository.delete(directionCurrent.get());
		}
	
	public Direction updateDirection(Direction direction, Long directionId) {
		Optional<Direction> directionCurrent = repository.findById(directionId);
		
		if(directionCurrent.isEmpty()) 
		  { 
			  throw new NoSuchElementException(); 
		  }
		  BeanUtils.copyProperties(direction, directionCurrent.get(), "id"); 
		  
		return repository.save(directionCurrent.get());
	}
}
