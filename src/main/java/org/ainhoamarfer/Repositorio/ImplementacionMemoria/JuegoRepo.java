package org.ainhoamarfer.Repositorio.ImplementacionMemoria;

import org.ainhoamarfer.Modelo.Entidad.JuegoEntidad;
import org.ainhoamarfer.Modelo.Form.JuegoForm;
import org.ainhoamarfer.Repositorio.Interfaz.IJuegosRepo;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JuegoRepo implements IJuegosRepo {

    private static final List<JuegoEntidad> juegos = new ArrayList<>();
    private static Long idContador = 1L;

    @Override
    public Optional<JuegoEntidad> crear(JuegoForm form) {
        Long id = idContador;
        idContador = id + 1L;
        JuegoEntidad juego = new JuegoEntidad(id, form.getTitulo(), form.getDescripcion(), form.getDesarrollador(), form.getFechaLanzamiento(), form.getPrecioBase(),
                form.getDescuentoActual(), form.getCategoria(), form.getIdiomas(), form.getClasificacionEdad(), form.getEstado());
        juegos.add(juego);

        return Optional.of(juego);
    }

    @Override
    public Optional<JuegoEntidad> obtenerPorId(Long id) {
        return juegos.stream()
                .filter(u -> id.equals(u.getId()))
                .findFirst();
    }

    @Override
    public List<JuegoEntidad> obtenerTodos() {
        return new ArrayList<>(juegos);
    }

    @Override
    public Optional<JuegoEntidad> actualizar(Long id, JuegoForm form) {
        Optional<JuegoEntidad> juegoOpt = this.obtenerPorId(id);

        if (juegoOpt.isEmpty()) {
            throw new IllegalArgumentException("Juego no encontrado");
        } else {
            JuegoEntidad juegoActualizado = new JuegoEntidad(id, form.getTitulo(), form.getDescripcion(), form.getDesarrollador(), form.getFechaLanzamiento(), form.getPrecioBase(),
                    form.getDescuentoActual(), form.getCategoria(), form.getIdiomas(), form.getClasificacionEdad(), form.getEstado());
            juegos.removeIf((u) -> id.equals(u.getId()));
            juegos.add(juegoActualizado);
            return Optional.of(juegoActualizado);
        }
    }

    @Override
    public boolean eliminar(Long id) {
        return juegos.removeIf((u) -> id.equals(u.getId()));
    }
}
