package org.ainhoamarfer.Modelo.DTOs;

import java.time.LocalDate;

public class ResenaDTO {

    private long id;
    private long idUsuario;
    private UsuarioDTO usuario;
    private long idJuego;
    private JuegoDTO juego;
    private boolean recomendado;
    private String texto;
    private double horasJugadas;
    private LocalDate fechaPublicacion;
    private LocalDate fechaUltEdicion;

    public ResenaDTO(long id, long idUsuario, UsuarioDTO usuario, long idJuego, JuegoDTO juego, boolean recomendado, String texto, double horasJugadas, LocalDate fechaPublicacion, LocalDate fechaUltEdicion) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.idJuego = idJuego;
        this.juego = juego;
        this.recomendado = recomendado;
        this.texto = texto;
        this.horasJugadas = horasJugadas;
        this.fechaPublicacion = fechaPublicacion;
        this.fechaUltEdicion = fechaUltEdicion;
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

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public JuegoDTO getJuego() {
        return juego;
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
