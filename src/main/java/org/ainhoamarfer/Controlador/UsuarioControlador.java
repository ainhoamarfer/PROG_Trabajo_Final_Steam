package org.ainhoamarfer.Controlador;

import org.ainhoamarfer.Excepciones.ExcepcionValidacion;
import org.ainhoamarfer.Mapper.Mapper;
import org.ainhoamarfer.Modelo.DTOs.ErrorDTO;
import org.ainhoamarfer.Modelo.DTOs.UsuarioDTO;
import org.ainhoamarfer.Modelo.Entidad.UsuarioEntidad;
import org.ainhoamarfer.Modelo.Form.ErrorType;
import org.ainhoamarfer.Modelo.Form.UsuarioForm;
import org.ainhoamarfer.Repositorio.Interfaz.IUsuarioRepo;
import org.ainhoamarfer.Vista.SteamVista;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioControlador {

    /*
    Registrar nuevo usuario
    Consultar perfil
    Añadir saldo a cartera
    Consultar saldo
     */

    private IUsuarioRepo usuarioRepo;
    private SteamVista vista;
    private Util util;

    public UsuarioControlador(IUsuarioRepo repo, SteamVista vista) {
        this.usuarioRepo = repo;
        this.vista = vista;
    }

    /*
    Registrar nuevo usuario

    Descripción: Crear una nueva cuenta de usuario en la plataforma
    Entrada: Datos del formulario de registro (nombre de usuario, email, contraseña, nombre real, país, fecha de nacimiento)
    Salida: Usuario creado exitosamente o lista de errores de validación
    Validaciones: Aplicar todas las restricciones definidas en la sección de validación de Usuario
     */
    public UsuarioDTO registrarNuevoUsuario(UsuarioForm form) throws ExcepcionValidacion {
        List<ErrorDTO> errores = form.validar(form);

        usuarioRepo.obtenerPorNombreUsuario(form.getNombreUsuario())
                .ifPresent(u -> errores.add(new ErrorDTO("nombre", ErrorType.DUPLICADO)));

        if (!errores.isEmpty()) {
            throw new ExcepcionValidacion(errores);
        }

        Optional<UsuarioEntidad> usuarioOpt = usuarioRepo.crear(form);
        UsuarioEntidad usuario = usuarioOpt.orElse(null);

        return Mapper.mapDe(usuario);
    }

    /*
    Consultar perfil

    Descripción: Mostrar la información de un usuario específico
    Entrada: ID o nombre del usuario a consultar
    Salida: Información del perfil del usuario o mensaje de acceso denegado
    Información mostrada: Nombre de usuario, avatar, país, fecha de registro, biblioteca y
    estadísticas de juego
     */
    public UsuarioDTO consultarPerfil(UsuarioForm form) throws ExcepcionValidacion {
        List<ErrorDTO> errores = form.validar(form);

        if (!errores.isEmpty()) {
            throw new ExcepcionValidacion(errores);
        }

        Optional<UsuarioEntidad> usuarioOpt = usuarioRepo.obtenerPorNombreUsuario(form.getNombreUsuario());
        UsuarioEntidad usuario = usuarioOpt.orElse(null);

        return Mapper.mapDe(usuario);
    }

    /*
    Añadir saldo a cartera

    Descripción: Recargar dinero en la cartera virtual de Steam del usuario
    Entrada: ID del usuario, cantidad a añadir
    Salida: Nuevo saldo de la cartera o mensaje de error
    Validaciones: Cantidad > 0, cuenta activa, rango entre 5.00 y 500.00
     */
    public UsuarioDTO anadirSaldoCartera(Double recarga, long idUsuario) throws ExcepcionValidacion {
        List<ErrorDTO> errores = new ArrayList<>();

        UsuarioEntidad usuario = usuarioValido(idUsuario);

        if(usuario.getEstadoCuenta().estadoBloqueado()){
            errores.add(new ErrorDTO("Estado cuenta", ErrorType.ESTADO_CUENTA));
            throw new ExcepcionValidacion(errores);
        }

        if(!Util.validarRecargaCartera(recarga)){
            errores.add(new ErrorDTO("Dinero recarga", ErrorType.VALOR_NO_VALIDO));
            throw new ExcepcionValidacion(errores);
        }
        return Mapper.mapDe(usuario);
    }

    /*
    Añadir saldo a cartera

    Descripción: Mostrar el saldo disponible en la cartera Steam de un usuario
    Entrada: ID del usuario
    Salida: Saldo actual de la cartera (ejemplo: "45.67 €")
    Validaciones: Usuario debe existir en el sistema
     */
    public Double consultarSaldoCartera(long idUsuario) throws ExcepcionValidacion {

        UsuarioEntidad usuario = usuarioValido(idUsuario);

        return usuario.getSaldoCartera();
    }

    private UsuarioEntidad usuarioValido(long idUsuario) throws ExcepcionValidacion {
        List<ErrorDTO> errores = new ArrayList<>();

        Optional<UsuarioEntidad> usuarioOpt = usuarioRepo.obtenerPorId(idUsuario);
        UsuarioEntidad usuario = usuarioOpt.orElse(null);

        if(usuario == null){
            errores.add(new ErrorDTO("Usuario", ErrorType.USUARIO_INVALIDO));
            throw new ExcepcionValidacion(errores);
        }else return usuario;
    }

}
