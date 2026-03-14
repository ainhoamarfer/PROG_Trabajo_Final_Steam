package org.ainhoamarfer.repositorio.interfaz;

import org.ainhoamarfer.modelo.entidad.CompraEntidad;
import org.ainhoamarfer.modelo.enums.CompraEstadoEnum;
import org.ainhoamarfer.modelo.form.CompraForm;

import java.util.Optional;

public interface ICompraRepo extends ICrud<CompraEntidad, CompraForm, Long> {

    Optional<CompraEntidad> obtenerPorIdUsuario(Long id);

    void actualizarEstadoCompra (Long idCompra, CompraEstadoEnum estadoCompra);

}
