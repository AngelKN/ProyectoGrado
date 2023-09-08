package com.proyect.service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyect.service.entity.PuntoRecarga;
import com.proyect.service.repository.PuntoRecargarRepository;

@Service
public class PuntoRecargarService {

	@Autowired
	private PuntoRecargarRepository repo;
	
	//PUNTOS DE RECARGA
	public List<PuntoRecarga> getAll(){
		return repo.findAll();
	}
	
	//BUSACAR PUNTO DE RECARGAR
	public Optional<PuntoRecarga> getUsuarioByNombre(String nombre){
		Optional<PuntoRecarga> pr = java.util.Optional.empty();
		
		for(PuntoRecarga item :repo.findAll()) {
			if(item.getNombre().equals(nombre)) {
				pr = repo.findById(item.getId());
			}
		}
		
		return pr;	
	}

	//BUSACAR PUNTO DE RECARGAR POR ID
	public Optional<PuntoRecarga> getUsuarioById(String id){
		Optional<PuntoRecarga> punto = repo.findById(id);
				
		return punto;	
	}
	
	//AGREGAR PUNTO DE RECARGA
	public boolean save(PuntoRecarga pr) {
		Optional<PuntoRecarga> vpr = getUsuarioByNombre(pr.getNombre());
		
		if(vpr.equals(Optional.empty())){
			repo.save(pr);
			return true;  
		}else {
			return false;
		}
	}
	
	//ELIMINAR PUNTO DE RECARGA
	public boolean delete(String nombre) {
		
		Optional<PuntoRecarga> pr = getUsuarioById(nombre);
		
		if(!pr.equals(Optional.empty())){
			repo.deleteById(pr.get().getId());
			return true;
		}else {
			return false;
		}
	}
	
	//ACTUALIZAR PUNTO DE RECARGA
	public boolean update(PuntoRecarga pr) {

		Optional<PuntoRecarga> vpr = getUsuarioById(pr.getId());
		
		if(!vpr.equals(Optional.empty())){
			repo.save(pr);
			return true;
		}else {
			return false;
		}
	}
}
