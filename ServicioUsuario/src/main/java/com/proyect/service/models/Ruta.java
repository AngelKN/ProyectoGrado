package com.proyect.service.models;

import java.util.List;

public class Ruta {

	private String id;
	private String nombre;
	private String descripcion;
	private double precio;
	private String foto;
	private String tipo;
	private List<String> paradas;
	
	public Ruta() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}
	
	public List<String> getParadas() {
		return paradas;
	}

	public void setParadas(List<String> paradas) {
		this.paradas = paradas;
	}
}
