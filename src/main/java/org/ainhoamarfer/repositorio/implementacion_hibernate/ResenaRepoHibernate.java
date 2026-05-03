package org.ainhoamarfer.repositorio.implementacion_hibernate;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.ainhoamarfer.modelo.entidad.CompraEntidad;
import org.ainhoamarfer.modelo.entidad.JuegoEntidad;
import org.ainhoamarfer.modelo.entidad.ResenaEntidad;
import org.ainhoamarfer.modelo.enums.ResenaEstado;
import org.ainhoamarfer.modelo.form.ResenaForm;
import org.ainhoamarfer.repositorio.interfaz.IResenaRepo;
import org.ainhoamarfer.transaction.ISesionManager;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.service.internal.StandardSessionFactoryServiceInitiators;

import java.util.List;
import java.util.Optional;

public class ResenaRepoHibernate implements IResenaRepo {

    private ISesionManager sm;
    public ResenaRepoHibernate(ISesionManager sm) {
        this.sm = sm;
    }

    @Override
    public Optional<ResenaEntidad> obtenerPorIdUsuarioYIdJuego(Long idUsuario, Long idJuego) {
        Session session = sm.getSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<ResenaEntidad> cq = cb.createQuery(ResenaEntidad.class);
        Root<ResenaEntidad> root = cq.from(ResenaEntidad.class);


        cq.select(root).where(cb.and(
                        cb.equal(root.get("usuario_id"), idUsuario),
                        cb.equal(root.get("juego_id"), idJuego)
                )
        );

        return session.createQuery(cq).getResultStream().findFirst();
    }

    @Override
    public Optional<ResenaEntidad> obtenerPorIdUsuario(Long idUsuario) {
        Session session = sm.getSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<ResenaEntidad> cq = cb.createQuery(ResenaEntidad.class);
        Root<ResenaEntidad> root = cq.from(ResenaEntidad.class);


        cq.select(root).where(cb.equal(root.get("usuario_id"), idUsuario));

        return session.createQuery(cq).getResultStream().findFirst();
    }

    @Override
    public void actualizarEstadoResena(Long id, ResenaEstado estado) {
        Session session = sm.getSession();
        Transaction tx = session.beginTransaction();
        try (session) {
            ResenaEntidad resenaActual = session.get(ResenaEntidad.class, id);
            if (resenaActual == null) {
                throw new IllegalArgumentException("Compra no encontrada");
            }

            ResenaEntidad resenaMod = new ResenaEntidad(resenaActual.getId(), resenaActual.getUsuarioId(), resenaActual.getJuegoId(), resenaActual.isRecomendado(),
                    resenaActual.getTexto(), resenaActual.getHorasJugadas(), resenaActual.getFechaPublicacion(), resenaActual.getFechaUltEdicion(), estado
            );

            // merge actualiza la fila existente o inserta si no existe
            session.merge(resenaMod);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
    }

    @Override
    public Optional<ResenaEntidad> crear(ResenaForm form) {

        Session session = sm.getSession();
        ResenaEntidad resena = new ResenaEntidad(-1, form.getUsuarioId(), form.getJuegoId(), form.isRecomendado(), form.getTexto(), form.getHorasJugadas(), form.getFechaPublicacion(),
                form.getFechaUltEdicion(), form.getEstado());
        session.persist(resena);
        return Optional.empty();
    }

    @Override
    public Optional<ResenaEntidad> obtenerPorId(Long id) {
        Session session = sm.getSession();

        ResenaEntidad resena = session.find(ResenaEntidad.class, id);

        return Optional.ofNullable(resena);
    }

    @Override
    public List<ResenaEntidad> obtenerTodos() {
        Session session = sm.getSession();

        CriteriaBuilder cBuilder = session.getCriteriaBuilder();

        CriteriaQuery<ResenaEntidad> cQuery = cBuilder.createQuery(ResenaEntidad.class);
        Root<ResenaEntidad> root = cQuery.from(ResenaEntidad.class);

        cQuery.select(root).orderBy(cBuilder.asc(root.get("fechaPublicacion")));

        return session.createQuery(cQuery).getResultList();
    }

    @Override
    public Optional<ResenaEntidad> actualizar(Long id, ResenaForm form) {
        Session session = sm.getSession();
        Optional<ResenaEntidad> resenaEntidad = this.obtenerPorId(id);

        if (resenaEntidad.isEmpty()) {
            return Optional.empty();
        } else {
            session.merge(new ResenaEntidad(id, form.getUsuarioId(), form.getJuegoId(), form.isRecomendado(), form.getTexto(), form.getHorasJugadas(), form.getFechaPublicacion(),
                    form.getFechaUltEdicion(), form.getEstado()));
            return this.obtenerPorId(id);
        }
    }

    @Override
    public boolean eliminar(Long id) {
        Session session = sm.getSession();
        Optional<ResenaEntidad> resenaEntidad = this.obtenerPorId(id);

        if (resenaEntidad.isEmpty()) {
            return false;
        } else {
            session.remove(resenaEntidad.get());
            return true;
        }
    }
}
