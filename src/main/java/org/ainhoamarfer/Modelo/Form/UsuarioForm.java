package org.ainhoamarfer.Modelo.Form;

import org.ainhoamarfer.Modelo.DTOs.ErrorDTO;
import org.ainhoamarfer.Modelo.Enums.UsuarioEstadoCuenta;

import java.time.LocalDate;
import java.util.List;

public class UsuarioForm {

    private String nombreUsuario; //único
    private String email;
    private String contrasena;
    private String nombreReal;
    private String pais;
    private LocalDate fechaNaci;
    private LocalDate fechaRegistro;
    private String avatar;
    private double saldoCartera;
    private UsuarioEstadoCuenta estadoCuenta;

    public UsuarioForm(String nombreUsuario, String email, String contrasena, String nombreReal, String pais, LocalDate fechaNaci, LocalDate fechaRegistro, String avatar, double saldoCartera, UsuarioEstadoCuenta estadoCuenta) {
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.contrasena = contrasena;
        this.nombreReal = nombreReal;
        this.pais = pais;
        this.fechaNaci = fechaNaci;
        this.fechaRegistro = fechaRegistro;
        this.avatar = avatar;
        this.saldoCartera = saldoCartera;
        this.estadoCuenta = estadoCuenta;
    }

    public UsuarioEstadoCuenta getEstadoCuenta() {
        return estadoCuenta;
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

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public String getAvatar() {
        return avatar;
    }

    public double getSaldoCartera() {
        return saldoCartera;
    }



    public List<ErrorDTO> validar (UsuarioForm form){

        List<ErrorDTO> errores = form.validar(form);

        if (nombreUsuario == null || nombreUsuario.isBlank()) {
            errores.add(new ErrorDTO("nombre", ErrorType.REQUERIDO));
        }

        if (email == null || email.isBlank() || !email.contains("@")) {
            errores.add(new ErrorDTO("email", ErrorType.FORMATO_INVALIDO));
        }

        if (contrasena == null || contrasena.length() < 6) {
            errores.add(new ErrorDTO("contrasena", ErrorType.FORMATO_INVALIDO));
        }

        return errores;
    }
}
