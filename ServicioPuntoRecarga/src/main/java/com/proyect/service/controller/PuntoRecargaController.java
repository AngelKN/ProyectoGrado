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

import com.proyect.service.entity.PuntoRecarga;
import com.proyect.service.service.PuntoRecargarService;


@RestController
@RequestMapping("/punto")
public class PuntoRecargaController {

	@Autowired
	private PuntoRecargarService service;
	
	@GetMapping("/")
	public String saludar() {
		return "Miaow";
	}
	
	//PUNTOS DE RECARGA
	@GetMapping("/all")
	public List<PuntoRecarga> findAll(){
		return service.getAll();
	}
	
	//BUSCAR PUNTOS DE RECARGAR POR ID
	@GetMapping("/find/{id}")
	public Optional<PuntoRecarga> findById(@PathVariable("id") String id){
		Optional<PuntoRecarga> pr = service.getUsuarioById(id);
		if(pr != null) {
			return pr; 
		}else {
			return Optional.empty();
		}
	}
	
	//AGREGAR PUNTO DE RECARGA
	@PostMapping("/save")
	public String save(@RequestBody PuntoRecarga pr) {
		if(service.save(pr)) {
			return "guardado";
		}else {
			return "exciste";
		}
	}
	
	//ELIMINAR PUNTO DE RECARGA
	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable("id") String id) {
		if(service.delete(id)) {
			return "eliminado";
		}else {
			return "no existe";
		}
	}
	
	//ACTUALIZAR PUNTO DE RECARGA
	@PostMapping("/update")
	public String update(@RequestBody PuntoRecarga pr) {
		
		if(service.update(pr)) {
			return "actualizado";
		}else {
			return "no exciste";
		}
	}
}
