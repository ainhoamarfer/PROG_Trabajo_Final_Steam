package Modelo.Form;

import Modelo.Enums.CompraMetodoPagoEnum;

import java.time.LocalDate;

public class CompraForm {

    private int usuarioId;
    private int juegoId;
    private LocalDate fechaCompra;
    private CompraMetodoPagoEnum metodoPago;
    private double precioSinDes;
    private double descuento;

    public CompraForm(int usuarioId, int juegoId, LocalDate fechaCompra, CompraMetodoPagoEnum metodoPago, double precioSinDes, double descuento) {
        this.usuarioId = usuarioId;
        this.juegoId = juegoId;
        this.fechaCompra = fechaCompra;
        this.metodoPago = metodoPago;
        this.precioSinDes = precioSinDes;
        this.descuento = descuento;
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

    public CompraMetodoPagoEnum getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(CompraMetodoPagoEnum metodoPago) {
        this.metodoPago = metodoPago;
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
