package Modelo.Form;

import Modelo.DTOs.ErrorDto;
import Modelo.Enums.ErrorType;
import Modelo.Enums.JuegoClasificacionEdad;
import Modelo.Enums.JuegoEstado;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JuegoForm {

    private String titulo;
    private String desarrollador;
    private LocalDate fechaLanzamiento;
    private double precioBase;
    private JuegoClasificacionEdad clasificacionEdad;
    private JuegoEstado estadoActual;

    public JuegoForm(String titulo, String desarrollador, LocalDate fechaLanzamiento, double precioBase, JuegoClasificacionEdad clasificacionEdad, JuegoEstado estadoActual) {

        this.titulo = titulo;
        this.desarrollador = desarrollador;
        this.fechaLanzamiento = fechaLanzamiento;
        this.precioBase = precioBase;
        this.clasificacionEdad = clasificacionEdad;
        this.estadoActual = estadoActual;
    }

    public List<ErrorDto> validar() {
        List<ErrorDto> errores = new ArrayList<>();

        if (titulo == null || titulo.isBlank() || titulo.length() > 1 && titulo.length() > 100) {
            errores.add(new ErrorDto("titulo", ErrorType.REQUERIDO));
        }//comprobar único en el sistema en el controlador

        if (nombreReal == null || nombreReal.isBlank() || nombreReal.length() > 2 && nombreReal.length() > 50) {
            errores.add(new ErrorDto("nombre", ErrorType.REQUERIDO));
        }

        if (email == null || email.isBlank() || !email.contains("@")) {
            errores.add(new ErrorDto("email", ErrorType.FORMATO_INVALIDO));
        }//comprobar único en el sistema en el controlador

        if (contrasena == null || contrasena.length() < 8) {
            errores.add(new ErrorDto("contrasena", ErrorType.FORMATO_INVALIDO));
        }//al menos una mayúscula, una minúscula y un número

        if (pais == null || pais.isBlank() || pais != Paises) {
            errores.add(new ErrorDto("pais", ErrorType.REQUERIDO));
        }
        if (fechaNaci == null || fechaNaci > ) {
            errores.add(new ErrorDto("precio", ErrorType.VALOR_DEMASIADO_BAJO));
        }

        if (estadoCuenta != null) {}
        return errores;
    }

    public String getTitulo() {
        return titulo;
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

    public JuegoClasificacionEdad getClasificacionEdad() {
        return clasificacionEdad;
    }

    public JuegoEstado getEstadoActual() {
        return estadoActual;
    }
}
