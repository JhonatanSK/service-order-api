package com.jhonatan.serviceorderapi.api.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jhonatan.serviceorderapi.domain.model.Cliente;

@RestController
public class ClienteController {

	@GetMapping("/clientes")
	public List<Cliente> listar() {
		Cliente cliente1 = new Cliente();
		cliente1.setId(1L);
		cliente1.setNome("Jhon");
		cliente1.setEmail("jhon@gmail.com");
		cliente1.setTelefone("11-99711-1581");
		Cliente cliente2 = new Cliente();
		cliente2.setId(2L);
		cliente2.setNome("Jhonatan da costa");
		cliente2.setEmail("jhonatan@gmail.com");
		cliente2.setTelefone("11-99711-1581");
		return Arrays.asList(cliente1, cliente2);
	}
}
