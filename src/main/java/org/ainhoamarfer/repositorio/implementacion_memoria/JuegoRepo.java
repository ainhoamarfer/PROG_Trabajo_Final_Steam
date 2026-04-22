package org.ainhoamarfer.repositorio.implementacion_memoria;

import org.ainhoamarfer.modelo.entidad.JuegoEntidad;
import org.ainhoamarfer.modelo.form.JuegoForm;
import org.ainhoamarfer.repositorio.interfaz.IJuegosRepo;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JuegoRepo implements IJuegosRepo {

    private static final List<JuegoEntidad> JUEGOS = new ArrayList<>();
    private static Long idContador = 1L;

    @Override
    public Optional<JuegoEntidad> crear(JuegoForm form) {
        Long id = idContador;
        idContador = id + 1L;
        JuegoEntidad juego = new JuegoEntidad(id, form.getTitulo(), form.getDescripcion(), form.getDesarrollador(), form.getFechaLanzamiento(), form.getPrecioBase(),
                form.getDescuentoActual(), form.getCategoria(), form.getIdiomas(), form.getClasificacionEdad());
        JUEGOS.add(juego);

        return Optional.of(juego);
    }

    @Override
    public Optional<JuegoEntidad> obtenerPorId(Long id) {
        return JUEGOS.stream()
                .filter(u -> id.equals(u.getId()))
                .findFirst();
    }

    public Optional<JuegoEntidad> obtenerPorTitulo(String titulo) {
        return JUEGOS.stream().filter(u -> titulo.equals(u.getTitulo())).findFirst();
    }

    @Override
    public List<JuegoEntidad> obtenerTodos() {
        return new ArrayList<>(JUEGOS);
    }

    @Override
    public Optional<JuegoEntidad> actualizar(Long id, JuegoForm form) {
        Optional<JuegoEntidad> juegoOpt = this.obtenerPorId(id);

        if (juegoOpt.isEmpty()) {
            throw new IllegalArgumentException("Juego no encontrado");
        } else {
            JuegoEntidad juegoActualizado = new JuegoEntidad(id, form.getTitulo(), form.getDescripcion(), form.getDesarrollador(), form.getFechaLanzamiento(), form.getPrecioBase(),
                    form.getDescuentoActual(), form.getCategoria(), form.getIdiomas(), form.getClasificacionEdad());
            JUEGOS.removeIf((u) -> id.equals(u.getId()));
            JUEGOS.add(juegoActualizado);
            return Optional.of(juegoActualizado);
        }
    }

    @Override
    public boolean eliminar(Long id) {
        return JUEGOS.removeIf((u) -> id.equals(u.getId()));
    }
}
