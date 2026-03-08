package org.ainhoamarfer.repositorio.implementacionMemoria;

import org.ainhoamarfer.modelo.entidad.CompraEntidad;
import org.ainhoamarfer.modelo.form.CompraForm;
import org.ainhoamarfer.repositorio.interfaz.ICompraRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompraRepo implements ICompraRepo {

    private static final List<CompraEntidad> COMPRAS = new ArrayList<>();
    private static Long idContador = 1L;

    @Override
    public Optional<CompraEntidad> crear(CompraForm form) {
        Long id = idContador;
        idContador = id + 1L;
        CompraEntidad compra = new CompraEntidad(id, form.getUsuarioId(), form.getJuegoId(), form.getPrecioSinDes(), form.getDescuento(), form.getMetodoPago());
        COMPRAS.add(compra);

        return Optional.of(compra);
    }

    @Override
    public Optional<CompraEntidad> obtenerPorId(Long id) {
        return COMPRAS.stream()
                .filter(u -> id.equals(u.getId()))
                .findFirst();
    }

    @Override
    public List<CompraEntidad> obtenerTodos() {
        return new ArrayList<>(COMPRAS);
    }

    @Override
    public Optional<CompraEntidad> actualizar(Long id, CompraForm form) {
        Optional<CompraEntidad> compraOpt = this.obtenerPorId(id);

        if (compraOpt.isEmpty()) {
            throw new IllegalArgumentException("Compra no encontrada");
        } else {
            CompraEntidad CompraActualizada = new CompraEntidad(id, form.getUsuarioId(), form.getJuegoId(), form.getPrecioSinDes(), form.getDescuento(), form.getMetodoPago());
            COMPRAS.removeIf((u) -> id.equals(u.getId()));
            COMPRAS.add(CompraActualizada);
            return Optional.of(CompraActualizada);
        }
    }

    @Override
    public boolean eliminar(Long id) {
        return COMPRAS.removeIf((u) -> id.equals(u.getId()));
    }
}
