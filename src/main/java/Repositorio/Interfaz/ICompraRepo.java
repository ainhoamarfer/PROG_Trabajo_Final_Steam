package Repositorio.Interfaz;

import Modelo.Entidad.CompraEntidad;
import Modelo.Form.CompraForm;

import java.util.List;

public interface ICompraRepo {

    //CRUD

    //create
    CompraEntidad crear(CompraForm form);

    //readAll
    List<CompraEntidad> obtenerTodos();

    //readId
    CompraEntidad obtenerPorId(int id);

    //update
    CompraEntidad actualizar(int id, CompraForm form);

    //delete
    boolean eliminar(int id);

}
