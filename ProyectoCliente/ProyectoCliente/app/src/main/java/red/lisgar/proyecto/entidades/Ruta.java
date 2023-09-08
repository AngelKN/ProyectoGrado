package red.lisgar.proyecto.entidades;

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

    public Ruta(String id, String nombre, String descripcion, String foto, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.foto = foto;
        this.tipo = tipo;
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

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    @Override
    public String toString() {
        return nombre;
    }
}
