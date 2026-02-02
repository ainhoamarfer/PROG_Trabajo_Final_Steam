package Repositorio.ImplementacionMemoria;

import Modelo.Entidad.JuegoEntidad;
import Modelo.Form.JuegoForm;
import Repositorio.Interfaz.IJuegosRepo;

import java.util.List;

public class JuegoRepo implements IJuegosRepo {

    @Override
    public JuegoEntidad create(JuegoForm form) {
        return null;
    }

    @Override
    public List<JuegoEntidad> readAll() {
        return List.of();
    }

    @Override
    public JuegoEntidad readById(int id) {
        return null;
    }

    @Override
    public JuegoEntidad readByTitulo(String titulo) {
        return null;
    }

    @Override
    public JuegoEntidad update(int id, JuegoForm form) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
