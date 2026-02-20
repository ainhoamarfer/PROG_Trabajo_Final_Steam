package org.ainhoamarfer.Modelo.Form;

import org.ainhoamarfer.Modelo.DTOs.ErrorDTO;
import org.ainhoamarfer.Modelo.Enums.ErrorType;
import org.ainhoamarfer.Modelo.Enums.JuegoClasificacionEdad;
import org.ainhoamarfer.Modelo.Enums.JuegoEstado;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class JuegoForm {

    private String titulo;
    private String descripcion;
    private String desarrollador;
    private LocalDate fechaLanzamiento;
    private Double precioBase;
    private Double descuentoActual;
    private String categoria;
    private String idiomas;
    private JuegoClasificacionEdad clasificacionEdad;
    private JuegoEstado estado;

    public JuegoForm(String titulo, String descripcion, String desarrollador, LocalDate fechaLanzamiento, Double precioBase, Double descuentoActual, String categoria, String idiomas, JuegoClasificacionEdad clasificacionEdad, JuegoEstado estado) {

        this.titulo = titulo;
        this.descripcion = descripcion;
        this.desarrollador = desarrollador;
        this.fechaLanzamiento = fechaLanzamiento;
        this.precioBase = precioBase;
        this.descuentoActual = descuentoActual;
        this.categoria = categoria;
        this.idiomas = idiomas;
        this.clasificacionEdad = clasificacionEdad;
        this.estado = estado;
    }

    public JuegoEstado getEstado() {
        return estado;
    }

    public JuegoClasificacionEdad getClasificacionEdad() {
        return clasificacionEdad;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public String getCategoria() {
        return categoria;
    }

    public double getDescuentoActual() {
        return descuentoActual;
    }

    public Double getPrecioBase() {
        return precioBase;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public String getDesarrollador() {
        return desarrollador;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public List<ErrorDTO> validar (JuegoForm form){
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
        if (precioBase == null) {
            errores.add(new ErrorDTO("precioBase", ErrorType.REQUERIDO));
        } else {
            if (precioBase < 0 || precioBase > 999.99) {
                errores.add(new ErrorDTO("precioBase", ErrorType.VALOR_NO_VALIDO));
            }
            BigDecimal bdPrecio = BigDecimal.valueOf(precioBase);
            if (bdPrecio.scale() > 2) {
                errores.add(new ErrorDTO("precioBase", ErrorType.FORMATO_INVALIDO));
            }
        }

        // Descuento actual: opcional, entero 0-100, valor por defecto 0
        if (descuentoActual != null) {
            if (descuentoActual < 0 || descuentoActual > 100) {
                errores.add(new ErrorDTO("descuentoActual", ErrorType.VALOR_NO_VALIDO));
            }
        }

        // Clasificación por edad: obligatoria, Debe ser uno de: {PEGI_3, PEGI_7, PEGI_12, PEGI_16, PEGI_18}
        if (clasificacionEdad == null) {
            errores.add(new ErrorDTO("clasificacionEdad", ErrorType.REQUERIDO));
        }

        // Idiomas: opcional; si se proporciona, al menos 1 idioma y longitud máxima 200
        if (idiomas != null && !idiomas.isBlank()) {
            if (idiomas.length() > 200) {
                errores.add(new ErrorDTO("idiomas", ErrorType.LONGITUD_INVALIDA));
            }
        }

        return errores;
    }
}
