package org.ainhoamarfer.repositorio.interfaz;

import org.ainhoamarfer.modelo.entidad.JuegoEntidad;
import org.ainhoamarfer.modelo.form.JuegoForm;

import java.util.Optional;

public interface IJuegosRepo extends ICrud<JuegoEntidad, JuegoForm, Long> {

    Optional<JuegoEntidad> obtenerPorTitulo(String titulo);

}
