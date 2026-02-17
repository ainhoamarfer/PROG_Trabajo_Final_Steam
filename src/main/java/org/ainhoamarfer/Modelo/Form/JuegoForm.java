package org.ainhoamarfer.Modelo.Form;

import org.ainhoamarfer.Modelo.DTOs.ErrorDTO;
import org.ainhoamarfer.Modelo.Enums.ErrorType;
import org.ainhoamarfer.Modelo.Enums.JuegoClasificacionEdad;
import org.ainhoamarfer.Modelo.Enums.JuegoEstado;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JuegoForm {

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

    public JuegoForm(String titulo, String descripcion, String desarrollador, LocalDate fechaLanzamiento, double precioBase, double descuentoActual, String categoria, String idiomas, JuegoClasificacionEdad clasificacionEdad, JuegoEstado estado) {

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

    public JuegoEstado getEstado() {
        return estado;
    }

    public JuegoClasificacionEdad getClasificacionEdad() {
        return clasificacionEdad;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public String getCategoria() {
        return categoria;
    }

    public double getDescuentoActual() {
        return descuentoActual;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public String getDesarrollador() {
        return desarrollador;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public List<ErrorDTO> validar (JuegoForm form){
        List<ErrorDTO> errores = new ArrayList<>();

        // Título
        if (titulo == null || titulo.isBlank()) {
            errores.add(new ErrorDTO("titulo", ErrorType.REQUERIDO));
        }
        // Desarrollador
        if (desarrollador == null || desarrollador.isBlank()) {
            errores.add(new ErrorDTO("desarrollador", ErrorType.REQUERIDO));
        }
        // Fecha de lanzamiento
        if (fechaLanzamiento == null) {
            errores.add(new ErrorDTO("fechaLanzamiento", ErrorType.REQUERIDO));
        }
        // Precio base
        if (precioBase < 0) {
            errores.add(new ErrorDTO("precioBase", ErrorType.REQUERIDO));
        }
        // Clasificación por edad
        if (clasificacionEdad == null) {
            errores.add(new ErrorDTO("clasificacionEdad", ErrorType.REQUERIDO));
        }

        return errores;
    }
}
