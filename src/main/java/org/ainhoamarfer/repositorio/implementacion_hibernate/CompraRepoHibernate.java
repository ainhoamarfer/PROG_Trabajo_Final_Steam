package org.ainhoamarfer.repositorio.implementacion_hibernate;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.ainhoamarfer.modelo.entidad.BibliotecaEntidad;
import org.ainhoamarfer.modelo.entidad.CompraEntidad;
import org.ainhoamarfer.modelo.entidad.JuegoEntidad;
import org.ainhoamarfer.modelo.entidad.ResenaEntidad;
import org.ainhoamarfer.modelo.enums.CompraEstadoEnum;
import org.ainhoamarfer.modelo.form.CompraForm;
import org.ainhoamarfer.repositorio.interfaz.ICompraRepo;
import org.ainhoamarfer.transaction.ISesionManager;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class CompraRepoHibernate implements ICompraRepo {

    private ISesionManager sm;
    public CompraRepoHibernate(ISesionManager sm) {
        this.sm = sm;
    }

    @Override
    public Optional<CompraEntidad> obtenerPorIdUsuario(Long idUsuario) {
        Session session = sm.getSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<CompraEntidad> cq = cb.createQuery(CompraEntidad.class);
        Root<CompraEntidad> root = cq.from(CompraEntidad.class);


        cq.select(root).where(cb.equal(root.get("usuario_id"), idUsuario));

        return session.createQuery(cq).getResultStream().findFirst();
    }

    @Override
    public Optional<CompraEntidad> obtenerPorIdUsuarioYIdCompra(Long idUsuario, Long id) {
        Session session = sm.getSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<CompraEntidad> cq = cb.createQuery(CompraEntidad.class);
        Root<CompraEntidad> root = cq.from(CompraEntidad.class);


        cq.select(root).where(cb.and(
                        cb.equal(root.get("usuario_id"), idUsuario),
                        cb.equal(root.get("id"), id)
                )
        );

        return session.createQuery(cq).getResultStream().findFirst();
    }

    @Override
    public void actualizarEstadoCompra(Long idCompra, CompraEstadoEnum estadoCompra) {
        Session session = sm.getSession();
        Transaction tx = session.beginTransaction();
        try (session) {
            CompraEntidad compraActual = session.get(CompraEntidad.class, idCompra);
            if (compraActual == null) {
                throw new IllegalArgumentException("Compra no encontrada");
            }

            CompraEntidad compraModificada = new CompraEntidad(compraActual.getId(), compraActual.getUsuarioId(), compraActual.getJuegoId(),
                    compraActual.getFechaCompra(), compraActual.getPrecioBase(), compraActual.getPorcentajeDescuento(), compraActual.getMetodoPago(), estadoCompra
            );

            // merge actualiza la fila existente o inserta si no existe
            session.merge(compraModificada);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Override
    public Optional<CompraEntidad> crear(CompraForm form) {
        Session session = sm.getSession();
        CompraEntidad compra = new CompraEntidad(-1, form.getUsuarioId(), form.getJuegoId(), LocalDate.now(), form.getPrecioBase(),
                form.getDescuentoActual(), form.getMetodoPago(), CompraEstadoEnum.PENDIENTE);
        session.persist(compra);

        return Optional.empty();
    }

    @Override
    public Optional<CompraEntidad> obtenerPorId(Long id) {
        Session session = sm.getSession();

        CompraEntidad compra = session.find(CompraEntidad.class, id);

        return Optional.ofNullable(compra);
    }

    @Override
    public List<CompraEntidad> obtenerTodos() {
        Session session = sm.getSession();

        CriteriaBuilder cBuilder = session.getCriteriaBuilder();

        CriteriaQuery<CompraEntidad> cQuery = cBuilder.createQuery(CompraEntidad.class);
        Root<CompraEntidad> root = cQuery.from(CompraEntidad.class);

        cQuery.select(root).orderBy(cBuilder.asc(root.get("fecha_compra")));

        return session.createQuery(cQuery).getResultList();
    }

    @Override
    public Optional<CompraEntidad> actualizar(Long id, CompraForm form) {
        Session session = sm.getSession();
        Optional<CompraEntidad> compraEntidad = this.obtenerPorId(id);

        if (compraEntidad.isEmpty()) {
            return Optional.empty();
        } else {
            session.merge(new CompraEntidad(id, form.getUsuarioId(), form.getJuegoId(), LocalDate.now(), form.getPrecioBase(),
                    form.getDescuentoActual(), form.getMetodoPago(), compraEntidad.get().getEstadoCompra()));
            return this.obtenerPorId(id);
        }
    }

    @Override
    public boolean eliminar(Long id) {
        Session session = sm.getSession();
        Optional<CompraEntidad> compraEntidad = this.obtenerPorId(id);

        if (compraEntidad.isEmpty()) {
            return false;
        } else {
            session.remove(compraEntidad);
            return true;
        }
    }
}
