package com.purchaseOrders.api.controller;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import com.purchaseOrders.api.domain.model.Direction;

import com.purchaseOrders.api.domain.service.DirectionService;

@RestController
@RequestMapping("/directions")
public class DirectionController {

	public DirectionService service;
	
	public DirectionController(DirectionService service) {
		this.service = service;
	}
	@ResponseStatus(code = HttpStatus.OK)
	@GetMapping
	public List<Direction> listDirection(){
		return service.listDirections();
	}
	
	@GetMapping("/{directionId}")
	public ResponseEntity<Direction> searchProduct(@PathVariable Long directionId){
		
			Direction directionCurrent = service.searchDirection(directionId);
			return ResponseEntity.status(HttpStatus.FOUND).body(directionCurrent);
		
	}
	
	@PostMapping
	public ResponseEntity<Direction> addDirection(@RequestBody Direction direction){
		Direction directionSave = service.addDirection(direction);
		return ResponseEntity.status(HttpStatus.CREATED).body(directionSave);
	}
	
	@DeleteMapping("/{directionId}")
	public ResponseEntity<Direction> removeProduct(@PathVariable Long directionId){
		
			service.removeDirection(directionId);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		
	}
	
	@PutMapping("/{directionId}")
	public ResponseEntity<Direction> updateDirection(@RequestBody Direction direction, @PathVariable Long directionId){
		
		
			Direction directionSave  = service.updateDirection(direction, directionId);
			return ResponseEntity.status(HttpStatus.OK).body(directionSave);
		
	}
}
