package org.ainhoamarfer.Modelo.Form;

import java.time.LocalDate;

public class BibliotecaForm {

    private int usuarioId;
    private int juegoId;
    private LocalDate fechaAdquisicion;
    private double tiempoJuego;
    private LocalDate fechaUltimaJugado;
    public boolean instalado;

    public BibliotecaForm(int usuarioId, int juegoId, LocalDate fechaAdquisicion, double tiempoJuego, LocalDate fechaUltimaJugado, boolean instalado) {
        this.usuarioId = usuarioId;
        this.juegoId = juegoId;
        this.fechaAdquisicion = fechaAdquisicion;
        this.tiempoJuego = tiempoJuego;
        this.fechaUltimaJugado = fechaUltimaJugado;
        this.instalado = false;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public int getJuegoId() {
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
