package org.ainhoamarfer.modelo.entidad;

import org.ainhoamarfer.modelo.enums.ResenaEstado;

import java.time.LocalDate;

public class ResenaEntidad {

    private long id;
    private long usuarioId;
    private long juegoId;
    private boolean recomendado;
    private String texto;
    private double horasJugadas;
    private LocalDate fechaPublicacion;
    private LocalDate fechaUltEdicion;
    private ResenaEstado estado;

    public ResenaEntidad(long id, long usuarioId, long juegoId, boolean recomendado, String texto, double horasJugadas, LocalDate fechaPublicacion, LocalDate fechaUltEdicion, ResenaEstado estado) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.juegoId = juegoId;
        this.recomendado = recomendado;
        this.texto = texto;
        this.horasJugadas = horasJugadas;
        this.fechaPublicacion = fechaPublicacion;
        this.fechaUltEdicion = fechaUltEdicion;
        this.estado = estado;
    }

    public long getId() {
        return id;
    }

    public long getUsuarioId() {
        return usuarioId;
    }

    public long getJuegoId() {
        return juegoId;
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

    public ResenaEstado getEstado() {
        return estado;
    }
}
