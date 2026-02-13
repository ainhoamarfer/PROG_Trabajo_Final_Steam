package org.ainhoamarfer.Modelo.DTOs;

import org.ainhoamarfer.Modelo.Enums.CompraEstadoEnum;
import org.ainhoamarfer.Modelo.Enums.CompraMetodoPagoEnum;

import java.time.LocalDate;

public class CompraDTO {

    private long id;
    private long idUsuario;
    private UsuarioDTO usuario;
    private long idJuego;
    private JuegoDTO juego;
    private LocalDate fechaCompra;
    private double precioSinDes;
    private double descuento;
    private CompraEstadoEnum estadoCompra;
    private CompraMetodoPagoEnum metodoPago;

    public CompraDTO(long id, long idUsuario, UsuarioDTO usuario, long idJuego, JuegoDTO juego, LocalDate fechaCompra, double precioSinDes, double descuento, CompraEstadoEnum estadoCompra, CompraMetodoPagoEnum metodoPago) {
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

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public JuegoDTO getJuego() {
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
