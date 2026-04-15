package org.ainhoamarfer.modelo.entidad;

import org.ainhoamarfer.modelo.enums.CompraEstadoEnum;
import org.ainhoamarfer.modelo.enums.CompraMetodoPagoEnum;

import java.time.LocalDate;

public class CompraEntidad {

    private long id;
    private long usuarioId;
    private long juegoId;
    private LocalDate fechaCompra;
    private double precioBase;
    private double descuento;
    private CompraEstadoEnum estadoCompra;
    private CompraMetodoPagoEnum metodoPago;

    public CompraEntidad(long id, long usuarioId, long juegoId, LocalDate fechaCompra, double precioBase, double descuento, CompraMetodoPagoEnum metodoPago) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.juegoId = juegoId;
        this.fechaCompra = LocalDate.now();
        this.precioBase = precioBase;
        this.descuento = descuento;
        this.estadoCompra = CompraEstadoEnum.PENDIENTE;
        this.metodoPago = metodoPago;
    }

    public CompraMetodoPagoEnum getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(CompraMetodoPagoEnum metodoPago) {
        this.metodoPago = metodoPago;
    }

    public CompraEstadoEnum getEstadoCompra() {
        return estadoCompra;
    }

    public void setEstadoCompra(CompraEstadoEnum estadoCompra) {
        this.estadoCompra = estadoCompra;
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

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public long getJuegoId() {
        return juegoId;
    }

    public void setJuegoId(int juegoId) {
        this.juegoId = juegoId;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }
}
