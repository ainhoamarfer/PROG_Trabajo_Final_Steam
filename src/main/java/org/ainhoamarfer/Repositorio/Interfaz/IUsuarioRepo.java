package org.ainhoamarfer.Repositorio.Interfaz;

import org.ainhoamarfer.Modelo.Entidad.UsuarioEntidad;
import org.ainhoamarfer.Modelo.Form.UsuarioForm;

import java.util.Optional;

public interface IUsuarioRepo extends ICrud<UsuarioEntidad, UsuarioForm, Long> {

    public Optional<UsuarioEntidad> obtenerPorNombreUsuario(String nombreUsuario);

}
