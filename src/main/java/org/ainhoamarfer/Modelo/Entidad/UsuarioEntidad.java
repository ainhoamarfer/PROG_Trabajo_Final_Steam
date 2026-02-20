package org.ainhoamarfer.Modelo.Entidad;

import org.ainhoamarfer.Modelo.Enums.UsuarioEstadoCuenta;

import java.time.LocalDate;

public class UsuarioEntidad {

    private long id; //único
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

    public UsuarioEntidad(long id, String nombreUsuario, String email, String contrasena, String nombreReal, String pais, LocalDate fechaNaci, String avatar, double saldoCartera) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.contrasena = contrasena;
        this.nombreReal = nombreReal;
        this.pais = pais;
        this.fechaNaci = fechaNaci;
        this.fechaRegistro = LocalDate.now();
        this.avatar = avatar;
        this.saldoCartera = saldoCartera;
        this.estadoCuenta = UsuarioEstadoCuenta.ACTIVA;
    }

    public UsuarioEstadoCuenta getEstadoCuenta() {
        return estadoCuenta;
    }

    public long getId() {
        return id;
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
}
