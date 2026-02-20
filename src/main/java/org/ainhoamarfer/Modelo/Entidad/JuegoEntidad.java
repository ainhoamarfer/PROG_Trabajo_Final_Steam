package org.ainhoamarfer.Modelo.Entidad;

import org.ainhoamarfer.Modelo.Enums.JuegoClasificacionEdad;
import org.ainhoamarfer.Modelo.Enums.JuegoEstado;

import java.time.LocalDate;

public class JuegoEntidad {

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
    private JuegoEstado estado;

    public JuegoEntidad(long id, String titulo, String descripcion, String desarrollador, LocalDate fechaLanzamiento, double precioBase, double descuentoActual, String categoria, String idiomas, JuegoClasificacionEdad clasificacionEdad) {
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
        this.estado = JuegoEstado.DISPONIBLE;
    }

    public long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getDesarrollador() {
        return desarrollador;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public double getDescuentoActual() {
        return descuentoActual;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public JuegoClasificacionEdad getClasificacionEdad() {
        return clasificacionEdad;
    }

    public JuegoEstado getEstado() {
        return estado;
    }
}
