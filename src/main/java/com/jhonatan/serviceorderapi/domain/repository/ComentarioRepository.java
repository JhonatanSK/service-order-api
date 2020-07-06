package com.jhonatan.serviceorderapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jhonatan.serviceorderapi.api.model.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Long>{

}
