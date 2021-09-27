package com.wine.teste.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.Data;

@Data
@Entity
public class Cep {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(nullable = false)
	private String codigoLoja;
	@Column(nullable = false)
	@Min(value = 01001000, message = "Faixa inicial inválido")
	private long faixaInicio;
	
	@Column(nullable = false)
	@Max(value = 99999999, message = "Faixa final inválido")
	private long faixaFim;
	
	// @ManyToOne
	// @JoinColumn(name = "codigoLoja", referencedColumnName = "codigoLoja", insertable = false, updatable = false)
	// private Loja loja;

}
