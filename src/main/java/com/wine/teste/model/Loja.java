package com.wine.teste.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Loja {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, unique = true)
	private String codigoLoja;
	
	@Column(nullable = false)
	private String nome;
	
	// @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)  
    // private List<Cep> ceps;
	
}
