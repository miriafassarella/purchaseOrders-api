package com.purchaseOrders.api.domain.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Entity
public class Direction {
	
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String principal;
	
	@Column(name = "code_budgetaire")
	private String codeBudgetaire;
	
	@Column(name = "name_school")
	private String nameSchool;
	
	@Column(name = "number_school")
	private Integer numberSchool;

}
