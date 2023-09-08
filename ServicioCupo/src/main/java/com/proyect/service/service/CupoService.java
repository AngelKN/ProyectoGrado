package com.proyect.service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyect.service.entity.Cupo;
import com.proyect.service.repository.CupoRepository;

@Service
public class CupoService {

	@Autowired
	private CupoRepository repo;
	
	public List<Cupo> getAll(){
		return repo.findAll();
	}
	
	public Optional<Cupo> getCupoByUbicacion(double precio){
		Optional<Cupo> cupo = java.util.Optional.empty();
		
		for(Cupo item :repo.findAll()) {
			if(item.getPrecio() == precio) {
				cupo = repo.findById(item.getId());
			}
		}
		
		return cupo;	
	}
	
	public Optional<Cupo> getCupoById(String id){
		Optional<Cupo> cupo = repo.findById(id);
				
		return cupo;	
	}
	
	public boolean save(Cupo parada) {
		
			repo.save(parada);
			return true; 
	}
	
	public boolean delete(String id) {
		
		Optional<Cupo> cupo = getCupoById(id);
		
		if(!cupo.equals(Optional.empty())){
			repo.deleteById(cupo.get().getId());
			return true;
		}else {
			return false;
		}
	}
	
	public boolean update(Cupo cupo) {
			repo.save(cupo);
			return true;
	}
	
	//CUPOS POR ID_USER
	public List<Cupo> cupoUser(String id_user){
		List<Cupo> cupo = new ArrayList<Cupo>();
		Cupo par = new Cupo();
		
		for(Cupo item :repo.findAll()) {
			if(item.getId_user().equalsIgnoreCase(id_user)) {
				par.setId(item.getId());
				par.setPrecio(item.getPrecio());
				par.setHora_llegada(item.getHora_llegada());
				par.setHora_salida(item.getHora_salida());
				par.setDescripcion(item.getDescripcion());
				par.setId_user(item.getId_user());
				cupo.add(par);
			}
		}
		
		return cupo;
	}
}
