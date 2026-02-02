package Repositorio.Interfaz;

import Modelo.Entidad.JuegoEntidad;
import Modelo.Form.JuegoForm;

import java.util.List;

public interface IJuegosRepo {

    //CRUD

    //create
    JuegoEntidad create(JuegoForm form);

    //readAll
    List<JuegoEntidad> readAll();

    //readId
    JuegoEntidad readById(int id);

    //readByTitulo
    JuegoEntidad readByTitulo(String titulo);

    //update
    JuegoEntidad update(int id, JuegoForm form);

    //delete
    boolean delete(int id);
}
