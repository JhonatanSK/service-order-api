package com.jhonatan.serviceorderapi.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jhonatan.serviceorderapi.api.model.Comentario;
import com.jhonatan.serviceorderapi.api.model.ComentarioInput;
import com.jhonatan.serviceorderapi.api.model.ComentarioModel;
import com.jhonatan.serviceorderapi.domain.model.ServiceOrder;
import com.jhonatan.serviceorderapi.domain.repository.ServiceOrderRepository;
import com.jhonatan.serviceorderapi.domain.service.ServiceOrderService;

@RestController
@RequestMapping("/ordens-servico/{ordemServicoId}/comentarios")
public class ComentarioController {

	@Autowired
	private ServiceOrderService serviceOrderService;
	
	@Autowired
	private ServiceOrderRepository serviceOrderRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping
	public List<ComentarioModel> list(@PathVariable Long id){
		ServiceOrder serviceOrder = serviceOrderRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Ordem de Serviço não encontrada"));
		
		return toCollectionModel(serviceOrder.getComentarios());
	}
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ComentarioModel add(@PathVariable Long ordemServicoId, @RequestBody @Valid ComentarioInput comentarioInput) {
		Comentario comentario = serviceOrderService.addComment(ordemServicoId, comentarioInput.getDescricao());
		
		return toModel(comentario);
	}
	
	private ComentarioModel toModel(Comentario comentario) {
		return modelMapper.map(comentario, ComentarioModel.class);
	}
	
	private List<ComentarioModel> toCollectionModel(List<Comentario> comentarios){
		return comentarios.stream().map(comentario -> toModel(comentario)).collect(Collectors.toList());
	}
}
