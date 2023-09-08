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

import com.proyect.service.models.Cupo;

@FeignClient(name = "ServicioCupo", url = "http://localhost:9995")
@RequestMapping("/cupo")
public interface CupoFeignClient {

	//NUEVA CUPO
	@PostMapping("/save")
	public String save(@RequestBody Cupo cupo);
	
	//ACTUALIZAR CUPO
	@GetMapping("/update")
	public String update(@RequestBody Cupo cupo);
	
	//LISTA DE CUPOS
	@GetMapping("/all")
	public List<Cupo> findAll();
	
	//BUSCAR CUPO
	@GetMapping("/find/{id}")
	public Optional<Cupo> findParada(@PathVariable("id") String id);
	
	//ELIMINAR CUPO
	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable("id") String id);
	
	//CUPOS POR USUARIO
	@GetMapping("/id_user/{id}")
	public List<Cupo> cupoUser(@PathVariable("id") String id);
}
