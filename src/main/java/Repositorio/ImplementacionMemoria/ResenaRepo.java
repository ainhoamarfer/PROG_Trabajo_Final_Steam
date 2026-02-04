package Repositorio.ImplementacionMemoria;

import Modelo.Entidad.CompraEntidad;
import Modelo.Entidad.ResenaEntidad;
import Modelo.Form.ResenaForm;
import Repositorio.Interfaz.IResenaRepo;

import java.util.ArrayList;
import java.util.List;

public class ResenaRepo implements IResenaRepo {

    private static final List<ResenaEntidad> resenas = new ArrayList<>();
    private static Long idContador = 1L;

    @Override
    public ResenaEntidad crear(ResenaForm form) {
        return null;
    }

    @Override
    public List<ResenaEntidad> obtenerTodos() {
        return List.of();
    }

    @Override
    public ResenaEntidad obtenerPorId(int id) {
        return null;
    }

    @Override
    public ResenaEntidad actualizar(int id, ResenaForm form) {
        return null;
    }

    @Override
    public boolean eliminar(int id) {
        return false;
    }
}
