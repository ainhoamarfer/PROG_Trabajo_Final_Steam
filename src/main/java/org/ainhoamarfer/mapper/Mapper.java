package org.ainhoamarfer.mapper;

import org.ainhoamarfer.modelo.dtos.*;
import org.ainhoamarfer.modelo.entidad.*;

public class Mapper {


    public static UsuarioDTO mapDeUsuario(UsuarioEntidad entidad) {
        if (entidad == null)
            return null;

        return new UsuarioDTO(
                entidad.getId(),
                entidad.getNombreUsuario(),
                entidad.getEmail(),
                entidad.getNombreReal(),
                entidad.getPais(),
                entidad.getFechaNaci(),
                entidad.getFechaRegistro(),
                entidad.getAvatar(),
                entidad.getSaldoCartera(),
                entidad.getEstadoCuenta()
        );
    }
    public static JuegoDTO mapDeJuego(JuegoEntidad entidad) {
        if (entidad == null)
            return null;

        return new JuegoDTO(
                entidad.getId(),
                entidad.getTitulo(),
                entidad.getDescripcion(),
                entidad.getDesarrollador(),
                entidad.getFechaLanzamiento(),
                entidad.getPrecioBase(),
                entidad.getDescuentoActual(),
                entidad.getCategoria(),
                entidad.getIdiomas(),
                entidad.getClasificacionEdad(),
                entidad.getEstado()
        );
    }


    public static ResenaDTO mapDeResena(ResenaEntidad resena) {
        if (resena == null)
            return null;

        return new ResenaDTO(
                resena.getId(),
                resena.getUsuarioId(),
                resena.getJuegoId(),
                resena.isRecomendado(),
                resena.getTexto(),
                resena.getHorasJugadas(),
                resena.getFechaPublicacion(),
                resena.getFechaUltEdicion(),
                resena.getEstado()
        );
    }

    public static BibliotecaDTO mapDeBiblioteca(BibliotecaEntidad biblioteca, UsuarioDTO usuario, JuegoDTO juego) {
        if (biblioteca == null)
            return null;

        return new BibliotecaDTO(
                biblioteca.getId(),
                biblioteca.getUsuarioId(),
                usuario,
                biblioteca.getJuegoId(),
                juego,
                biblioteca.getFechaAdquisicion(),
                biblioteca.getTiempoJuego(),
                biblioteca.getFechaUltimaJugado(),
                biblioteca.isInstalado()
        );
    }

    public static CompraDTO mapDeCompra(CompraEntidad compra, UsuarioDTO usuario, JuegoDTO juego) {
        if (compra == null)
            return null;

        return new CompraDTO(
                compra.getId(),
                compra.getUsuarioId(),
                usuario,
                compra.getJuegoId(),
                juego,
                compra.getFechaCompra(),
                compra.getPrecioBase(),
                compra.getPorcentajeDescuento(),
                compra.getEstadoCompra(),
                compra.getMetodoPago()
        );
    }
}
