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

import com.proyect.service.models.Parada;

@FeignClient(name = "ServicioParada", url = "http://localhost:9994")
@RequestMapping("/parada")
public interface ParadaFeignClient {

	//NUEVA PARADA
	@PostMapping("/save")
	public String save(@RequestBody Parada parada);
	
	//ACTUALIZAR PARADA
	@GetMapping("/update")
	public String update(@RequestBody Parada parada);
	
	//LISTA DE PARADAS
	@GetMapping("/all")
	public List<Parada> findAll();
	
	//BUSCAR PARADA
	@GetMapping("/find/{id}")
	public Optional<Parada> findParada(@PathVariable("id") String id);
	
	//ELIMINAR PARADA
	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable("id") String id);
}
