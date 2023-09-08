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

import com.proyect.service.models.Parada;
import com.proyect.service.service.ParadaService;

@RestController
@RequestMapping("/user")
public class ParadaController {

	@Autowired
	private ParadaService serviceP;
	
	//NUEVA PARADA
	@PostMapping("/saveparada")
	public String saveParada(@RequestBody Parada parada) {
		String nuevo = serviceP.saveParada(parada);
		return nuevo;
	}
	
	//ACTUALIZAR PARADA
	@PostMapping("/updateparada")
	public String updateParada(@RequestBody Parada parada) {
		String nuevo = serviceP.updateParada(parada);
		return nuevo;
	}
	
	//LISTA DE PARADAS
	@GetMapping("/paradas")
	public List<Parada> findAllParadas(){
		List<Parada> paradas = serviceP.findAllParada();
		return paradas;
	}
	
	//BUSCAR PARADA
	@GetMapping("/parada/{id}")
	public Optional<Parada> findParada(@PathVariable("id") String id){
		Optional<Parada> parada = serviceP.findParada(id);
		return parada;
	}
	
	//ELIMINAR PARADA
	@DeleteMapping("/deleteparada/{id}")
	public String deleteParada(@PathVariable("id") String id){
		String eliminar = serviceP.deleteParada(id);
		return eliminar;
	}
}
