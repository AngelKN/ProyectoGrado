package com.proyect.service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyect.service.entity.Ruta;
import com.proyect.service.repository.RutaRepository;

@Service
public class RutaService {

	@Autowired
	private RutaRepository repo;
	
	public List<Ruta> getAll(){
		return repo.findAll();
	}
	
	public Optional<Ruta> getUsuarioById(String id){
		Optional<Ruta> ruta = repo.findById(id);
		
		return ruta;	
	}
	
	public Optional<Ruta> getUsuarioByNombre(String nombre){
		Optional<Ruta> ruta = java.util.Optional.empty();
		
		for(Ruta item :repo.findAll()) {
			if(item.getNombre().equals(nombre)) {
				ruta = repo.findById(item.getId());
			}
		}
		
		return ruta;	
	}
	
	public boolean save(Ruta ruta) {
		Optional<Ruta> vuser = getUsuarioByNombre(ruta.getNombre());
		
		if(vuser.equals(Optional.empty())){
			repo.save(ruta);
			return true;  
		}else {
			return false;
		}
	}
	
	public boolean delete(String id) {
		
		Optional<Ruta> ruta = getUsuarioById(id);
		
		if(!ruta.equals(Optional.empty())){
			repo.deleteById(ruta.get().getId());
			return true;
		}else {
			return false;
		}
	}
	
	public boolean update(Ruta ruta) {

		Optional<Ruta> vruta = getUsuarioById(ruta.getId());
		
		if(!vruta.equals(Optional.empty())){
			repo.save(ruta);
			return true;
		}else {
			return false;
		}
	}
 
}
