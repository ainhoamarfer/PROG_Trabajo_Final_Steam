package Repositorio.ImplementacionMemoria;

import Modelo.Entidad.CompraEntidad;
import Modelo.Entidad.UsuarioEntidad;
import Modelo.Form.UsuarioForm;
import Repositorio.Interfaz.IUsuarioRepo;

import java.util.ArrayList;
import java.util.List;

public class UsuarioRepo implements IUsuarioRepo {

    private static final List<UsuarioEntidad> usuarios = new ArrayList<>();
    private static Long idContador = 1L;

    @Override
    public UsuarioEntidad crear(UsuarioForm form) {
        return null;
    }

    @Override
    public List<UsuarioEntidad> obtenerTodos() {
        return List.of();
    }

    @Override
    public UsuarioEntidad obtenerPorId(int id) {
        return null;
    }

    @Override
    public UsuarioEntidad actualizar(int id, UsuarioForm form) {
        return null;
    }

    @Override
    public boolean eliminar(int id) {
        return false;
    }
}
