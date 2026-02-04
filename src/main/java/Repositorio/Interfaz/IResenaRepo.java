package Repositorio.Interfaz;

import Modelo.Entidad.ResenaEntidad;
import Modelo.Form.ResenaForm;
import java.util.List;

public interface IResenaRepo {

    //CRUD

    //create
    ResenaEntidad crear(ResenaForm form);

    //readAll
    List<ResenaEntidad> obtenerTodos();

    //readId
    ResenaEntidad obtenerPorId(int id);

    //update
    ResenaEntidad actualizar(int id, ResenaForm form);

    //delete
    boolean eliminar(int id);
}
