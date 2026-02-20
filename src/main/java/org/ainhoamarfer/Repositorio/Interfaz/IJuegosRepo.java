package org.ainhoamarfer.Repositorio.Interfaz;

import org.ainhoamarfer.Modelo.Entidad.JuegoEntidad;
import org.ainhoamarfer.Modelo.Form.JuegoForm;

import java.util.Optional;

public interface IJuegosRepo extends ICrud<JuegoEntidad, JuegoForm, Long> {

    Optional<JuegoEntidad> obtenerPorTitulo(String titulo);

}
