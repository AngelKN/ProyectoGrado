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

import com.proyect.service.models.PuntoRecarga;
import com.proyect.service.service.PuntoRecargaService;

@RestController
@RequestMapping("/user")
public class PuntoRecargaController {

	@Autowired
	private PuntoRecargaService servicePR;

	//NUEVO PUNTO
	@PostMapping("/savepunto")
	public String savePunto(@RequestBody PuntoRecarga pr) {
		String nuevo = servicePR.savePunto(pr);
		return nuevo;
	}
	
	//ACTUALIZAR PUNTO
	@PostMapping("/updatepunto")
	public String updatePunto(@RequestBody PuntoRecarga pr) {
		String nuevo = servicePR.updatePunto(pr);
		return nuevo;
	}
	
	//LISTA DE PUNTO
	@GetMapping("/puntos")
	public List<PuntoRecarga> findAllPunto(){
		List<PuntoRecarga> pr = servicePR.findAllPunto();
		return pr;
	}
	
	//BUSCAR PUNTO
	@GetMapping("/punto/{id}")
	public Optional<PuntoRecarga> findPunto(@PathVariable("id") String id){
		Optional<PuntoRecarga> punto = servicePR.findPunto(id);
		return punto;
	}
	
	//ELIMINAR PUNTO
	@DeleteMapping("/deletepunto/{id}")
	public String deletePunto(@PathVariable("id") String id){
		String eliminar = servicePR.deletePunto(id);
		return eliminar;
	}
}
