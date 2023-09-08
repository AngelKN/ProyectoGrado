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

import com.proyect.service.models.PuntoRecarga;

@FeignClient(name = "ServicioPuntoRecarga", url = "http://localhost:9993")
@RequestMapping("/punto")
public interface PuntoRecargaFeignClient {

	//NUEVA PARADA
	@PostMapping("/save")
	public String save(@RequestBody PuntoRecarga pr);
	
	//ACTUALIZAR PARADA
	@GetMapping("/update")
	public String update(@RequestBody PuntoRecarga pr);
	
	//LISTA DE PARADAS
	@GetMapping("/all")
	public List<PuntoRecarga> findAll();
	
	//BUSCAR PARADA
	@GetMapping("/find/{id}")
	public Optional<PuntoRecarga> findPunto(@PathVariable("id") String id);
	
	//ELIMINAR PARADA
	@DeleteMapping("/delete/{id}")
	public String delete(@PathVariable("id") String id);
}
