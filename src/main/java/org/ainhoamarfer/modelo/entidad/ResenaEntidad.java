package org.ainhoamarfer.modelo.entidad;

import org.ainhoamarfer.modelo.enums.ResenaEstado;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "resenas")
public class ResenaEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "usuario_id", nullable = false)
    private long usuarioId;

    @Column(name = "juego_id", nullable = false)
    private long juegoId;

    @Column(name = "recomendado")
    private boolean recomendado;

    @Column(name = "texto", length = 8000)
    private String texto;

    @Column(name = "horas_jugadas")
    private double horasJugadas;

    @Column(name = "fecha_publicacion")
    private LocalDate fechaPublicacion;

    @Column(name = "fecha_ult_edicion")
    private LocalDate fechaUltEdicion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private ResenaEstado estado;

    public ResenaEntidad() {
    }

    public ResenaEntidad(long id, long usuarioId, long juegoId, boolean recomendado, String texto, double horasJugadas, LocalDate fechaPublicacion, LocalDate fechaUltEdicion, ResenaEstado estado) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.juegoId = juegoId;
        this.recomendado = recomendado;
        this.texto = texto;
        this.horasJugadas = horasJugadas;
        this.fechaPublicacion = fechaPublicacion;
        this.fechaUltEdicion = fechaUltEdicion;
        this.estado = estado;
    }

    public long getId() {
        return id;
    }

    public long getUsuarioId() {
        return usuarioId;
    }

    public long getJuegoId() {
        return juegoId;
    }

    public boolean isRecomendado() {
        return recomendado;
    }

    public String getTexto() {
        return texto;
    }

    public double getHorasJugadas() {
        return horasJugadas;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public LocalDate getFechaUltEdicion() {
        return fechaUltEdicion;
    }

    public ResenaEstado getEstado() {
        return estado;
    }
}
