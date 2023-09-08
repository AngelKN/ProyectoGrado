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
import com.proyect.service.models.Ruta;
import com.proyect.service.service.RutaService;

@RestController
@RequestMapping("/user")
public class RutaController {
	
	@Autowired
	private RutaService serviceR;

	//NUEVO RUTA
	@PostMapping("/saveruta")
	public String saveRuta(@RequestBody Ruta pr) {
		String nuevo = serviceR.saveRuta(pr);
		return nuevo;
	}
	
	//ACTUALIZAR RUTA
	@PostMapping("/updateruta")
	public String updateRuta(@RequestBody Ruta pr) {
		String nuevo = serviceR.updateRuta(pr);
		return nuevo;
	}
	
	//LISTA DE RUTAS
	@GetMapping("/rutas")
	public List<Ruta> findAllRuta(){
		List<Ruta> ruta = serviceR.findAllRuta();
		return ruta;
	}
	
	//BUSCAR RUTA
	@GetMapping("/ruta/{id}")
	public Optional<Ruta> findRuta(@PathVariable("id") String id){
		Optional<Ruta> ruta = serviceR.findRuta(id);
		return ruta;
	}
	
	//ELIMINAR RUTA
	@DeleteMapping("/deleteruta/{id}")
	public String deleteRuta(@PathVariable("id") String id){
		String eliminar = serviceR.deleteRuta(id);
		return eliminar;
	}
	
	@GetMapping("/paradasruta/{id}")
	public List<Parada> paradasRuta(@PathVariable("id") String id) {
		return serviceR.pararuta(id);
	}
}
