package Modelo.Form;

import java.time.LocalDate;

public class ResenaForm {

    private int usuarioId;
    private int juegoId;
    private boolean recomendado;
    private String texto;
    private double horasJugadas;
    private LocalDate fechaPublicacion;
    private LocalDate fechaUltEdicion;

    public ResenaForm(int usuarioId, int juegoId, boolean recomendado, String texto, double horasJugadas, LocalDate fechaPublicacion, LocalDate fechaUltEdicion) {
        this.usuarioId = usuarioId;
        this.juegoId = juegoId;
        this.recomendado = recomendado;
        this.texto = texto;
        this.horasJugadas = horasJugadas;
        this.fechaPublicacion = fechaPublicacion;
        this.fechaUltEdicion = fechaUltEdicion;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public int getJuegoId() {
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
}
