package org.ainhoamarfer.modelo.dtos;

import org.ainhoamarfer.modelo.entidad.JuegoEntidad;
import org.ainhoamarfer.modelo.entidad.UsuarioEntidad;
import org.ainhoamarfer.modelo.enums.CompraEstadoEnum;
import org.ainhoamarfer.modelo.enums.CompraMetodoPagoEnum;

import java.time.LocalDate;

public class CompraDTO {

    private long id;
    private long idUsuario;
    private UsuarioDTO usuario;
    private long idJuego;
    private JuegoDTO juego;
    private LocalDate fechaCompra;
    private double precioFinal;
    private double porcentajeDescuento;
    private double precioOriginal;
    private CompraEstadoEnum estadoCompra;
    private CompraMetodoPagoEnum metodoPago;

    public CompraDTO(long id, long idUsuario, UsuarioDTO usuario, long idJuego, JuegoDTO juego, LocalDate fechaCompra, double precioFinal, double porcentajeDescuento,double precioOriginal, CompraEstadoEnum estadoCompra, CompraMetodoPagoEnum metodoPago) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.idJuego = idJuego;
        this.juego = juego;
        this.fechaCompra = fechaCompra;
        this.precioFinal = precioFinal;
        this.porcentajeDescuento = porcentajeDescuento;
        this.precioOriginal = precioOriginal;
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

    public double getPrecioFinal() {
        return precioFinal;
    }

    public double getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public CompraEstadoEnum getEstadoCompra() {
        return estadoCompra;
    }

    public CompraMetodoPagoEnum getMetodoPago() {
        return metodoPago;
    }

    public double getPrecioOriginal() {
        return precioOriginal;
    }
}
