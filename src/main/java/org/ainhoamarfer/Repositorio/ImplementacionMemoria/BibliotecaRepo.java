package org.ainhoamarfer.Repositorio.ImplementacionMemoria;

import org.ainhoamarfer.Modelo.Entidad.BibliotecaEntidad;
import org.ainhoamarfer.Modelo.Form.BibliotecaForm;
import org.ainhoamarfer.Repositorio.Interfaz.IBibliotecaRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BibliotecaRepo implements IBibliotecaRepo {

    private static final List<BibliotecaEntidad> bibliotecas = new ArrayList<>();
    private static Long idContador = 1L;

    @Override
    public Optional<BibliotecaEntidad> crear(BibliotecaForm form) {
        Long id = idContador;
        idContador = id + 1L;
        BibliotecaEntidad biblioteca = new BibliotecaEntidad(id, form.getUsuarioId(), form.getJuegoId(), form.getFechaAdquisicion(), form.getTiempoJuego(),
                form.getFechaUltimaJugado(), form.isInstalado());
        bibliotecas.add(biblioteca);

        return Optional.of(biblioteca);
    }

    @Override
    public Optional<BibliotecaEntidad> obtenerPorId(Long id) {
        return bibliotecas.stream()
                .filter(u -> id.equals(u.getId()))
                .findFirst();
    }

    @Override
    public List<BibliotecaEntidad> obtenerTodos() {
        return new ArrayList<>(bibliotecas);
    }

    @Override
    public Optional<BibliotecaEntidad> actualizar(Long id, BibliotecaForm form) {
        Optional<BibliotecaEntidad> bibliotecaOpt = this.obtenerPorId(id);

        if (bibliotecaOpt.isEmpty()) {
            throw new IllegalArgumentException("Biblioteca no encontrada");
        } else {
            BibliotecaEntidad bibliotecaActualizado = new BibliotecaEntidad(id, form.getUsuarioId(), form.getJuegoId(), form.getFechaAdquisicion(), form.getTiempoJuego(),
                    form.getFechaUltimaJugado(), form.isInstalado());
            bibliotecas.removeIf((u) -> id.equals(u.getId()));
            bibliotecas.add(bibliotecaActualizado);
            return Optional.of(bibliotecaActualizado);
        }
    }

    @Override
    public boolean eliminar(Long id) {
        return bibliotecas.removeIf((u) -> id.equals(u.getId()));
    }
}
