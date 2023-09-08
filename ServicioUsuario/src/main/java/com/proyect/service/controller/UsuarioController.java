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

import com.proyect.service.entity.Usuario;
import com.proyect.service.models.Cupo;
import com.proyect.service.service.UsuarioService;

@RestController
@RequestMapping("/user")
public class UsuarioController {

	@Autowired
	private UsuarioService service;
	
	@GetMapping("/nia")
	public String saludar() {
		return "Miaow";
	}
	
	//LISTA USUARIOS
	@GetMapping("/all")
	public List<Usuario> findAll(){
		return service.getAll();
	}
	
	//BUSCAR USUARIO
	@GetMapping("/find/{id}")
	public Optional<Usuario> findById(@PathVariable("id") String id){
		Optional<Usuario> user = service.getUsuarioById(id);
		if(user != null) {
			return user; 
		}else {
			return Optional.empty();
		}
	}
	
	//BUSCAR USUARIO
	@GetMapping("/findcorreo/{correo}")
	public Optional<Usuario> findByCorreo(@PathVariable("correo") String correo){
		Optional<Usuario> user = service.getUsuarioByCorre(correo);
		if(user != null) {
			return user; 
		}else {
			return Optional.empty();
		}
	}
	
	//NUEVO USUARIO
	@PostMapping("/save")
	public String save(@RequestBody Usuario user) {
		if(service.save(user)) {
			return "guardado";
		}else {
			return "exciste";
		}
	}
	
	//ELIMINAR USUARIO
	@DeleteMapping("/delete/{nombre}")
	public String delete(@PathVariable("nombre") String nombre) {
		if(service.delete(nombre)) {
			return "eliminado";
		}else {
			return "no existe";
		}
	}
	
	//ACTUALIZAR USUARIO
	@PostMapping("/update")
	public String update(@RequestBody Usuario user) {
		
		if(service.update(user)) {
			return "actualizado";
		}else {
			return "no exciste";
		}
	}
	
	//LOGIN
	@PostMapping("/login")
	public Optional<Usuario> login(@RequestBody Usuario user) {
		Optional<Usuario> luser = service.login(user.getCorreo(), user.getContraseña());
		
		if(luser != null) {
			return luser;
		}else {
			return null;
		}
	}
	
	//CUPO POR USUARIO
	@GetMapping("/cupouser/{id}")
	public List<Cupo> cupoUser(@PathVariable("id") String id){
		return service.cupoUser(id);
	}
}
