package Repositorio.Interfaz;

import Modelo.Entidad.UsuarioEntidad;
import Modelo.Form.UsuarioForm;

import java.util.List;

public interface IUsuarioRepo {

    //CRUD

    //create
    UsuarioEntidad crear(UsuarioForm form);

    //readAll
    List<UsuarioEntidad> obtenerTodos();

    //readId
    UsuarioEntidad obtenerPorId(int id);

    //update
    UsuarioEntidad actualizar(int id, UsuarioForm form);

    //delete
    boolean eliminar(int id);
}
