package com.proyect.service.feignclient;

import java.util.List;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.proyect.service.models.Ruta;

@FeignClient(name = "ServicioRuta", url = "http://localhost:9992")
@RequestMapping("/ruta")
public interface RutaFeignClient {
	
	//NUEVA RUTA
	@PostMapping("/save")
	public String save(@RequestBody Ruta ruta);
	
	//ACTUALIZAR RUTA
	@GetMapping("/update")
	public String update(@RequestBody Ruta ruta);
	
	//LISTA DE RUTAS
	@GetMapping("/all")
	public List<Ruta> findAll();
	
	//BUSCAR RUTA
	@GetMapping("/find/{id}")
	public Optional<Ruta> findRuta(@PathVariable("id") String id);
	
	//ELIMINAR RUTA
	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable("id") String id);
}
