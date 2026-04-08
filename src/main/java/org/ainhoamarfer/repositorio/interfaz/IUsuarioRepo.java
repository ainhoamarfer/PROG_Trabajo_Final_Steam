package org.ainhoamarfer.repositorio.interfaz;

import org.ainhoamarfer.modelo.entidad.UsuarioEntidad;
import org.ainhoamarfer.modelo.form.UsuarioForm;

import java.util.Optional;

public interface IUsuarioRepo extends ICrud<UsuarioEntidad, UsuarioForm, Long> {

    public Optional<UsuarioEntidad> obtenerPorNombreUsuario(String nombreUsuario);

    void actualizarSaldoCartera(Long idUsuario, Double precioJuego);

}
