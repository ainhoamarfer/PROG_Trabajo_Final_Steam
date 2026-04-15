package org.ainhoamarfer.mapper;

import org.ainhoamarfer.modelo.dtos.*;
import org.ainhoamarfer.modelo.entidad.*;
import org.ainhoamarfer.repositorio.interfaz.IBibliotecaRepo;
import org.ainhoamarfer.repositorio.interfaz.ICompraRepo;
import org.ainhoamarfer.repositorio.interfaz.IJuegosRepo;
import org.ainhoamarfer.repositorio.interfaz.IUsuarioRepo;

public class Mapper {

    private static ICompraRepo compraRepo;
    private static IJuegosRepo juegoRepo;
    private static IUsuarioRepo usuarioRepo;
    private static IBibliotecaRepo bibliotecaRepo;

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

    public static BibliotecaDTO mapDeBiblioteca(BibliotecaEntidad biblioteca) {
        if (biblioteca == null)
            return null;

        UsuarioEntidad usuario = usuarioRepo.obtenerPorId(biblioteca.getUsuarioId()).orElse(null);
        JuegoEntidad juego = juegoRepo.obtenerPorId(biblioteca.getJuegoId()).orElse(null);

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

    public static CompraDTO mapDeCompra(CompraEntidad compra) {
        if (compra == null)
            return null;

        return new CompraDTO(
                compra.getId(),
                compra.getUsuarioId(),
                usuarioRepo.obtenerPorId(compra.getUsuarioId()),
                compra.getJuegoId(),
                juegoRepo.obtenerPorId(compra.getJuegoId()),
                compra.getFechaCompra(),
                compra.getPrecioBase(),
                compra.getDescuento(),
                compra.getEstadoCompra(),
                compra.getMetodoPago()
        );
    }
}
