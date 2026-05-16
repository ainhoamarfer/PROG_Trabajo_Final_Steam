package org.ainhoamarfer.repositorio.interfaz;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz genérica CRUD (Create, Read, Update, Delete) que define las operaciones
 * básicas para la gestión de entidades en la base de datos.
 *
 * @param <E> Tipo de la entidad (Entity)
 * @param <F> Tipo del formulario (Form) utilizado para crear/actualizar
 * @param <ID> Tipo del identificador único
 */
public interface ICrud<E, F, ID> {

    /**
     * Crea una nueva entidad en la base de datos a partir de un formulario.
     *
     * @param form Formulario con los datos de la entidad a crear
     * @return Optional conteniendo la entidad creada si la operación fue exitosa,
     *         o un Optional vacío si la operación falló
     */
    Optional<E> crear(F form);

    /**
     * Obtiene una entidad de la base de datos por su identificador único.
     *
     * @param id Identificador único de la entidad
     * @return Optional conteniendo la entidad encontrada, o un Optional vacío
     *         si no existe una entidad con el ID especificado
     */
    Optional<E> obtenerPorId(ID id);

    /**
     * Obtiene todas las entidades de la base de datos.
     *
     * @return Lista con todas las entidades. Retorna una lista vacía si no hay
     *         entidades en la base de datos
     */
    List<E> obtenerTodos();

    /**
     * Actualiza una entidad existente en la base de datos con los datos de un formulario.
     *
     * @param id ID de la entidad a actualizar
     * @param form Formulario con los nuevos datos
     * @return Optional conteniendo la entidad actualizada si la operación fue exitosa,
     *         o un Optional vacío si no existe la entidad o la actualización falló
     */
    Optional<E> actualizar(ID id, F form);

    /**
     * Elimina una entidad de la base de datos por su identificador único.
     *
     * @param id Identificador único de la entidad a eliminar
     * @return true si la entidad fue eliminada exitosamente, false si la entidad
     *         no existe o la eliminación falló
     */
    boolean eliminar(ID id);
}
