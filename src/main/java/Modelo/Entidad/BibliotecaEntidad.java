package Modelo.Entidad;

import java.time.LocalDate;

public class BibliotecaEntidad {

    private long id;
    private int usuarioId;
    private int juegoId;
    private LocalDate fechaAdquisicion;
    private double tiempoJuego;
    private LocalDate fechaUltimaJugado;
    public boolean instalado;

    public BibliotecaEntidad(long id, int usuarioId, int juegoId, LocalDate fechaAdquisicion, double tiempoJuego, LocalDate fechaUltimaJugado, boolean instalado) {
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

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getJuegoId() {
        return juegoId;
    }

    public void setJuegoId(int juegoId) {
        this.juegoId = juegoId;
    }

    public LocalDate getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(LocalDate fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public double getTiempoJuego() {
        return tiempoJuego;
    }

    public void setTiempoJuego(double tiempoJuego) {
        this.tiempoJuego = tiempoJuego;
    }

    public LocalDate getFechaUltimaJugado() {
        return fechaUltimaJugado;
    }

    public void setFechaUltimaJugado(LocalDate fechaUltimaJugado) {
        this.fechaUltimaJugado = fechaUltimaJugado;
    }

    public boolean isInstalado() {
        return instalado;
    }

    public void setInstalado(boolean instalado) {
        this.instalado = instalado;
    }
}
