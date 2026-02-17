package org.ainhoamarfer.Controlador;

import org.ainhoamarfer.Excepciones.ExcepcionValidacion;
import org.ainhoamarfer.Mapper.Mapper;
import org.ainhoamarfer.Modelo.DTOs.ErrorDTO;
import org.ainhoamarfer.Modelo.DTOs.UsuarioDTO;
import org.ainhoamarfer.Modelo.Entidad.UsuarioEntidad;
import org.ainhoamarfer.Modelo.Enums.ErrorType;
import org.ainhoamarfer.Modelo.Form.UsuarioForm;
import org.ainhoamarfer.Repositorio.Interfaz.IUsuarioRepo;
import org.ainhoamarfer.Vista.SteamVista;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioControlador {
    
    private IUsuarioRepo usuarioRepo;
    //private SteamVista vista;
    private Util util;

    public UsuarioControlador(IUsuarioRepo repo) {
        this.usuarioRepo = repo;
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * Crear una nueva cuenta de usuario en la plataforma
     *
     * @param form formulario con los datos del nuevo usuario
     * @return UsuarioDTO mapeado desde la entidad creada (puede ser {@code null}
     * si la entidad no fue creada por el repositorio)
     * @throws ExcepcionValidacion cuando el formulario no es válido o existen errores
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

    /**
     * Consulta el perfil de un usuario.
     *
     * @param idUsuario identificador del usuario
     * @return UsuarioDTO con la información del usuario (Nombre de usuario, avatar, país, fecha de registro, biblioteca y
     *     estadísticas de juego)
     * @throws ExcepcionValidacion cuando el formulario no pasa las validaciones
     */
    public UsuarioDTO consultarPerfil(long idUsuario) throws ExcepcionValidacion {
        List<ErrorDTO> errores = new ArrayList<>();

        Optional<UsuarioEntidad> usuarioOpt = usuarioRepo.obtenerPorId(idUsuario);
        UsuarioEntidad usuario = usuarioOpt.orElse(null);
        UsuarioEntidad usuarioValido = usuarioValido(idUsuario);

        return Mapper.mapDe(usuarioValido);
    }

    /**
     * Añade saldo a la cartera de un usuario identificado por su id.
     * <p>
     * Comprueba que el usuario exista, que la cuenta no esté bloqueada y que la
     * cantidad de recarga sea válida según las reglas del sistema.
     *
     * @param recarga cantidad a añadir
     * @param idUsuario identificador del usuario que recibirá la recarga
     * @return Double Nuevo saldo de la cartera o mensaje de error
     * @throws ExcepcionValidacion cuando el usuario no existe, la cuenta está bloqueada o la recarga no es válida
     */
    public Double anadirSaldoCartera(Double recarga, long idUsuario) throws ExcepcionValidacion {
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

        double nuevoSaldo = usuario.getSaldoCartera() + recarga;

        UsuarioForm form = new UsuarioForm(usuario.getNombreUsuario(), usuario.getEmail(), usuario.getContrasena(), usuario.getNombreReal(),
                usuario.getPais(), usuario.getFechaNaci(), usuario.getFechaRegistro(), usuario.getAvatar(), nuevoSaldo, usuario.getEstadoCuenta());

        Optional<UsuarioEntidad> usuarioSaldoActualizado = usuarioRepo.actualizar(idUsuario,form);

        return nuevoSaldo;
    }

    /**
     * Consulta el saldo disponible en la cartera de un usuario.
     *
     * @param idUsuario identificador del usuario a consultar
     * @return saldo actual de la cartera como Double
     * @throws ExcepcionValidacion si el usuario no existe
     */
    public Double consultarSaldoCartera(long idUsuario) throws ExcepcionValidacion {

        UsuarioEntidad usuario = usuarioValido(idUsuario);

        return usuario.getSaldoCartera();
    }

    /**
     * Obtiene la entidad de usuario por su id y valida que exista.
     *
     * @param idUsuario identificador del usuario a buscar
     * @return UsuarioEntidad encontrado
     * @throws ExcepcionValidacion si no se encuentra el usuario
     */
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
