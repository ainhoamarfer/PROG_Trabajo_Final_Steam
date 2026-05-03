package org.ainhoamarfer.repositorio.implementacion_memoria;

import org.ainhoamarfer.modelo.dtos.JuegoDTO;
import org.ainhoamarfer.modelo.entidad.BibliotecaEntidad;
import org.ainhoamarfer.modelo.form.BibliotecaForm;
import org.ainhoamarfer.repositorio.interfaz.IBibliotecaRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BibliotecaRepo implements IBibliotecaRepo {

    private static final List<BibliotecaEntidad> BIBLIOTECAS = new ArrayList<>();
    private static Long idContador = 1L;



    @Override
    public Optional<BibliotecaEntidad> crear(BibliotecaForm form) {
        Long id = idContador;
        idContador = id + 1L;
        BibliotecaEntidad biblioteca = new BibliotecaEntidad(id, form.getUsuarioId(), form.getJuegoId(), form.getFechaAdquisicion(), form.getTiempoJuego(),
                form.getFechaUltimaJugado());
        BIBLIOTECAS.add(biblioteca);

        return Optional.of(biblioteca);
    }

    @Override
    public Optional<BibliotecaEntidad> obtenerPorId(Long id) {
        return BIBLIOTECAS.stream()
                .filter(u -> id.equals(u.getId()))
                .findFirst();
    }


    public List<BibliotecaEntidad> obtenerPorIdUsuario(Long idUsuario) {
        return BIBLIOTECAS.stream()
                .filter(b -> b.getUsuarioId() == idUsuario)
                .toList();
    }

    @Override
    public Optional<BibliotecaEntidad> obtenerPorIdUsuarioYIdJuego(Long idUsuario, Long idJuego) {
        return BIBLIOTECAS.stream()
                .filter(u -> idUsuario.equals(u.getUsuarioId()) && idJuego.equals(u.getJuegoId()))
                .findFirst();
    }

    @Override
    public List<BibliotecaEntidad> obtenerTodos() {
        return new ArrayList<>(BIBLIOTECAS);
    }

    @Override
    public Optional<BibliotecaEntidad> actualizar(Long id, BibliotecaForm form) {
        Optional<BibliotecaEntidad> bibliotecaOpt = this.obtenerPorId(id);

        if (bibliotecaOpt.isEmpty()) {
            throw new IllegalArgumentException("Biblioteca no encontrada");
        } else {
            BibliotecaEntidad bibliotecaActualizado = new BibliotecaEntidad(id, form.getUsuarioId(), form.getJuegoId(), form.getFechaAdquisicion(), form.getTiempoJuego(),
                    form.getFechaUltimaJugado());
            BIBLIOTECAS.removeIf((u) -> id.equals(u.getId()));
            BIBLIOTECAS.add(bibliotecaActualizado);
            return Optional.of(bibliotecaActualizado);
        }
    }

    @Override
    public boolean eliminar(Long id) {
        return BIBLIOTECAS.removeIf((u) -> id.equals(u.getId()));
    }
}
