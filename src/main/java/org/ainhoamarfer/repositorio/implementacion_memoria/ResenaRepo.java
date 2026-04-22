package org.ainhoamarfer.repositorio.implementacion_memoria;

import org.ainhoamarfer.modelo.entidad.CompraEntidad;
import org.ainhoamarfer.modelo.entidad.ResenaEntidad;
import org.ainhoamarfer.modelo.enums.ResenaEstado;
import org.ainhoamarfer.modelo.form.ResenaForm;
import org.ainhoamarfer.repositorio.interfaz.IResenaRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ResenaRepo implements IResenaRepo {

    private static final List<ResenaEntidad> RESENAS = new ArrayList<>();
    private static Long idContador = 1L;



    @Override
    public Optional<ResenaEntidad> crear(ResenaForm form) {
        Long id = idContador;
        idContador = id + 1L;
        ResenaEntidad resena = new ResenaEntidad(id, form.getUsuarioId(), form.getJuegoId(), form.isRecomendado(), form.getTexto(), form.getHorasJugadas(),
                form.getFechaPublicacion(), form.getFechaUltEdicion(), form.getEstado());
        RESENAS.add(resena);

        return Optional.of(resena);
    }

    @Override
    public Optional<ResenaEntidad> obtenerPorId(Long id) {
        return RESENAS.stream()
                .filter(u -> id.equals(u.getId()))
                .findFirst();
    }

    @Override
    public List<ResenaEntidad> obtenerTodos() {
        return new ArrayList<>(RESENAS);
    }

    @Override
    public Optional<ResenaEntidad> actualizar(Long id, ResenaForm form) {
        Optional<ResenaEntidad> resenaOpt = this.obtenerPorId(id);

        if (resenaOpt.isEmpty()) {
            throw new IllegalArgumentException("Reseña no encontrada");
        } else {
            ResenaEntidad resenaActualizada = new ResenaEntidad(id, form.getUsuarioId(), form.getJuegoId(), form.isRecomendado(), form.getTexto(), form.getHorasJugadas(),
                    form.getFechaPublicacion(), form.getFechaUltEdicion(), form.getEstado());
            RESENAS.removeIf((u) -> id.equals(u.getId()));
            RESENAS.add(resenaActualizada);
            return Optional.of(resenaActualizada);
        }
    }

    @Override
    public boolean eliminar(Long id) {
        return RESENAS.removeIf((u) -> id.equals(u.getId()));
    }

    @Override
    public Optional<ResenaEntidad> obtenerPorIdUsuarioYIdJuego(Long idUsuario, Long idJuego) {
        return RESENAS.stream()
                .filter(u -> idUsuario.equals(u.getUsuarioId()) && idJuego.equals(u.getJuegoId()))
                .findFirst();
    }

    @Override
    public Optional<ResenaEntidad> obtenerPorIdUsuario(Long idUsuario) {
        return RESENAS.stream()
                .filter(u -> idUsuario.equals(u.getUsuarioId()))
                .findFirst();
    }

    @Override
    public Optional<ResenaEntidad> actualizarEstadoResena(Long id, ResenaEstado estado) {
        Optional<ResenaEntidad> resenaOpt = this.obtenerPorId(id);

        if (resenaOpt.isEmpty()) {
            throw new IllegalArgumentException("Reseña no encontrada");
        } else {
            ResenaEntidad r = resenaOpt.get();
            ResenaEntidad resenaActualizada = new ResenaEntidad(id, r.getUsuarioId(), r.getJuegoId(), r.isRecomendado(), r.getTexto(), r.getHorasJugadas(),
                    r.getFechaPublicacion(), r.getFechaUltEdicion(), estado);
            RESENAS.removeIf((u) -> id.equals(u.getId()));
            RESENAS.add(resenaActualizada);
            return Optional.of(resenaActualizada);
        }
    }
}
