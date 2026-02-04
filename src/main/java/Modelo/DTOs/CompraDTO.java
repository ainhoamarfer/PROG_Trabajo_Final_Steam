package Modelo.DTOs;

import Modelo.Enums.CompraEstadoEnum;
import Modelo.Enums.CompraMetodoPagoEnum;

import java.time.LocalDate;

public class CompraDTO {

    private long id;
    private UsuarioDTO usuario;
    private JuegoDTO juego;
    private LocalDate fechaCompra;
    private double precioSinDes;
    private double descuento;
    private CompraEstadoEnum estado;
    private CompraMetodoPagoEnum metodoPago;

    public CompraDTO(long id, UsuarioDTO usuario, JuegoDTO juego, LocalDate fechaCompra, double precioSinDes, double descuento, CompraEstadoEnum estado, CompraMetodoPagoEnum metodoPago) {

        this.id = id;
        this.usuario = usuario;
        this.juego = juego;
        this.fechaCompra = fechaCompra;
        this.precioSinDes = precioSinDes;
        this.descuento = descuento;
        this.estado = estado;
        this.metodoPago = metodoPago;
    }

    public CompraEstadoEnum getEstado() {
        return estado;
    }

    public void setEstado(CompraEstadoEnum estado) {
        this.estado = estado;
    }

    public CompraMetodoPagoEnum getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(CompraMetodoPagoEnum metodoPago) {
        this.metodoPago = metodoPago;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public JuegoDTO getJuego() {
        return juego;
    }

    public void setJuego(JuegoDTO juego) {
        this.juego = juego;
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
