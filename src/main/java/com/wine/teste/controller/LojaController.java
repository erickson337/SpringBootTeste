package com.wine.teste.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.wine.teste.model.Loja;
import com.wine.teste.repository.LojaRepository;

@RestController
@RequestMapping("/lojas")
public class LojaController {

	@Autowired
	private LojaRepository lojaRepository;
	
	@GetMapping
	public List<Loja> getAll() {
		return lojaRepository.findAll();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Loja store(@RequestBody Loja loja) throws Exception {
		try {
			return lojaRepository.save(loja);
		} catch (Exception e) {
			throw new Exception("Não foi possível cadastrar esta loja");
		}
	}
}
