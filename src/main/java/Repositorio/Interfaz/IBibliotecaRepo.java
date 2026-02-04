package Repositorio.Interfaz;

import Modelo.Entidad.BibliotecaEntidad;
import Modelo.Form.BibliotecaForm;

import java.util.List;

public interface IBibliotecaRepo {

    //CRUD

    //create
    BibliotecaEntidad crear(BibliotecaForm form);

    //readAll
    List<BibliotecaEntidad> obtenerTodos();

    //readId
    BibliotecaEntidad obtenerPorId(int id);

    //readByTitulo
    BibliotecaEntidad obtenerPorTitulo(String titulo);

    //update
    BibliotecaEntidad actualizar(int id, BibliotecaForm form);

    //delete
    boolean eliminar(int id);
}
