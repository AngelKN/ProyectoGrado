package com.proyect.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.proyect.service.entity.Parada;

public interface ParadaRepository extends MongoRepository<Parada, String>{

}
