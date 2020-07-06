package com.jhonatan.serviceorderapi.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jhonatan.serviceorderapi.api.model.ServiceOrderInput;
import com.jhonatan.serviceorderapi.api.model.ServiceOrderModel;
import com.jhonatan.serviceorderapi.domain.model.ServiceOrder;
import com.jhonatan.serviceorderapi.domain.repository.ServiceOrderRepository;
import com.jhonatan.serviceorderapi.domain.service.ServiceOrderService;

@RestController
@RequestMapping("/ordens-servico")
public class ServiceOrderController {

	@Autowired
	private ServiceOrderService serviceOrderService;
	
	@Autowired
	private ServiceOrderRepository serviceOrderRepository;
	
	private ModelMapper modelMapper;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ServiceOrderModel create(@Valid @RequestBody ServiceOrderInput serviceOrderInput) {
		ServiceOrder serviceOrder = toEntity(serviceOrderInput);
		return toModel(serviceOrderService.create(serviceOrder));
	}
	
	@GetMapping
	public List<ServiceOrderModel> list(){
		return toCollectionModel(serviceOrderRepository.findAll());
	}
	
	@GetMapping("/{ordemServicoId}")
	public ResponseEntity<ServiceOrderModel> search(@PathVariable Long ordemServicoId){
		Optional<ServiceOrder> serviceOrder = serviceOrderRepository.findById(ordemServicoId);
		
		if(serviceOrder.isPresent()) {
			ServiceOrderModel model = toModel(serviceOrder.get());
			return ResponseEntity.ok(model);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}/finalizacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void finalize(@PathVariable Long id) {
		serviceOrderService.close(id);
	}
	
	
	private ServiceOrderModel toModel(ServiceOrder serviceOrder) {
		return modelMapper.map(serviceOrder, ServiceOrderModel.class);
	}
	
	private List<ServiceOrderModel> toCollectionModel(List<ServiceOrder> ordens){
		return ordens.stream().map(ordem -> toModel(ordem)).collect(Collectors.toList());
	}
	
	private ServiceOrder toEntity(ServiceOrderInput serviceOrderInput) {
		return modelMapper.map(serviceOrderInput, ServiceOrder.class);
	}
}




