package Mapper;

import Modelo.DTOs.UsuarioDTO;
import Modelo.Entidad.UsuarioEntidad;

public class Mapper {

    public static UsuarioDTO mapDe(UsuarioEntidad entidad) {
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
                entidad.getEstadoCuenta());
    }



}
