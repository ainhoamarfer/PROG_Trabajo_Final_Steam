package org.ainhoamarfer.modelo.form;

import org.ainhoamarfer.modelo.dtos.ErrorDTO;
import org.ainhoamarfer.modelo.enums.CompraEstadoEnum;
import org.ainhoamarfer.modelo.enums.CompraMetodoPagoEnum;
import org.ainhoamarfer.modelo.enums.ErrorType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class CompraForm {

    private long usuarioId;
    private long juegoId;
    private LocalDate fechaCompra;
    private Double precioBase;
    private int descuentoActual;
    private CompraEstadoEnum estadoCompra;
    private CompraMetodoPagoEnum metodoPago;

    public CompraForm(long usuarioId, long juegoId, LocalDate fechaCompra, Double precioBase, int descuentoActual, CompraEstadoEnum estadoCompra, CompraMetodoPagoEnum metodoPago) {
        this.usuarioId = usuarioId;
        this.juegoId = juegoId;
        this.precioBase = precioBase;
        this.descuentoActual = descuentoActual;
        this.estadoCompra = estadoCompra;
        this.metodoPago = metodoPago;
        this.fechaCompra = fechaCompra;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public long getUsuarioId() {
        return usuarioId;
    }

    public long getJuegoId() {
        return juegoId;
    }

    public Double getPrecioBase() {
        return precioBase;
    }

    public int getDescuentoActual() {
        return descuentoActual;
    }

    public CompraEstadoEnum getEstadoCompra() {
        return estadoCompra;
    }

    public CompraMetodoPagoEnum getMetodoPago() {
        return metodoPago;
    }

    public List<ErrorDTO> validar (CompraForm form){
        List<ErrorDTO> errores = new ArrayList<>();

        // Usuario: obligatorio y debe existir; la cuenta debe estar ACTIVA TODO: comprobar existencia y estado ACTIVA del usuario en repositorio
        if (usuarioId <= 0) errores.add(new ErrorDTO("usuarioId", ErrorType.REQUERIDO));

        // Juego: obligatorio y debe existir; estado debe ser DISPONIBLE, PREVENTA o ACCESO_ANTICIPADO TODO: comprobar existencia y estado del juego en repositorio
        if (juegoId <= 0) errores.add(new ErrorDTO("juegoId", ErrorType.REQUERIDO));

        // Metodo de pago obligatorio
        if (metodoPago == null) errores.add(new ErrorDTO("metodoPago", ErrorType.REQUERIDO));

        // Precio sin descuento: obligatorio, debe ser positivo, máximo 2 decimales
        if (precioBase == null) {
            errores.add(new ErrorDTO("precioSinDes", ErrorType.REQUERIDO));
        } else {
            if (precioBase <= 0) {
                errores.add(new ErrorDTO("precioSinDes", ErrorType.VALOR_NO_VALIDO));
            }
            BigDecimal bd = BigDecimal.valueOf(precioBase);
            if (bd.scale() > 2) {
                errores.add(new ErrorDTO("precioSinDes", ErrorType.FORMATO_INVALIDO));
            }
        }

        // Descuento aplicado: opcional, entero 0-100, valor por defecto 0

        if (descuentoActual < 0 || descuentoActual > 100) {
            errores.add(new ErrorDTO("descuento", ErrorType.VALOR_NO_VALIDO));
        } else {
            descuentoActual = 0;
        }


        // Estado de la compra: por defecto PENDIENTE (asignado en constructor)

        return errores;
    }
}
