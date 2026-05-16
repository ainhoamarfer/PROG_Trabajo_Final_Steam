package org.ainhoamarfer.repositorio.interfaz;

import org.ainhoamarfer.modelo.entidad.JuegoEntidad;
import org.ainhoamarfer.modelo.form.JuegoForm;

import java.util.Optional;

/**
 * Interfaz de repositorio para la gestión de Juegos.
 * Define operaciones específicas para acceder y modificar datos de juegos,
 * incluyendo búsquedas por título.
 *
 * Extiende de ICrud<JuegoEntidad, JuegoForm, Long> para heredar
 * las operaciones CRUD básicas.
 */
public interface IJuegosRepo extends ICrud<JuegoEntidad, JuegoForm, Long> {

    /**
     * Obtiene un juego por su título único.
     * El título es un identificador único en el sistema.
     *
     * @param titulo Título del juego a buscar
     * @return Optional conteniendo el juego si existe un juego con ese título,
     *         o un Optional vacío si no existe
     */
    Optional<JuegoEntidad> obtenerPorTitulo(String titulo);

}
