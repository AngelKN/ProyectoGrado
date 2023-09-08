package com.proyect.service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyect.service.entity.Parada;
import com.proyect.service.repository.ParadaRepository;

@Service
public class ParadaService {

	@Autowired
	private ParadaRepository repo;
	
	//PARADAS
	public List<Parada> getAll(){
		return repo.findAll();
	}
	
	//BUSCAR PARADA
	public Optional<Parada> getUsuarioByUbicacion(String ubicacion){
		Optional<Parada> parada = java.util.Optional.empty();
		
		for(Parada item :repo.findAll()) {
			if(item.getUbicacion().equals(ubicacion)) {
				parada = repo.findById(item.getId());
			}
		}
		
		return parada;	
	}
	
	//BUSCAR PARADA POR ID
	public Optional<Parada> getUsuarioById(String id){
		Optional<Parada> parada = repo.findById(id);
		
		return parada;	
	}
	
	//AGREGAR PARADA
	public boolean save(Parada parada) {
		Optional<Parada> vparada = getUsuarioByUbicacion(parada.getUbicacion());
		
		if(vparada.equals(Optional.empty())){
			repo.save(parada);
			return true;  
		}else {
			return false;
		}
	}
	
	//ELIMINAR PARADA
	public boolean delete(String id) {
		
		Optional<Parada> parada = getUsuarioById(id);
		
		if(!parada.equals(Optional.empty())){
			repo.deleteById(parada.get().getId());
			return true;
		}else {
			return false;
		}
	}
	
	//ACTUALIZAR PARADA
	public boolean update(Parada parada) {

		Optional<Parada> vparada = getUsuarioById(parada.getId());
		
		if(!vparada.equals(Optional.empty())){
			repo.save(parada);
			return true;
		}else {
			return false;
		}
	}
}
