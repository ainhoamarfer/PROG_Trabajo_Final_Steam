package Repositorio.ImplementacionMemoria;

import Modelo.Entidad.CompraEntidad;
import Modelo.Form.CompraForm;
import Repositorio.Interfaz.ICompraRepo;

import java.util.List;

public class CompraRepo implements ICompraRepo {
    @Override
    public CompraEntidad create(CompraForm form) {
        return null;
    }

    @Override
    public List<CompraEntidad> readAll() {
        return List.of();
    }

    @Override
    public CompraEntidad readById(int id) {
        return null;
    }

    @Override
    public CompraEntidad readByTitulo(String titulo) {
        return null;
    }

    @Override
    public CompraEntidad update(int id, CompraForm form) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
