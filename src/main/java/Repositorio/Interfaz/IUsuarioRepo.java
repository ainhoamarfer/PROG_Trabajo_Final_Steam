package Repositorio.Interfaz;

import Modelo.Entidad.UsuarioEntidad;
import Modelo.Form.UsuarioForm;

import java.util.Optional;

public interface IUsuarioRepo extends ICrud<UsuarioEntidad, UsuarioForm, Long> {

    public Optional<UsuarioEntidad> obtenerPorNombreUsuario(String nombreUsuario);

}
