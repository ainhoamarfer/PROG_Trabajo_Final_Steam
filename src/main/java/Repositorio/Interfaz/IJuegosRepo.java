package Repositorio.Interfaz;

import Modelo.Entidad.JuegoEntidad;
import Modelo.Form.JuegoForm;

import java.util.List;

public interface IJuegosRepo {

    //CRUD

    //create
    JuegoEntidad crear(JuegoForm form);

    //readAll
    List<JuegoEntidad> obtenerTodos();

    //readId
    JuegoEntidad obtenerPorId(int id);

    //readByTitulo
    JuegoEntidad obtenerPorTitulo(String titulo);

    //update
    JuegoEntidad actualizar(int id, JuegoForm form);

    //delete
    boolean eliminar(int id);
}

