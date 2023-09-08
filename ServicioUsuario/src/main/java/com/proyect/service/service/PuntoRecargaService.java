package com.proyect.service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyect.service.feignclient.PuntoRecargaFeignClient;
import com.proyect.service.models.PuntoRecarga;

@Service
public class PuntoRecargaService {

	@Autowired
	private PuntoRecargaFeignClient fei;

	//NUEVO PUNTO
	public String savePunto(PuntoRecarga pr) {
		String nuevo = fei.save(pr);
		return nuevo;
	}
	
	//ACTUALIZAR PUNTO
	public String updatePunto(PuntoRecarga pr) {
		String nuevo = fei.update(pr);
		return nuevo;
	}
	
	//LISTA PUNTOS DE RECARGA
	public List<PuntoRecarga> findAllPunto(){
		List<PuntoRecarga> pr = fei.findAll();
		return pr;
	}
	
	//BUSCAR PUNTO
	public Optional<PuntoRecarga> findPunto(String id){
		Optional<PuntoRecarga> punto = fei.findPunto(id);
		return punto;
	}
	
	//ELIMINAR PUNTO
	public String deletePunto(String id){
		String eliminar = fei.delete(id);
		return eliminar;
	}	
}
