package org.ainhoamarfer.Modelo.Form;

import org.ainhoamarfer.Modelo.DTOs.ErrorDTO;
import org.ainhoamarfer.Modelo.Enums.CompraEstadoEnum;
import org.ainhoamarfer.Modelo.Enums.CompraMetodoPagoEnum;
import org.ainhoamarfer.Modelo.Enums.ErrorType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class CompraForm {

    private int usuarioId;
    private int juegoId;
    private LocalDate fechaCompra;
    private Double precioSinDes;
    private Double descuento;
    private CompraEstadoEnum estadoCompra;
    private CompraMetodoPagoEnum metodoPago;

    public CompraForm(int usuarioId, int juegoId, LocalDate fechaCompra, Double precioSinDes, Double descuento, CompraEstadoEnum estadoCompra, CompraMetodoPagoEnum metodoPago) {
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

    public Double getPrecioSinDes() {
        return precioSinDes;
    }

    public Double getDescuento() {
        return descuento;
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
        if (usuarioId <= 0) {
            errores.add(new ErrorDTO("usuarioId", ErrorType.REQUERIDO));
        }

        // Juego: obligatorio y debe existir; estado debe ser DISPONIBLE, PREVENTA o ACCESO_ANTICIPADO TODO: comprobar existencia y estado del juego en repositorio
        if (juegoId <= 0) {
            errores.add(new ErrorDTO("juegoId", ErrorType.REQUERIDO));
        }

        // Fecha de compra: generada automáticamente (ya asignada en constructor). No permitimos modificación por usuario.
        if (fechaCompra == null) {
            errores.add(new ErrorDTO("fechaCompra", ErrorType.VALOR_NO_VALIDO));
        }

        // Metodo de pago obligatorio
        if (metodoPago == null) {
            errores.add(new ErrorDTO("metodoPago", ErrorType.REQUERIDO));
        }

        // Precio sin descuento: obligatorio, debe ser positivo, máximo 2 decimales
        if (precioSinDes == null) {
            errores.add(new ErrorDTO("precioSinDes", ErrorType.REQUERIDO));
        } else {
            if (precioSinDes <= 0) {
                errores.add(new ErrorDTO("precioSinDes", ErrorType.VALOR_NO_VALIDO));
            }
            BigDecimal bd = BigDecimal.valueOf(precioSinDes);
            if (bd.scale() > 2) {
                errores.add(new ErrorDTO("precioSinDes", ErrorType.FORMATO_INVALIDO));
            }
        }

        // Descuento aplicado: opcional, entero 0-100, valor por defecto 0
        if (descuento != null) {
            if (descuento < 0 || descuento > 100) {
                errores.add(new ErrorDTO("descuento", ErrorType.VALOR_NO_VALIDO));
            }
        } else {
            descuento = 0.0;
        }

        // Estado de la compra: por defecto PENDIENTE (asignado en constructor)

        return errores;
    }
}
