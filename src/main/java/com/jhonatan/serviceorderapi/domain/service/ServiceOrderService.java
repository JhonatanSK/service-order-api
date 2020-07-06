package com.jhonatan.serviceorderapi.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jhonatan.serviceorderapi.api.model.Comentario;
import com.jhonatan.serviceorderapi.domain.exception.EntityNotFoundException;
import com.jhonatan.serviceorderapi.domain.exception.Exceptions;
import com.jhonatan.serviceorderapi.domain.model.Cliente;
import com.jhonatan.serviceorderapi.domain.model.ServiceOrder;
import com.jhonatan.serviceorderapi.domain.model.StatusServiceOrder;
import com.jhonatan.serviceorderapi.domain.repository.ClienteRepository;
import com.jhonatan.serviceorderapi.domain.repository.ComentarioRepository;
import com.jhonatan.serviceorderapi.domain.repository.ServiceOrderRepository;

@Service
public class ServiceOrderService {

	@Autowired
	private ServiceOrderRepository serviceOrderRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	public ServiceOrder create(ServiceOrder serviceOrder) {	
		Cliente cliente = clienteRepository.findById(serviceOrder.getCliente().getId()).orElseThrow(() -> new Exceptions("Cliente Não Encontrado"));
		
		serviceOrder.setCliente(cliente);
		serviceOrder.setStatus(StatusServiceOrder.OPEN);
		serviceOrder.setDataAbertura(OffsetDateTime.now());
		
		return serviceOrderRepository.save(serviceOrder);
	}
	
	public Comentario addComment(Long ServiceOrderId, String description) {
		ServiceOrder serviceOrder = search(ServiceOrderId);
		Comentario comentario = new Comentario();
		comentario.setDataEnvio(OffsetDateTime.now());
		comentario.setDescricao(description);
		comentario.setServiceOrder(serviceOrder);
		
		return comentarioRepository.save(comentario);
	}
	
	public void close(Long id) {
		ServiceOrder serviceOrder = search(id);
		serviceOrder.finalizar();
		
		serviceOrderRepository.save(serviceOrder);
	}
	

	private ServiceOrder search(Long id) {
		return serviceOrderRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Ordem de serviço não encontrado"));
	}
}
