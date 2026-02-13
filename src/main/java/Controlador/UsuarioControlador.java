package Controlador;

import Excepciones.ValidationException;
import Mapper.Mapper;
import Modelo.DTOs.ErrorDTO;
import Modelo.DTOs.UsuarioDTO;
import Modelo.Entidad.UsuarioEntidad;
import Modelo.Form.ErrorType;
import Modelo.Form.UsuarioForm;
import Repositorio.Interfaz.IUsuarioRepo;
import Vista.SteamVista;

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
        List<ErrorDTO> errores = new ArrayList<>();

        usuarioRepo.obtenerPorNombreUsuario(form.getNombreUsuario())
                .ifPresent(u -> errores.add(new ErrorDTO("nombre", ErrorType.DUPLICADO)));

        if (!errores.isEmpty()) {
            throw new ValidationException(errores);
        }

        Optional<UsuarioEntidad> usuarioOpt = usuarioRepo.crear(form);
        UsuarioEntidad usuario = usuarioOpt.orElse(null);

        return Mapper.mapDe(usuario);
    }
}
