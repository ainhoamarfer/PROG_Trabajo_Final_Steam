package org.ainhoamarfer.modelo.entidad;

import org.ainhoamarfer.modelo.enums.CompraEstadoEnum;
import org.ainhoamarfer.modelo.enums.CompraMetodoPagoEnum;

import java.time.LocalDate;

public class CompraEntidad {

    private long id;
    private long usuarioId;
    private long juegoId;
    private LocalDate fechaCompra;
    private double precioOriginal;
    private double porcentajeDescuento;
    private double precioFinal;
    private CompraEstadoEnum estadoCompra;
    private CompraMetodoPagoEnum metodoPago;

    public CompraEntidad(long id, long usuarioId, long juegoId, LocalDate fechaCompra, double precioOriginal, double porcentajeDescuento, CompraMetodoPagoEnum metodoPago) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.juegoId = juegoId;
        this.fechaCompra = fechaCompra != null ? fechaCompra : LocalDate.now();
        this.precioOriginal = precioOriginal;
        this.porcentajeDescuento = porcentajeDescuento;
        this.precioFinal = calcularPrecioFinal(precioOriginal, porcentajeDescuento);
        this.estadoCompra = CompraEstadoEnum.PENDIENTE;
        this.metodoPago = metodoPago;
    }

    private double calcularPrecioFinal(double precioOriginal, double descuento) {
        if (descuento <= 0 || descuento > 100) {
            return precioOriginal;
        } else {
            return precioOriginal * (1 - descuento / 100.0);
        }
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
