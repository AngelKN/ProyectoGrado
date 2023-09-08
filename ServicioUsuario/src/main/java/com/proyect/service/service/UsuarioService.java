package com.proyect.service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyect.service.entity.Usuario;
import com.proyect.service.feignclient.CupoFeignClient;
import com.proyect.service.models.Cupo;
import com.proyect.service.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repo;
	
	@Autowired
	private CupoFeignClient feic;
	
	//LISTA USUARIOS
	public List<Usuario> getAll(){
		return repo.findAll();
	}
	
	//BUSCAR USUARIO
	public Optional<Usuario> getUsuarioByCorre(String correo){
		Optional<Usuario> user = java.util.Optional.empty();
		
		for(Usuario item :repo.findAll()) {
			if(item.getCorreo().equals(correo)) {
				user = repo.findById(item.getId());
			}
		}
		
		return user;	
	}
	
	public Optional<Usuario> getUsuarioById(String id){
		Optional<Usuario> ruta = repo.findById(id);
		
		return ruta;	
	}
	
	//NUEVO USUARIO
	public boolean save(Usuario user) {
		Optional<Usuario> vuser = getUsuarioByCorre(user.getCorreo());
		
		if(vuser.equals(Optional.empty())){
			repo.save(user);
			return true;  
		}else {
			return false;
		}
	}
	
	//ELIMINAR USUARIO
	//TAMBIEN SE ELIMINAN TODAS LOS CUPOS(PUBLICACIONES)CREADAS POR EL USUARIO
	public boolean delete(String id) {
		
		Optional<Usuario> user = getUsuarioById(id);
		
		String deleteUser;
		List<String> id_parada1 = new ArrayList<String>();
		
		if(!user.equals(Optional.empty())){
			repo.deleteById(user.get().getId());
			for(Cupo item : feic.findAll()) {
				if(item.getId_user().equals(id)) {
					deleteUser = feic.delete(item.getId());
				}
			}
			return true;
		}else {
			return false;
		}
	}
	
	//ACTUALIZAR USUARIO
	public boolean update(Usuario user) {

		Optional<Usuario> vuser = getUsuarioById(user.getId());
		
		if(!vuser.equals(Optional.empty())){
			repo.save(user);
			return true;
		}else {
			return false;
		}
	}
	
	//LOGIN
	public Optional<Usuario> login(String correo, String contraseña) {
		Optional<Usuario> user = java.util.Optional.empty();
		
		for(Usuario item :repo.findAll()) {
			if(item.getCorreo().equals(correo) && item.getContraseña().equals(contraseña)) {
				user = repo.findById(item.getId());
			}
		}
		
		return user;
	}
	
	//CUPOS POR USUARIO
	public List<Cupo> cupoUser(String id){
		List<Cupo> cupo = feic.cupoUser(id);
		return cupo;
	}
	
}
