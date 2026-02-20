package org.ainhoamarfer.Modelo.Form;

import org.ainhoamarfer.Modelo.DTOs.ErrorDTO;
import org.ainhoamarfer.Modelo.Enums.ResenaEstado;
import org.ainhoamarfer.Modelo.Enums.ErrorType;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ResenaForm {

    private int usuarioId;
    private int juegoId;
    private boolean recomendado;
    private String texto;
    private double horasJugadas;
    private LocalDate fechaPublicacion;
    private LocalDate fechaUltEdicion;
    private ResenaEstado estado;

    public ResenaForm(int usuarioId, int juegoId, boolean recomendado, String texto, double horasJugadas, LocalDate fechaPublicacion, LocalDate fechaUltEdicion, ResenaEstado estado) {
        this.usuarioId = usuarioId;
        this.juegoId = juegoId;
        this.recomendado = recomendado;
        this.texto = texto;
        this.horasJugadas = horasJugadas;
        this.fechaPublicacion = fechaPublicacion;
        this.fechaUltEdicion = fechaUltEdicion;
        this.estado = estado;
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

    public ResenaEstado getEstado() {
        return estado;
    }

    // Setters -------------------------
    public void setTexto(String texto) {
        this.texto = texto;
    }

    public void setEstado(ResenaEstado estado) {
        this.estado = estado;
    }

    public void setFechaUltEdicion(LocalDate fechaUltEdicion) {
        this.fechaUltEdicion = fechaUltEdicion;
    }

    public List<ErrorDTO> validar (ResenaForm form){
        List<ErrorDTO> errores = new ArrayList<>();

        // Usuario: obligatorio - TODO: Validar que el usuario existe en el sistema y que tiene el juego en su biblioteca
        if (form.usuarioId <= 0) {
            errores.add(new ErrorDTO("usuarioId", ErrorType.REQUERIDO));
        }

        // Juego: obligatorio - TODO: Validar que el juego existe en el sistema y que el usuario solo puede tener una reseña por juego
        if (form.juegoId <= 0) {
            errores.add(new ErrorDTO("juegoId", ErrorType.REQUERIDO));
        }

        // Recomendado: obligatorio boolean

        // Texto de la reseña: obligatorio, entre 50 y 8000 caracteres
        if (form.texto == null || form.texto.trim().isEmpty()) {
            errores.add(new ErrorDTO("texto", ErrorType.REQUERIDO));
        } else if (form.texto.length() < 50 || form.texto.length() > 8000) {
            errores.add(new ErrorDTO("texto", ErrorType.LONGITUD_INVALIDA));
        }

        // Horas jugadas: generada automáticamente desde la biblioteca, debe ser positivo o cero, máximo 1 decimal
        if (form.horasJugadas < 0) {
            errores.add(new ErrorDTO("horasJugadas", ErrorType.VALOR_NO_VALIDO));
        }
        BigDecimal bd = BigDecimal.valueOf(form.horasJugadas);
        if (bd.scale() > 1) {
            errores.add(new ErrorDTO("horasJugadas", ErrorType.FORMATO_INVALIDO));
        }

        // Fecha de publicación: generada automáticamente, no puede ser null
        if (form.fechaPublicacion == null) {
            errores.add(new ErrorDTO("fechaPublicacion", ErrorType.VALOR_NO_VALIDO));
        }

        // Fecha de última edición: opcional, pero si existe debe ser posterior a la fecha de publicación
        if (form.fechaUltEdicion != null && form.fechaPublicacion != null) {
            if (form.fechaUltEdicion.isBefore(form.fechaPublicacion) || form.fechaUltEdicion.isEqual(form.fechaPublicacion)) {
                errores.add(new ErrorDTO("fechaUltEdicion", ErrorType.VALOR_NO_VALIDO));
            }
        }

        // Estado: obligatorio, valor por defecto PUBLICADA
        if (form.estado == null) {
            form.estado = ResenaEstado.PUBLICADA;
        }

        return errores;
    }
}
