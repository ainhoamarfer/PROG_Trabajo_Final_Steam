package org.ainhoamarfer.modelo.entidad;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "bibliotecas")
public class BibliotecaEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "usuario_id")
    private long usuarioId;

    @Column(name = "juego_id")
    private long juegoId;

    @Column(name = "fecha_adquisicion")
    private LocalDate fechaAdquisicion;

    @Column(name = "tiempo_juego")
    private double tiempoJuego;

    @Column(name = "fecha_ultima_jugado")
    private LocalDate fechaUltimaJugado;

    @Column(name = "instalado")
    private boolean instalado;

    // Constructor sin argumentos
    public BibliotecaEntidad() {
    }

    public BibliotecaEntidad(long id, long usuarioId, long juegoId, LocalDate fechaAdquisicion, double tiempoJuego, LocalDate fechaUltimaJugado) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.juegoId = juegoId;
        this.fechaAdquisicion = fechaAdquisicion;
        this.tiempoJuego = tiempoJuego;
        this.fechaUltimaJugado = fechaUltimaJugado;
        this.instalado = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUsuarioId() {
        return usuarioId;
    }

    public long getJuegoId() {
        return juegoId;
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
