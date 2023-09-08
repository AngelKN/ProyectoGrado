package com.proyect.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.proyect.service.entity.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, String>{

}
