package org.ainhoamarfer.modelo.dtos;

import org.ainhoamarfer.modelo.entidad.BibliotecaEntidad;
import org.ainhoamarfer.modelo.entidad.JuegoEntidad;
import org.ainhoamarfer.modelo.entidad.UsuarioEntidad;

import java.time.LocalDate;
import java.util.Optional;

public class BibliotecaDTO {

    private long id;
    private long idUsuario;
    private UsuarioDTO usuario;
    private long idJuego;
    private JuegoDTO juego;
    private LocalDate fechaAdquisicion;
    private double tiempoJuego;
    private LocalDate fechaUltimaJugado;
    public boolean instalado = false;

    public BibliotecaDTO(long id, long idUsuario, UsuarioDTO usuario, long idJuego, JuegoDTO juego, LocalDate fechaAdquisicion, double tiempoJuego, LocalDate fechaUltimaJugado, boolean instalado) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.idJuego = idJuego;
        this.juego = juego;
        this.fechaAdquisicion = fechaAdquisicion;
        this.tiempoJuego = tiempoJuego;
        this.fechaUltimaJugado = fechaUltimaJugado;
        this.instalado = instalado;
    }

    public long getId() {
        return id;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public long getIdJuego() {
        return idJuego;
    }

    public JuegoDTO getJuego() {
        return juego;
    }

    public LocalDate getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public double getTiempoJuego() {
        return tiempoJuego;
    }

    public LocalDate getFechaUltimaJugado() {
        return fechaUltimaJugado;
    }

    public boolean isInstalado() {
        return instalado;
    }
}
