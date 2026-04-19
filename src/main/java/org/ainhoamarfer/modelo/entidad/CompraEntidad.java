package org.ainhoamarfer.modelo.entidad;

import org.ainhoamarfer.modelo.enums.CompraEstadoEnum;
import org.ainhoamarfer.modelo.enums.CompraMetodoPagoEnum;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "compras")
public class CompraEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "usuario_id", nullable = false)
    private long usuarioId;

    @Column(name = "juego_id", nullable = false)
    private long juegoId;

    @Column(name = "fecha_compra")
    private LocalDate fechaCompra;

    @Column(name = "precio_original")
    private double precioOriginal;

    @Column(name = "porcentaje_descuento")
    private double porcentajeDescuento;

    @Column(name = "precio_final")
    private double precioFinal;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_compra")
    private CompraEstadoEnum estadoCompra;

    @Enumerated(EnumType.STRING)
    @Column(name = "método_pago")
    private CompraMetodoPagoEnum metodoPago;


    public CompraEntidad() {
    }

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

    public void setEstadoCompra(CompraEstadoEnum estadoCompra) {
        this.estadoCompra = estadoCompra;
    }

    public void setMetodoPago(CompraMetodoPagoEnum metodoPago) {
        this.metodoPago = metodoPago;
    }

}
