package org.ainhoamarfer.mapper;

import org.ainhoamarfer.modelo.dtos.JuegoDTO;
import org.ainhoamarfer.modelo.dtos.ResenaDTO;
import org.ainhoamarfer.modelo.dtos.UsuarioDTO;
import org.ainhoamarfer.modelo.entidad.JuegoEntidad;
import org.ainhoamarfer.modelo.entidad.ResenaEntidad;
import org.ainhoamarfer.modelo.entidad.UsuarioEntidad;

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
}
