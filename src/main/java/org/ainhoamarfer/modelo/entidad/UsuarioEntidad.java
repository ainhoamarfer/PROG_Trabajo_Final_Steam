package org.ainhoamarfer.modelo.entidad;

import jakarta.persistence.*;
import org.ainhoamarfer.modelo.enums.UsuarioEstadoCuenta;

import java.time.LocalDate;

@Entity
@Table(name = "usuarios")
public class UsuarioEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; //único

    @Column(name = "nombre_usuario")
    private String nombreUsuario; //único

    @Column(name = "email")
    private String email;

    @Column(name = "contrasena")
    private String contrasena;

    @Column(name = "nombre_real")
    private String nombreReal;

    @Column(name = "pais")
    private String pais;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNaci;

    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "saldo_cartera")
    private double saldoCartera;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_cuenta")
    private UsuarioEstadoCuenta estadoCuenta;

    public UsuarioEntidad() {
    }

    public UsuarioEntidad(long id, String nombreUsuario, String email, String contrasena, String nombreReal, String pais, LocalDate fechaNaci,LocalDate fechaRegistro,
                          String avatar, double saldoCartera) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.contrasena = contrasena;
        this.nombreReal = nombreReal;
        this.pais = pais;
        this.fechaNaci = fechaNaci;
        this.fechaRegistro = fechaRegistro != null ? fechaRegistro : LocalDate.now();
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
