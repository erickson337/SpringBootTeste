package com.wine.teste.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wine.teste.model.Loja;

@Repository
public interface LojaRepository extends JpaRepository<Loja, Long> {
	Loja findByCodigoLoja(String codigoLoja);
}
