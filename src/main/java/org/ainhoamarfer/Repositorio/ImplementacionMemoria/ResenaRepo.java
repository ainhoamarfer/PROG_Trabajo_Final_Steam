package org.ainhoamarfer.Repositorio.ImplementacionMemoria;

import org.ainhoamarfer.Modelo.Entidad.ResenaEntidad;
import org.ainhoamarfer.Modelo.Form.ResenaForm;
import org.ainhoamarfer.Repositorio.Interfaz.IResenaRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ResenaRepo implements IResenaRepo {

    private static final List<ResenaEntidad> resenas = new ArrayList<>();
    private static Long idContador = 1L;

    @Override
    public Optional<ResenaEntidad> crear(ResenaForm form) {
        Long id = idContador;
        idContador = id + 1L;
        ResenaEntidad resena = new ResenaEntidad(id, form.getUsuarioId(), form.getJuegoId(), form.isRecomendado(), form.getTexto(), form.getHorasJugadas(),
                form.getFechaPublicacion(), form.getFechaUltEdicion());
        resenas.add(resena);

        return Optional.of(resena);
    }

    @Override
    public Optional<ResenaEntidad> obtenerPorId(Long id) {
        return resenas.stream()
                .filter(u -> id.equals(u.getId()))
                .findFirst();
    }

    @Override
    public List<ResenaEntidad> obtenerTodos() {
        return new ArrayList<>(resenas);
    }

    @Override
    public Optional<ResenaEntidad> actualizar(Long id, ResenaForm form) {
        Optional<ResenaEntidad> resenaOpt = this.obtenerPorId(id);

        if (resenaOpt.isEmpty()) {
            throw new IllegalArgumentException("Reseña no encontrada");
        } else {
            ResenaEntidad resenaActualizada = new ResenaEntidad(id, form.getUsuarioId(), form.getJuegoId(), form.isRecomendado(), form.getTexto(), form.getHorasJugadas(),
                    form.getFechaPublicacion(), form.getFechaUltEdicion());
            resenas.removeIf((u) -> id.equals(u.getId()));
            resenas.add(resenaActualizada);
            return Optional.of(resenaActualizada);
        }
    }

    @Override
    public boolean eliminar(Long id) {
        return resenas.removeIf((u) -> id.equals(u.getId()));
    }
}
