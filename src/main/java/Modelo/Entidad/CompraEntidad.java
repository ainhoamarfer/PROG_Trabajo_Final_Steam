package Modelo.Entidad;

import Modelo.Enums.CompraEstadoEnum;
import Modelo.Enums.CompraMetodoPagoEnum;

import java.time.LocalDate;

public class CompraEntidad {

    private long id;
    private int usuarioId;
    private int juegoId;
    private LocalDate fechaCompra;
    private double precioSinDes;
    private double descuento;
    private CompraEstadoEnum estadoCompra;
    private CompraMetodoPagoEnum metodoPago;

    public CompraEntidad(long id, int usuarioId, int juegoId, LocalDate fechaCompra, double precioSinDes, double descuento, CompraEstadoEnum estadoCompra, CompraMetodoPagoEnum metodoPago) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.juegoId = juegoId;
        this.fechaCompra = fechaCompra;
        this.precioSinDes = precioSinDes;
        this.descuento = descuento;
        this.estadoCompra = estadoCompra;
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

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getJuegoId() {
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

    public double getPrecioSinDes() {
        return precioSinDes;
    }

    public void setPrecioSinDes(double precioSinDes) {
        this.precioSinDes = precioSinDes;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }
}
