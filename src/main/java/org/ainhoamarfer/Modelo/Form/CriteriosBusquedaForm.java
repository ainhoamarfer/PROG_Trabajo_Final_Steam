package org.ainhoamarfer.Modelo.Form;

import org.ainhoamarfer.Modelo.DTOs.ErrorDTO;
import org.ainhoamarfer.Modelo.Enums.ErrorType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CriteriosBusquedaForm {

    private String titulo;
    private String descripcion;
    private String desarrollador;
    private LocalDate fechaLanzamiento;
    private double precioBase;
    private String categoria;

    public CriteriosBusquedaForm(String titulo, String descripcion, String desarrollador, LocalDate fechaLanzamiento, double precioBase, String categoria) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.desarrollador = desarrollador;
        this.fechaLanzamiento = fechaLanzamiento;
        this.precioBase = precioBase;
        this.categoria = categoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getDesarrollador() {
        return desarrollador;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public String getCategoria() {
        return categoria;
    }

    public List<ErrorDTO> validar (CriteriosBusquedaForm listaCriterios){
        List<ErrorDTO> errores = new ArrayList<>();

        // Título: obligatorio, longitud 1-100, único - TODO: validar que el título sea único en el sistema
        if (titulo == null || titulo.isBlank()) {
            errores.add(new ErrorDTO("titulo", ErrorType.REQUERIDO));
        } else {
            if (titulo.length() < 1 || titulo.length() > 100) {
                errores.add(new ErrorDTO("titulo", ErrorType.LONGITUD_INVALIDA));
            }

        }

        // Descripción: opcional, longitud máxima 2000
        if (descripcion != null && !descripcion.isBlank()) {
            if (descripcion.length() > 2000) {
                errores.add(new ErrorDTO("descripcion", ErrorType.LONGITUD_INVALIDA));
            }
        }

        // Desarrollador: obligatorio, longitud 2-100
        if (desarrollador == null || desarrollador.isBlank()) {
            errores.add(new ErrorDTO("desarrollador", ErrorType.REQUERIDO));
        } else {
            if (desarrollador.length() < 2 || desarrollador.length() > 100) {
                errores.add(new ErrorDTO("desarrollador", ErrorType.LONGITUD_INVALIDA));
            }
        }

        // Fecha de lanzamiento: obligatoria (puede ser futura)
        if (fechaLanzamiento == null) {
            errores.add(new ErrorDTO("fechaLanzamiento", ErrorType.REQUERIDO));
        }

        // Precio base: obligatorio, debe ser positivo o cero (juegos gratuitos), 0.00 - 999.99, max 2 decimales
        if (precioBase < 0 || precioBase > 999.99) {
            errores.add(new ErrorDTO("precioBase", ErrorType.VALOR_NO_VALIDO));
        }
        BigDecimal bdPrecio = BigDecimal.valueOf(precioBase);
        if (bdPrecio.scale() > 2) {
            errores.add(new ErrorDTO("precioBase", ErrorType.FORMATO_INVALIDO));
        }


        return errores;
    }
}
