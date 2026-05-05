package org.ainhoamarfer.controlador;

import org.ainhoamarfer.excepciones.ExcepcionValidacion;
import org.ainhoamarfer.modelo.dtos.ErrorDTO;
import org.ainhoamarfer.modelo.entidad.UsuarioEntidad;
import org.ainhoamarfer.modelo.enums.ErrorType;
import org.ainhoamarfer.repositorio.interfaz.IUsuarioRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Util {
    public static final double MIN_RECARGA = 5.00;
    public static final double MAX_RECARGA = 500.00;

    private IUsuarioRepo usuarioRepo;
    //mappers: public static
    //validaciones generales

    public static boolean validarRecargaCartera(Double numero) {
        //Validaciones: Cantidad > 0, rango entre 5.00 y 500.00
        if(numero == null || numero < MIN_RECARGA || numero > MAX_RECARGA){
            return  false;
        }else return true;
    }

    /**
     * Obtiene la entidad de usuario por su id y valida que exista.
     *
     * @param idUsuario identificador del usuario a buscar
     * @return UsuarioEntidad encontrado
     * @throws ExcepcionValidacion si no se encuentra el usuario
     */
    private UsuarioEntidad comprobarUsuarioValidoPorId(long idUsuario) throws ExcepcionValidacion {
        List<ErrorDTO> errores = new ArrayList<>();

        Optional<UsuarioEntidad> usuarioOpt = usuarioRepo.obtenerPorId(idUsuario);
        UsuarioEntidad usuario = usuarioOpt.orElse(null);

        if(usuario == null){
            errores.add(new ErrorDTO("Usuario no existente", ErrorType.USUARIO_INVALIDO));
            throw new ExcepcionValidacion(errores);
        }else return usuario;
    }




}
