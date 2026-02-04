package Repositorio.ImplementacionMemoria;

import Modelo.Entidad.CompraEntidad;
import Modelo.Entidad.JuegoEntidad;
import Modelo.Form.JuegoForm;
import Repositorio.Interfaz.IJuegosRepo;

import java.util.ArrayList;
import java.util.List;

public class JuegoRepo implements IJuegosRepo {

    private static final List<JuegoEntidad> juegos = new ArrayList<>();
    private static Long idContador = 1L;

    @Override
    public JuegoEntidad crear(JuegoForm form) {
        return null;
    }

    @Override
    public List<JuegoEntidad> obtenerTodos() {
        return List.of();
    }

    @Override
    public JuegoEntidad obtenerPorId(int id) {
        return null;
    }

    @Override
    public JuegoEntidad obtenerPorTitulo(String titulo) {
        return null;
    }

    @Override
    public JuegoEntidad actualizar(int id, JuegoForm form) {
        return null;
    }

    @Override
    public boolean eliminar(int id) {
        return false;
    }
}
