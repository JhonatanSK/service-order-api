package com.jhonatan.serviceorderapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhonatan.serviceorderapi.domain.exception.Exceptions;
import com.jhonatan.serviceorderapi.domain.model.Cliente;
import com.jhonatan.serviceorderapi.domain.repository.ClienteRepository;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente add(Cliente cliente) {
		Cliente clientExists = clienteRepository.findByEmail(cliente.getEmail());
		
		if(clientExists != null && !clientExists.equals(cliente)) 
			throw new Exceptions("Já existe um email cadastrado com este dominio");
				
		return clienteRepository.save(cliente);
	}
	
	public void remove(Long clienteId) {
		clienteRepository.deleteById(clienteId);
	}
}
