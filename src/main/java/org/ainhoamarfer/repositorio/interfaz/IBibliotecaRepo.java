package org.ainhoamarfer.repositorio.interfaz;

import org.ainhoamarfer.modelo.dtos.JuegoDTO;
import org.ainhoamarfer.modelo.entidad.BibliotecaEntidad;
import org.ainhoamarfer.modelo.entidad.ResenaEntidad;
import org.ainhoamarfer.modelo.form.BibliotecaForm;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz de repositorio para la gestión de la Biblioteca de usuarios.
 * Define operaciones específicas de negocio para la biblioteca personal de cada usuario,
 * incluyendo consultas para obtener juegos asociados a un usuario.
 *
 * Extiende de ICrud<BibliotecaEntidad, BibliotecaForm, Long> para heredar
 * las operaciones CRUD básicas.
 */
public interface IBibliotecaRepo extends ICrud<BibliotecaEntidad, BibliotecaForm, Long> {

    /**
     * Obtiene todos los registros de biblioteca asociados a un usuario específico.
     *
     * @param idUsuario Identificador único del usuario
     * @return Lista de entidades de biblioteca del usuario especificado.
     *         Retorna una lista vacía si no hay juegos en la biblioteca del usuario
     */
    List<BibliotecaEntidad> obtenerPorIdUsuario(Long idUsuario);

    /**
     * Obtiene un registro específico de biblioteca para un usuario y un juego determinado.
     *
     * @param idUsuario Identificador único del usuario
     * @param idJuego Identificador único del juego
     * @return Optional conteniendo la entidad de biblioteca si existe una asociación
     *         entre el usuario y el juego, o un Optional vacío si no existe
     */
    Optional<BibliotecaEntidad> obtenerPorIdUsuarioYIdJuego(Long idUsuario, Long idJuego);
}
