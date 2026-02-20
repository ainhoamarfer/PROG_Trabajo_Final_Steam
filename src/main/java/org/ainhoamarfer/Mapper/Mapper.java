package org.ainhoamarfer.Mapper;

import org.ainhoamarfer.Modelo.DTOs.JuegoDTO;
import org.ainhoamarfer.Modelo.DTOs.UsuarioDTO;
import org.ainhoamarfer.Modelo.Entidad.JuegoEntidad;
import org.ainhoamarfer.Modelo.Entidad.UsuarioEntidad;

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



}
