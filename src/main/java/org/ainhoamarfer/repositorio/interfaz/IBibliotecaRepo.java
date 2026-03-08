package org.ainhoamarfer.repositorio.interfaz;

import org.ainhoamarfer.modelo.dtos.JuegoDTO;
import org.ainhoamarfer.modelo.entidad.BibliotecaEntidad;
import org.ainhoamarfer.modelo.form.BibliotecaForm;

import java.util.List;
import java.util.Optional;

public interface IBibliotecaRepo extends ICrud<BibliotecaEntidad, BibliotecaForm, Long> {

    Optional<BibliotecaEntidad> obtenerPorIdUsuario(Long idUsario);

    Optional<List<JuegoDTO>> anadirJuegoBiblioteca(Long idUsuario, Long idJuego);

     Optional<List<JuegoDTO>> eliminarJuegoBiblioteca(Long idUsuario, Long idJuego);



}
