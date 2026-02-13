package org.ainhoamarfer.Controlador;

import org.ainhoamarfer.Excepciones.ValidationException;
import org.ainhoamarfer.Mapper.Mapper;
import org.ainhoamarfer.Modelo.DTOs.ErrorDTO;
import org.ainhoamarfer.Modelo.DTOs.UsuarioDTO;
import org.ainhoamarfer.Modelo.Entidad.UsuarioEntidad;
import org.ainhoamarfer.Modelo.Form.ErrorType;
import org.ainhoamarfer.Modelo.Form.UsuarioForm;
import org.ainhoamarfer.Repositorio.Interfaz.IUsuarioRepo;
import org.ainhoamarfer.Vista.SteamVista;

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
    public UsuarioDTO registrarNuevoUsuario(UsuarioForm form) throws ValidationException {
        List<ErrorDTO> errores = form.validar(form);

        usuarioRepo.obtenerPorNombreUsuario(form.getNombreUsuario())
                .ifPresent(u -> errores.add(new ErrorDTO("nombre", ErrorType.DUPLICADO)));

        if (!errores.isEmpty()) {
            throw new ValidationException(errores);
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
    public UsuarioDTO consultarPerfil(UsuarioForm form) throws ValidationException {
        List<ErrorDTO> errores = form.validar(form);

        if (!errores.isEmpty()) {
            throw new ValidationException(errores);
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

}
