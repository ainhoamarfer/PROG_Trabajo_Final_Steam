package org.ainhoamarfer.Modelo.DTOs;

import org.ainhoamarfer.Modelo.Enums.UsuarioEstadoCuenta;

import java.time.LocalDate;

public class UsuarioDTO {
    //sin contraseña
    private long id; //único
    private String nombreUsuario; //único
    private String email;
    private String nombreReal;
    private String pais;
    private LocalDate fechaNaci;
    private LocalDate fechaRegistro;
    private String avatar;
    private double saldoCartera = 0.00;
    private UsuarioEstadoCuenta estadoCuenta;

    public UsuarioDTO(long id, String nombreUsuario, String email, String contrasena, String nombreReal, LocalDate fechaNaci, LocalDate fechaRegistro, String avatar, double saldoCartera, UsuarioEstadoCuenta estadoCuenta) {

        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.nombreReal = nombreReal;
        this.pais = pais;
        this.fechaNaci = fechaNaci;
        this.fechaRegistro = fechaRegistro;
        this.avatar = avatar;
        this.saldoCartera = saldoCartera;
        this.estadoCuenta = estadoCuenta;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getEmail() {
        return email;
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

    public UsuarioEstadoCuenta getEstadoCuenta() {
        return estadoCuenta;
    }
}
