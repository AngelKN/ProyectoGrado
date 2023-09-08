package com.proyect.service.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proyect.service.entity.Cupo;
import com.proyect.service.service.CupoService;

@RestController
@RequestMapping("/cupo")
public class CupoController {

	@Autowired
	private CupoService service;
	
	@GetMapping("/test")
	public String saludar() {
		return "asdadad";
	}
	
	@GetMapping("/all")
	public List<Cupo> findAll(){
		return service.getAll();
	}
	
	@GetMapping("/find/{id}")
	public Optional<Cupo> findById(@PathVariable("id") String id){
		Optional<Cupo> parada = service.getCupoById(id);
		if(parada != null) {
			return parada; 
		}else {
			return Optional.empty();
		}
	}
	
	@PostMapping("/save")
	public String save(@RequestBody Cupo cupo) {
		if(service.save(cupo)) {
			return "guardado";
		}else {
			return "exciste";
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable("id") String id) {
		if(service.delete(id)) {
			return "eliminado";
		}else {
			return "no existe";
		}
	}
	
	@PostMapping("/update")
	public String update(@RequestBody Cupo cupo) {
		
		if(service.update(cupo)) {
			return "actualizado";
		}else {
			return "no exciste";
		}
	}
	
	@GetMapping("/id_user/{id}")
	public List<Cupo> cupoUser(@PathVariable("id") String id_user){
		return service.cupoUser(id_user);
	}
}
