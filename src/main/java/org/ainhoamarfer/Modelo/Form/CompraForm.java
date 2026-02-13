package org.ainhoamarfer.Modelo.Form;

import org.ainhoamarfer.Modelo.Enums.CompraEstadoEnum;
import org.ainhoamarfer.Modelo.Enums.CompraMetodoPagoEnum;

import java.time.LocalDate;

public class CompraForm {

    private int usuarioId;
    private int juegoId;
    private LocalDate fechaCompra;
    private double precioSinDes;
    private double descuento;
    private CompraEstadoEnum estadoCompra;
    private CompraMetodoPagoEnum metodoPago;

    public CompraForm(int usuarioId, int juegoId, LocalDate fechaCompra, double precioSinDes, double descuento, CompraEstadoEnum estadoCompra, CompraMetodoPagoEnum metodoPago) {
        this.usuarioId = usuarioId;
        this.juegoId = juegoId;
        this.fechaCompra = fechaCompra;
        this.precioSinDes = precioSinDes;
        this.descuento = descuento;
        this.estadoCompra = estadoCompra;
        this.metodoPago = metodoPago;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public int getJuegoId() {
        return juegoId;
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
