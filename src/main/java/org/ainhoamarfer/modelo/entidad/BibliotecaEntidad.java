package org.ainhoamarfer.modelo.entidad;

import java.time.LocalDate;

public class BibliotecaEntidad {

    private long id;
    private long usuarioId;
    private long juegoId;
    private LocalDate fechaAdquisicion;
    private double tiempoJuego;
    private LocalDate fechaUltimaJugado;
    public boolean instalado;

    public BibliotecaEntidad(long id, long usuarioId, long juegoId, LocalDate fechaAdquisicion, double tiempoJuego, LocalDate fechaUltimaJugado) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.juegoId = juegoId;
        this.fechaAdquisicion = fechaAdquisicion;
        this.tiempoJuego = tiempoJuego;
        this.fechaUltimaJugado = fechaUltimaJugado;
        this.instalado = false;
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

    public long getJuegoId() {
        return juegoId;
    }

    public LocalDate getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public double getTiempoJuego() {
        return tiempoJuego;
    }

    public LocalDate getFechaUltimaJugado() {
        return fechaUltimaJugado;
    }

    public boolean isInstalado() {
        return instalado;
    }
}
