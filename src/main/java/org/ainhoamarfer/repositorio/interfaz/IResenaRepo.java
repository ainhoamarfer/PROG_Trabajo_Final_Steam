package org.ainhoamarfer.repositorio.interfaz;

import org.ainhoamarfer.modelo.entidad.CompraEntidad;
import org.ainhoamarfer.modelo.entidad.ResenaEntidad;
import org.ainhoamarfer.modelo.enums.ResenaEstado;
import org.ainhoamarfer.modelo.form.ResenaForm;

import java.util.Optional;

/**
 * Interfaz de repositorio para la gestión de Reseñas.
 * Define operaciones específicas para acceder y modificar datos de reseñas,
 * incluyendo búsquedas por usuario y juego, además de la actualización de estados.
 *
 * Extiende de ICrud<ResenaEntidad, ResenaForm, Long> para heredar
 * las operaciones CRUD básicas.
 */
public interface IResenaRepo extends ICrud<ResenaEntidad, ResenaForm, Long> {

    /**
     * Obtiene la reseña de un usuario para un juego específico.
     * Esta combinación de usuario y juego debe ser única en el sistema.
     *
     * @param idUsuario Identificador único del usuario que realizó la reseña
     * @param idJuego Identificador único del juego reseñado
     * @return Optional conteniendo la reseña si existe para esa combinación de usuario y juego,
     *         o un Optional vacío si no existe
     */
    Optional<ResenaEntidad> obtenerPorIdUsuarioYIdJuego(Long idUsuario, Long idJuego);

    /**
     * Obtiene la reseña más reciente de un usuario específico.
     *
     * @param idUsuario Identificador único del usuario
     * @return Optional conteniendo la última reseña realizada por el usuario,
     *         o un Optional vacío si el usuario no tiene reseñas
     */
    Optional<ResenaEntidad> obtenerPorIdUsuario(Long idUsuario);

    /**
     * Actualiza el estado de una reseña específica.
     * Los estados posibles son definidos en el enum ResenaEstado.
     *
     * @param id Identificador único de la reseña a actualizar
     * @param estado El nuevo estado de la reseña
     */
    void actualizarEstadoResena(Long id, ResenaEstado estado);

}
