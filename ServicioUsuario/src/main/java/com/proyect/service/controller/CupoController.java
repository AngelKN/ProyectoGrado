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

import com.proyect.service.models.Cupo;
import com.proyect.service.service.CupoService;

@RestController
@RequestMapping("/user")
public class CupoController {

	@Autowired
	private CupoService serviceP;
	
	//NUEVA PARADA
	@PostMapping("/savecupo")
	public String saveCupo(@RequestBody Cupo cupo) {
		String nuevo = serviceP.saveCupo(cupo);
		return nuevo;
	}
	
	//ACTUALIZAR PARADA
	@PostMapping("/updatecupo")
	public String updateCupo(@RequestBody Cupo cupo) {
		String nuevo = serviceP.updateCupo(cupo);
		return nuevo;
	}
	
	//LISTA DE PARADAS
	@GetMapping("/cupos")
	public List<Cupo> findAllCupos(){
		List<Cupo> cupos = serviceP.findAllCupo();
		return cupos;
	}
	
	//BUSCAR PARADA
	@GetMapping("/cupo/{id}")
	public Optional<Cupo> findCupo(@PathVariable("id") String id){
		Optional<Cupo> cupo = serviceP.findCupo(id);
		return cupo;
	}
	
	//ELIMINAR PARADA
	@DeleteMapping("/deletecupo/{id}")
	public String deleteCupo(@PathVariable("id") String id){
		String eliminar = serviceP.deleteCupo(id);
		return eliminar;
	}
}
