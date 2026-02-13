package org.ainhoamarfer.Modelo.Form;

import java.time.LocalDate;

public class JuegoForm {

    private String titulo;
    private String descripcion;
    private String desarrollador;
    private LocalDate fechaLanzamiento;
    private double precioBase;
    private double descuentoActual;
    private String categoria;
    private String idiomas;

    public JuegoForm(String titulo, String descripcion, String desarrollador, LocalDate fechaLanzamiento, double precioBase, double descuentoActual, String categoria, String idiomas) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.desarrollador = desarrollador;
        this.fechaLanzamiento = fechaLanzamiento;
        this.precioBase = precioBase;
        this.descuentoActual = descuentoActual;
        this.categoria = categoria;
        this.idiomas = idiomas;
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
}
