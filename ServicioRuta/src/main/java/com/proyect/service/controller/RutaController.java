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

import com.proyect.service.entity.Ruta;
import com.proyect.service.service.RutaService;

@RestController
@RequestMapping("/ruta")
public class RutaController {

	@Autowired
	private RutaService service;
	
	@GetMapping("/")
	public String saludar() {
		return "Miaow";
	}
	
	@GetMapping("/all")
	public List<Ruta> findAll(){
		return service.getAll();
	}
	
	@GetMapping("/find/{id}")
	public Optional<Ruta> findById(@PathVariable("id") String id){
		Optional<Ruta> ruta = service.getUsuarioById(id);
		if(ruta != null) {
			return ruta; 
		}else {
			return Optional.empty();
		}
	}
	
	@PostMapping("/save")
	public String save(@RequestBody Ruta ruta) {
		if(service.save(ruta)) {
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
	public String update(@RequestBody Ruta ruta) {
		
		if(service.update(ruta)) {
			return "actualizado";
		}else {
			return "no exciste";
		}
	}
}
