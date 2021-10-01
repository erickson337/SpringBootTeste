package com.wine.teste.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.wine.teste.model.Cep;
import com.wine.teste.model.Loja;
import com.wine.teste.repository.CepRepository;
import com.wine.teste.repository.LojaRepository;

@RestController
@RequestMapping("/ceps")
public class CepController {
	
	@Autowired
	private CepRepository cepRepository;
	
	@Autowired
	private LojaRepository lojaRepository;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Object> store(@RequestBody Cep cep) {
		try {
			List<Cep> ceps = cepRepository.findByFaixaBetween(cep.getFaixaInicio(), cep.getFaixaFim());
			
			if (cep.getFaixaInicio() >= cep.getFaixaFim()) {
				return new ResponseEntity<Object>("Faixa de cep inválida!", HttpStatus.BAD_REQUEST); 
			}
			
			String codigoLoja = cep.getCodigoLoja();
			
			if (codigoLoja != null && !codigoLoja.trim().equals("")) {
				Loja loja = lojaRepository.findByCodigoLoja(codigoLoja);
				
				if (loja == null) {
					return new ResponseEntity<Object>("Código de Loja não encontrado!", HttpStatus.BAD_REQUEST);
				}
			}
			
			if (ceps.size() == 0) {
				Cep cepNew = cepRepository.save(cep);
				return new ResponseEntity<Object>(cepNew, HttpStatus.OK);			
			}
			
			return new ResponseEntity<Object>("Faixa de cep já cadastrada!", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Não foi possível cadastrar este cep!", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping
	public ResponseEntity<Object> getAll() {
		List<Cep> ceps = cepRepository.findAll();
		return new ResponseEntity<Object>(ceps, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> delete(@PathVariable long id) {
		try {
			cepRepository.deleteById(id);
			return new ResponseEntity<Object>("Cep deletado com sucesso!", HttpStatus.OK);			
		} catch (Exception e) {
			return new ResponseEntity<Object>("Não foi possível excluir este cep, tente novamente!", HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping
	public ResponseEntity<Object> edit(@RequestBody Cep cep) {
		try {
			if (cep.getFaixaInicio() >= cep.getFaixaFim()) {
				return new ResponseEntity<Object>("Faixa de cep inválida!", HttpStatus.BAD_REQUEST); 
			}
			
			if (cep.getId() == 0) {
				return new ResponseEntity<Object>("Id de cep inválido!", HttpStatus.BAD_REQUEST); 				
			}
			
			String codigoLoja = cep.getCodigoLoja();
			if (codigoLoja != null && !codigoLoja.trim().equals("")) {
				Loja loja = lojaRepository.findByCodigoLoja(codigoLoja);
				
				if (loja == null) {
					return new ResponseEntity<Object>("Código de Loja não encontrado!", HttpStatus.BAD_REQUEST);
				}
			}
			
			List<Cep> ceps = cepRepository.findByIdFaixaBetween(cep.getId(), cep.getFaixaInicio(), cep.getFaixaFim());

			if (ceps.size() != 0) {
				return new ResponseEntity<Object>("Faixa de cep já cadastrada!", HttpStatus.BAD_REQUEST);
			}
			Cep cepEdit = cepRepository.save(cep);
			return new ResponseEntity<Object>(cepEdit, HttpStatus.OK);				
		} catch (Exception e) {
			return new ResponseEntity<Object>("Não foi possível editar este cep, tente novamente!", HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/loja/{codigo}")
	public ResponseEntity<Object> cepFromCodeLoja(@PathVariable String codigo) {
		List<Cep> result = cepRepository.findByCodigoLoja(codigo);
		if (result.size() > 0) {
			return new ResponseEntity<Object>(result, HttpStatus.OK); 
		}
		return new ResponseEntity<Object>("Não foi possível localizar está loja", HttpStatus.NOT_FOUND); 
	}
	
	@GetMapping("/{cep}")
	public ResponseEntity<Object> cepFromLoja(@PathVariable long cep) {
		List<Cep> result = cepRepository.findByFaixaCepBetween(cep);
		if (result.size() > 0) {
			String codigoLoja = result.get(0).getCodigoLoja();
			Loja loja = lojaRepository.findByCodigoLoja(codigoLoja);
			
			if (loja != null) {
				return new ResponseEntity<Object>(loja, HttpStatus.OK);				
			}
		}
		return new ResponseEntity<Object>("Não foi possível localizar este cep!", HttpStatus.NOT_FOUND); 
	}
	
}
