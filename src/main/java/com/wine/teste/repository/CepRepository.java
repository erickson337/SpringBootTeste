package com.wine.teste.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wine.teste.model.Cep;

public interface CepRepository extends JpaRepository<Cep, Long> {
	List<Cep> findByCodigoLoja(String codigoLoja);
	
	@Query("select p from Cep p where :faixaInicio between p.faixaInicio and p.faixaFim or :faixaFim between p.faixaInicio and p.faixaFim")
	List<Cep> findByFaixaBetween(long faixaInicio, long faixaFim);
	
	@Query("select p from Cep p where :cep between p.faixaInicio and p.faixaFim")
	List<Cep> findByFaixaCepBetween(long cep);
}
