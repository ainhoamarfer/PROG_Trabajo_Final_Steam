package org.ainhoamarfer.modelo.dtos;

import org.ainhoamarfer.modelo.entidad.JuegoEntidad;
import org.ainhoamarfer.modelo.entidad.UsuarioEntidad;
import org.ainhoamarfer.modelo.enums.CompraEstadoEnum;
import org.ainhoamarfer.modelo.enums.CompraMetodoPagoEnum;

import java.time.LocalDate;
import java.util.Optional;

public class CompraDTO {

    private long id;
    private long idUsuario;
    private Optional<UsuarioEntidad> usuario;
    private long idJuego;
    private Optional<JuegoEntidad> juego;
    private LocalDate fechaCompra;
    private double precioSinDes;
    private double descuento;
    private CompraEstadoEnum estadoCompra;
    private CompraMetodoPagoEnum metodoPago;

    public CompraDTO(long id, long idUsuario, Optional<UsuarioEntidad> usuario, long idJuego, Optional<JuegoEntidad> juego, LocalDate fechaCompra, double precioSinDes, double descuento, CompraEstadoEnum estadoCompra, CompraMetodoPagoEnum metodoPago) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.idJuego = idJuego;
        this.juego = juego;
        this.fechaCompra = fechaCompra;
        this.precioSinDes = precioSinDes;
        this.descuento = descuento;
        this.estadoCompra = estadoCompra;
        this.metodoPago = metodoPago;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public long getIdJuego() {
        return idJuego;
    }

    public long getId() {
        return id;
    }

    public Optional<UsuarioEntidad> getUsuario() {
        return usuario;
    }

    public Optional<JuegoEntidad> getJuego() {
        return juego;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public double getPrecioSinDes() {
        return precioSinDes;
    }

    public double getDescuento() {
        return descuento;
    }

    public CompraEstadoEnum getEstadoCompra() {
        return estadoCompra;
    }

    public CompraMetodoPagoEnum getMetodoPago() {
        return metodoPago;
    }
}
