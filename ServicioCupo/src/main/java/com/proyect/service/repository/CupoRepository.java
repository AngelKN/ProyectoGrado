package com.proyect.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.proyect.service.entity.Cupo;

public interface CupoRepository extends MongoRepository<Cupo, String>{

}
