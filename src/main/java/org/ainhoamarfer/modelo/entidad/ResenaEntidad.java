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

    public ResenaEstado getEstado() {
        return estado;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public long getJuegoId() {
        return juegoId;
    }

    public void setJuegoId(long juegoId) {
        this.juegoId = juegoId;
    }

    public boolean isRecomendado() {
        return recomendado;
    }

    public void setRecomendado(boolean recomendado) {
        this.recomendado = recomendado;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public double getHorasJugadas() {
        return horasJugadas;
    }

    public void setHorasJugadas(double horasJugadas) {
        this.horasJugadas = horasJugadas;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public LocalDate getFechaUltEdicion() {
        return fechaUltEdicion;
    }

    public void setFechaUltEdicion(LocalDate fechaUltEdicion) {
        this.fechaUltEdicion = fechaUltEdicion;
    }
}
