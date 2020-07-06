package com.jhonatan.serviceorderapi.api.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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

import com.jhonatan.serviceorderapi.domain.model.Cliente;
import com.jhonatan.serviceorderapi.domain.repository.ClienteRepository;
import com.jhonatan.serviceorderapi.domain.service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ClienteService clienteService;
	
	@GetMapping
	public List<Cliente> list() {
		return clienteRepository.findAll();
	}
	
	@GetMapping("/{clienteId}")
	public ResponseEntity<Cliente> search(@PathVariable Long clienteId) {
		Optional<Cliente> cliente = clienteRepository.findById(clienteId);
		
		if(cliente.isPresent()) return ResponseEntity.ok(cliente.get());	
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente add(@Valid @RequestBody Cliente cliente) {
		return clienteService.add(cliente);
	}
	
	@PutMapping("/{clienteId}")
	public ResponseEntity<Cliente> update(@Valid @PathVariable Long clienteId, @RequestBody Cliente cliente){
		if(clienteRepository.existsById(clienteId)) return ResponseEntity.notFound().build();
		
		cliente.setId(clienteId);
		cliente = clienteService.add(cliente);
		
		return ResponseEntity.ok(cliente);
	}
	
	@DeleteMapping("/{clienteId}")
	public ResponseEntity<Void> remove(@PathVariable Long clienteId){
		if(clienteRepository.existsById(clienteId)) return ResponseEntity.notFound().build();
		
		clienteService.remove(clienteId);
		return ResponseEntity.noContent().build();
	}
	
}


