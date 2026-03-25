package org.ainhoamarfer.modelo.dtos;

import org.ainhoamarfer.modelo.entidad.ResenaEntidad;
import org.ainhoamarfer.modelo.enums.ResenaEstado;

import java.time.LocalDate;

public class ResenaDTO {

    private long id;
    private long idUsuario;
    private long idJuego;
    private boolean recomendado;
    private String texto;
    private double horasJugadas;
    private LocalDate fechaPublicacion;
    private LocalDate fechaUltEdicion;
    private ResenaEstado estado;

    public ResenaDTO(long id, long idUsuario, long idJuego, boolean recomendado, String texto, double horasJugadas, LocalDate fechaPublicacion,
                     LocalDate fechaUltEdicion, ResenaEstado estado) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idJuego = idJuego;
        this.recomendado = recomendado;
        this.texto = texto;
        this.horasJugadas = horasJugadas;
        this.fechaPublicacion = fechaPublicacion;
        this.fechaUltEdicion = fechaUltEdicion;
        this.estado = estado;
    }

    public ResenaEstado getEstado() {
        return estado;
    }

    public long getId() {
        return id;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public long getIdJuego() {
        return idJuego;
    }

    public boolean isRecomendado() {
        return recomendado;
    }

    public String getTexto() {
        return texto;
    }

    public double getHorasJugadas() {
        return horasJugadas;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public LocalDate getFechaUltEdicion() {
        return fechaUltEdicion;
    }
}
