package Repositorio.Interfaz;

import Modelo.Entidad.CompraEntidad;
import Modelo.Form.CompraForm;

import java.util.List;

public interface ICompraRepo {

    //CRUD

    //create
    CompraEntidad create(CompraForm form);

    //readAll
    List<CompraEntidad> readAll();

    //readId
    CompraEntidad readById(int id);

    //readByTitulo
    CompraEntidad readByTitulo(String titulo);

    //update
    CompraEntidad update(int id, CompraForm form);

    //delete
    boolean delete(int id);
}
