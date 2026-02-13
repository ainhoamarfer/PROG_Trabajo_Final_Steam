package org.ainhoamarfer.Modelo.DTOs;

import java.time.LocalDate;

public class BibliotecaDTO {

    private long id;
    private long idUsuario;
    private UsuarioDTO usuario;
    private long idJuego;
    private JuegoDTO juego;
    private LocalDate fechaAdquisicion;
    private double tiempoJuego;
    private LocalDate fechaUltimaJugado;
    public boolean instalado = false;

    public BibliotecaDTO(long id, long idUsuario, UsuarioDTO usuario, long idJuego, JuegoDTO juego, LocalDate fechaAdquisicion, double tiempoJuego, LocalDate fechaUltimaJugado, boolean instalado) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.idJuego = idJuego;
        this.juego = juego;
        this.fechaAdquisicion = fechaAdquisicion;
        this.tiempoJuego = tiempoJuego;
        this.fechaUltimaJugado = fechaUltimaJugado;
        this.instalado = instalado;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public long getIdJuego() {
        return idJuego;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public JuegoDTO getJuego() {
        return juego;
    }

    public void setJuego(JuegoDTO juego) {
        this.juego = juego;
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
