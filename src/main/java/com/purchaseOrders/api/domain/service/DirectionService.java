package com.purchaseOrders.api.domain.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.purchaseOrders.api.domain.exception.DirectionNotFoundException;
import com.purchaseOrders.api.domain.exception.EntityInUseException;

import com.purchaseOrders.api.domain.model.Direction;

import com.purchaseOrders.api.domain.repository.DirectionRepository;

@Service
public class DirectionService {

	@Autowired
	private DirectionRepository repository;
	
	
	public List<Direction> listDirections(){
		return repository.findAll();
	}
	
	public Direction searchDirection(Long directionId) {
		try {
			Optional<Direction> directionCurrent = repository.findById(directionId);
			return directionCurrent.get();
		}catch(NoSuchElementException ex) {
			throw new DirectionNotFoundException(directionId);
		}
		
	}
	
	public Direction addDirection(Direction direction) {
		return repository.save(direction);
	}
	
	public void removeDirection(Long directionId) {
		try {
			Optional<Direction> directionCurrent = repository.findById(directionId);
			
			repository.delete(directionCurrent.get());
			
		}catch(DataIntegrityViolationException ex) {
			throw new EntityInUseException(String.format("The code direction %s is in use.", directionId));
		}catch (NoSuchElementException ex) {
			throw new DirectionNotFoundException(directionId);
		}
	}
	
	public Direction updateDirection(Direction direction, Long directionId) {
		Optional<Direction> directionCurrent = repository.findById(directionId);
		
		try {
			if(directionCurrent.isEmpty()) 
			  { 
				  throw new NoSuchElementException(); 
			  }
			  BeanUtils.copyProperties(direction, directionCurrent.get(), "id"); 
			  
			return repository.save(directionCurrent.get());
			
		}catch (NoSuchElementException ex) {
			throw new DirectionNotFoundException(directionId);
		}
		
	}
}
