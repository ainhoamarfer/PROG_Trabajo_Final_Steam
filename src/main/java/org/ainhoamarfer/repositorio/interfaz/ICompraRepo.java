package org.ainhoamarfer.repositorio.interfaz;

import org.ainhoamarfer.modelo.entidad.CompraEntidad;
import org.ainhoamarfer.modelo.enums.CompraEstadoEnum;
import org.ainhoamarfer.modelo.form.CompraForm;

import java.util.Optional;

/**
 * Interfaz de repositorio para la gestión de Compras.
 * Define operaciones específicas para acceder y modificar datos de compras,
 * incluyendo búsquedas por usuario y actualización de estados.
 *
 * Extiende de ICrud<CompraEntidad, CompraForm, Long> para heredar
 * las operaciones CRUD básicas.
 */
public interface ICompraRepo extends ICrud<CompraEntidad, CompraForm, Long> {

    /**
     * Obtiene la compra más reciente asociada a un usuario específico.
     *
     * @param id Identificador único del usuario
     * @return Optional conteniendo la compra del usuario si existe,
     *         o un Optional vacío si el usuario no tiene compras
     */
    Optional<CompraEntidad> obtenerPorIdUsuario(Long id);

    /**
     * Obtiene una compra específica de un usuario determinado.
     * Verifica que la compra pertenezca al usuario especificado para validar el acceso.
     *
     * @param idUsuario Identificador único del usuario propietario de la compra
     * @param idCompra Identificador único de la compra
     * @return Optional conteniendo la compra si existe y pertenece al usuario,
     *         o un Optional vacío si no existe o no pertenece al usuario
     */
    Optional<CompraEntidad> obtenerPorIdUsuarioYIdCompra(Long idUsuario, Long idCompra);

    /**
     * Actualiza el estado de una compra específica.
     * Los estados posibles son definidos en el enum CompraEstadoEnum.
     *
     * @param idCompra Identificador único de la compra a actualizar
     * @param estadoCompra El nuevo estado de la compra
     */
    void actualizarEstadoCompra(Long idCompra, CompraEstadoEnum estadoCompra);

}
