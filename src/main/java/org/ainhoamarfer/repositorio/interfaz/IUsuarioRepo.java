package org.ainhoamarfer.repositorio.interfaz;

import org.ainhoamarfer.modelo.entidad.UsuarioEntidad;
import org.ainhoamarfer.modelo.form.UsuarioForm;

import java.util.Optional;

/**
 * Interfaz de repositorio para la gestión de Usuarios.
 * Define operaciones específicas para acceder y modificar datos de usuarios,
 * incluyendo búsquedas por nombre de usuario y correo electrónico,
 * así como operaciones de gestión del saldo de cartera.
 *
 * Extiende de ICrud<UsuarioEntidad, UsuarioForm, Long> para heredar
 * las operaciones CRUD básicas.
 */
public interface IUsuarioRepo extends ICrud<UsuarioEntidad, UsuarioForm, Long> {

    /**
     * Obtiene un usuario por su nombre de usuario único.
     * El nombre de usuario es un identificador único en el sistema.
     *
     * @param nombreUsuario Nombre de usuario para buscar
     * @return Optional conteniendo el usuario si existe con ese nombre de usuario,
     *         o un Optional vacío si no existe
     */
    Optional<UsuarioEntidad> obtenerPorNombreUsuario(String nombreUsuario);

    /**
     * Resta el saldo especificado de la cartera de un usuario.
     * Típicamente utilizado cuando un usuario realiza una compra.
     *
     * @param idUsuario Identificador único del usuario
     * @param precioJuego Cantidad a restar del saldo de la cartera
     */
    void restarSaldoCartera(Long idUsuario, Double precioJuego);

    /**
     * Suma el saldo especificado a la cartera de un usuario.
     * Típicamente utilizado cuando se reembolsa una compra o se agrega dinero a la cuenta.
     *
     * @param idUsuario Identificador único del usuario
     * @param precioJuego Cantidad a sumar al saldo de la cartera
     */
    void sumarSaldoCartera(Long idUsuario, Double precioJuego);

    /**
     * Obtiene un usuario por su dirección de correo electrónico.
     * La dirección de correo es un identificador único en el sistema.
     *
     * @param email Dirección de correo electrónico para buscar
     * @return Optional conteniendo el usuario si existe con ese email,
     *         o un Optional vacío si no existe
     */
    Optional<UsuarioEntidad> obtenerPorEmail(String email);

}
