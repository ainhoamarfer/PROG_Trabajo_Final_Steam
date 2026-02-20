package org.ainhoamarfer.Repositorio.ImplementacionMemoria;

import org.ainhoamarfer.Modelo.Entidad.CompraEntidad;
import org.ainhoamarfer.Modelo.Form.CompraForm;
import org.ainhoamarfer.Repositorio.Interfaz.ICompraRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompraRepo implements ICompraRepo {

    private static final List<CompraEntidad> compras = new ArrayList<>();
    private static Long idContador = 1L;

    @Override
    public Optional<CompraEntidad> crear(CompraForm form) {
        Long id = idContador;
        idContador = id + 1L;
        CompraEntidad compra = new CompraEntidad(id, form.getUsuarioId(), form.getJuegoId(), form.getPrecioSinDes(), form.getDescuento(), form.getMetodoPago());
        compras.add(compra);

        return Optional.of(compra);
    }

    @Override
    public Optional<CompraEntidad> obtenerPorId(Long id) {
        return compras.stream()
                .filter(u -> id.equals(u.getId()))
                .findFirst();
    }

    @Override
    public List<CompraEntidad> obtenerTodos() {
        return new ArrayList<>(compras);
    }

    @Override
    public Optional<CompraEntidad> actualizar(Long id, CompraForm form) {
        Optional<CompraEntidad> compraOpt = this.obtenerPorId(id);

        if (compraOpt.isEmpty()) {
            throw new IllegalArgumentException("Compra no encontrada");
        } else {
            CompraEntidad CompraActualizada = new CompraEntidad(id, form.getUsuarioId(), form.getJuegoId(), form.getPrecioSinDes(), form.getDescuento(), form.getMetodoPago());
            compras.removeIf((u) -> id.equals(u.getId()));
            compras.add(CompraActualizada);
            return Optional.of(CompraActualizada);
        }
    }

    @Override
    public boolean eliminar(Long id) {
        return compras.removeIf((u) -> id.equals(u.getId()));
    }
}
