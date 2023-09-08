package com.proyect.service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyect.service.feignclient.ParadaFeignClient;
import com.proyect.service.feignclient.RutaFeignClient;
import com.proyect.service.models.Parada;
import com.proyect.service.models.Ruta;

@Service
public class ParadaService {
	
	@Autowired
	private ParadaFeignClient fei;
	
	@Autowired
	private RutaFeignClient feiR;
	
	//NUEVA PARADA
	public String saveParada(Parada parada) {
		String nuevo = fei.save(parada);
		return nuevo;
	}
	
	//ACTUALIZAR PARADA
	public String updateParada(Parada parada) {
		String nuevo = fei.update(parada);
		return nuevo;
	}
	
	//LISTA DE PARADAS
	public List<Parada> findAllParada(){
		List<Parada> paradas = fei.findAll();
		return paradas;
	}
	
	//BUSCAR PARADA
	public Optional<Parada> findParada(String id){
		Optional<Parada> parada = fei.findParada(id);
		return parada;
	}
	
	//ELIMINAR PARADA
	//AL ELIMINAR UNA PARADA, AUTOMATICAMENTE BUSCARA LAS RUTAS QUE LA CONTENGAN Y LAS ELIMINARA DE LA LISTA
	public String deleteParada(String id){
		String eliminar = fei.delete(id);
		
		String updataRuta="";
		List<String> id_parada = new ArrayList<String>();
		List<String> id_parada1 = new ArrayList<String>();
		Ruta ruta = new Ruta();
		
		for(Ruta item : feiR.findAll()) {
			ruta = item;
			id_parada = ruta.getParadas();
			for(String ids : item.getParadas()) {
				if(item.getParadas().size() > 1) {
					if(ids.equals(id)) {
						id_parada.remove(id);
						ruta.setParadas(id_parada);
					}
				}else {
					ruta.setParadas(id_parada1);
				}
			}
			
			updataRuta = feiR.update(ruta);
		}
		
		return eliminar;
	}

}
