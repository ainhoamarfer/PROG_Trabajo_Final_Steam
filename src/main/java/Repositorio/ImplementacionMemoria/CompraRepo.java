package Repositorio.ImplementacionMemoria;

import Modelo.Entidad.CompraEntidad;
import Modelo.Form.CompraForm;
import Repositorio.Interfaz.ICompraRepo;

import java.util.ArrayList;
import java.util.List;

public class CompraRepo implements ICompraRepo {

    private static final List<CompraEntidad> compras = new ArrayList<>();
    private static Long idContador = 1L;

    @Override
    public CompraEntidad crear(CompraForm form) {
        return null;
    }

    @Override
    public List<CompraEntidad> obtenerTodos() {
        return List.of();
    }

    @Override
    public CompraEntidad obtenerPorId(int id) {
        return null;
    }

    @Override
    public CompraEntidad actualizar(int id, CompraForm form) {
        return null;
    }

    @Override
    public boolean eliminar(int id) {
        return false;
    }
}
