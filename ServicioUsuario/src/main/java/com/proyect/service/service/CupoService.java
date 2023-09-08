package com.proyect.service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyect.service.feignclient.CupoFeignClient;
import com.proyect.service.models.Cupo;

@Service
public class CupoService {
	
	@Autowired
	private CupoFeignClient fei;
	
	//NUEVA CUPO
	public String saveCupo(Cupo cupo) {
		String nuevo = fei.save(cupo);
		return nuevo;
	}
	
	//ACTUALIZAR CUPO
	public String updateCupo(Cupo cupo) {
		String nuevo = fei.update(cupo);
		return nuevo;
	}
	
	//LISTA DE CUPOS
	public List<Cupo> findAllCupo(){
		List<Cupo> cupo = fei.findAll();
		return cupo;
	}
	
	//BUSCAR CUPO
	public Optional<Cupo> findCupo(String id){
		Optional<Cupo> cupo = fei.findParada(id);
		return cupo;
	}
	
	//ELIMINAR CUPO
	public String deleteCupo(String nombre){
		String eliminar = fei.delete(nombre);
		return eliminar;
	}
}
