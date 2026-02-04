package Modelo.DTOs;

import Modelo.Enums.JuegoClasificacionEdad;
import Modelo.Enums.JuegoEstado;

import java.time.LocalDate;

public class JuegoDTO {

    private long id;
    private String titulo;
    private String descripcion;
    private String desarrollador;
    private LocalDate fechaLanzamiento;
    private double precioBase;
    private double descuentoActual;
    private String categoria;
    private String idiomas;
    private JuegoClasificacionEdad clasificacionEdad;
    private JuegoEstado estadoActual;

    public JuegoDTO(long id, String titulo, String descripcion, String desarrollador, LocalDate fechaLanzamiento, double precioBase, double descuentoActual, String categoria, String idiomas, JuegoClasificacionEdad clasificacionEdad, JuegoEstado estadoActual) {

        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.desarrollador = desarrollador;
        this.fechaLanzamiento = fechaLanzamiento;
        this.precioBase = precioBase;
        this.descuentoActual = descuentoActual;
        this.categoria = categoria;
        this.idiomas = idiomas;
        this.clasificacionEdad = clasificacionEdad;
        this.estadoActual = estadoActual;
    }

    public JuegoClasificacionEdad getClasificacionEdad() {
        return clasificacionEdad;
    }

    public void setClasificacionEdad(JuegoClasificacionEdad clasificacionEdad) {
        this.clasificacionEdad = clasificacionEdad;
    }

    public JuegoEstado getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(JuegoEstado estadoActual) {
        this.estadoActual = estadoActual;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDesarrollador() {
        return desarrollador;
    }

    public void setDesarrollador(String desarrollador) {
        this.desarrollador = desarrollador;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(LocalDate fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public double getDescuentoActual() {
        return descuentoActual;
    }

    public void setDescuentoActual(double descuentoActual) {
        this.descuentoActual = descuentoActual;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }
}
