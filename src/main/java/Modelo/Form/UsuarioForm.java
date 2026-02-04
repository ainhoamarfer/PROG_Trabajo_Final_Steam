package Modelo.Form;

import Modelo.DTOs.ErrorDto;
import Modelo.Enums.ErrorType;
import Modelo.Enums.Paises;
import Modelo.Enums.UsuarioEstadoCuenta;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;

public class UsuarioForm {

    private String nombreUsuario; //único
    private String email;
    private String contrasena;
    private String nombreReal;
    private String pais;
    private LocalDate fechaNaci;
    private UsuarioEstadoCuenta estadoCuenta;

    public UsuarioForm(String nombreUsuario, String email, String contrasena, String nombreReal, String pais, LocalDate fechaNaci, UsuarioEstadoCuenta estadoCuenta) {
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.contrasena = contrasena;
        this.nombreReal = nombreReal;
        this.pais = pais;
        this.fechaNaci = fechaNaci;
        this.estadoCuenta = UsuarioEstadoCuenta.ACTIVA;
    }

    public List<ErrorDto> validar() {
        List<ErrorDto> errores = new ArrayList<>();

        if (nombreUsuario == null || nombreUsuario.isBlank() || nombreUsuario.length() > 3 && nombreUsuario.length() > 20) {
            errores.add(new ErrorDto("nombre", ErrorType.REQUERIDO));
        }//comprobar único en el sistema en el controlador
        //falta que no pueda empezar por numero y alfanumericos

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

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getNombreReal() {
        return nombreReal;
    }

    public String getPais() {
        return pais;
    }

    public LocalDate getFechaNaci() {
        return fechaNaci;
    }

    public UsuarioEstadoCuenta getEstadoCuenta() {
        return estadoCuenta;
    }
}
