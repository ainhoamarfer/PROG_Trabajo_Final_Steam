package Modelo.DTOs;

import java.time.LocalDate;

public class ResenaDTO {

    private long id;
    private UsuarioDTO usuario;
    private JuegoDTO juego;
    private boolean recomendado;
    private String texto;
    private double horasJugadas;
    private LocalDate fechaPublicacion;
    private LocalDate fechaUltEdicion;

    public ResenaDTO(long id, UsuarioDTO usuario, JuegoDTO juego, boolean recomendado, String texto, double horasJugadas, LocalDate fechaPublicacion, LocalDate fechaUltEdicion) {
        this.id = id;
        this.usuario = usuario;
        this.juego = juego;
        this.recomendado = recomendado;
        this.texto = texto;
        this.horasJugadas = horasJugadas;
        this.fechaPublicacion = fechaPublicacion;
        this.fechaUltEdicion = fechaUltEdicion;
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
