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

    @Column(name = "precio_base")
    private double precioBase;

    @Column(name = "porcentaje_descuento")
    private int porcentajeDescuento;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_compra")
    private CompraEstadoEnum estadoCompra;

    @Enumerated(EnumType.STRING)
    @Column(name = "método_pago")
    private CompraMetodoPagoEnum metodoPago;


    public CompraEntidad() {
    }

    public CompraEntidad(long id, long usuarioId, long juegoId, LocalDate fechaCompra, double precioBase, int porcentajeDescuento, CompraMetodoPagoEnum metodoPago, CompraEstadoEnum estadoCompra) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.juegoId = juegoId;
        this.fechaCompra = fechaCompra != null ? fechaCompra : LocalDate.now();
        this.precioBase = precioBase;
        this.porcentajeDescuento = porcentajeDescuento;
        this.estadoCompra = estadoCompra;
        this.metodoPago = metodoPago;
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

    public int getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public CompraEstadoEnum getEstadoCompra() {
        return estadoCompra;
    }

    public CompraMetodoPagoEnum getMetodoPago() {
        return metodoPago;
    }

    public double getPrecioBase() {
        return precioBase;
    }
}
