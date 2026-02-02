package Modelo.DTOs;

import Modelo.Enums.UsuarioEstadoCuenta;

import java.time.LocalDate;

public class UsuarioDTO {

    private long id; //único
    private String nombreUsuario; //único
    private String email;
    private String contrasena;
    private String nombreReal;
    private String pais;
    private LocalDate fechaNaci;
    private LocalDate fechaRegistro;
    private String avatar;
    private double saldoCartera = 0.00;
    private UsuarioEstadoCuenta estadoCuenta;

    public UsuarioDTO(long id, String nombreUsuario, String email, String contrasena, String nombreReal, String pais, LocalDate fechaNaci, LocalDate fechaRegistro, String avatar, double saldoCartera, UsuarioEstadoCuenta estadoCuenta) {

        this.id = id;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getNombreReal() {
        return nombreReal;
    }

    public void setNombreReal(String nombreReal) {
        this.nombreReal = nombreReal;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public LocalDate getFechaNaci() {
        return fechaNaci;
    }

    public void setFechaNaci(LocalDate fechaNaci) {
        this.fechaNaci = fechaNaci;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public double getSaldoCartera() {
        return saldoCartera;
    }

    public void setSaldoCartera(double saldoCartera) {
        this.saldoCartera = saldoCartera;
    }

    public UsuarioEstadoCuenta getEstadoCuenta() {
        return estadoCuenta;
    }

    public void setEstadoCuenta(UsuarioEstadoCuenta estadoCuenta) {
        this.estadoCuenta = estadoCuenta;
    }
}
