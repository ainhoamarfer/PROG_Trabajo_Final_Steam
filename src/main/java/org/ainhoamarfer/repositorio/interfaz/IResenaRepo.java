package org.ainhoamarfer.repositorio.interfaz;

import org.ainhoamarfer.modelo.entidad.CompraEntidad;
import org.ainhoamarfer.modelo.entidad.ResenaEntidad;
import org.ainhoamarfer.modelo.enums.ResenaEstado;
import org.ainhoamarfer.modelo.form.ResenaForm;

import java.util.Optional;

public interface IResenaRepo extends ICrud<ResenaEntidad, ResenaForm, Long> {

    Optional<ResenaEntidad> obtenerPorIdUsuarioYIdJuego(Long idUsuario,  Long idJuego);

    Optional<ResenaEntidad> obtenerPorIdUsuario(Long idUsuario);

    public Optional<ResenaEntidad> actualizarEstadoResena(Long id, ResenaEstado estado);

}
