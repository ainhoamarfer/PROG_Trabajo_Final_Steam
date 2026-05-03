package org.ainhoamarfer.repositorio.implementacion_hibernate;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.ainhoamarfer.modelo.entidad.BibliotecaEntidad;
import org.ainhoamarfer.modelo.entidad.CompraEntidad;
import org.ainhoamarfer.modelo.form.BibliotecaForm;
import org.ainhoamarfer.repositorio.interfaz.IBibliotecaRepo;
import org.ainhoamarfer.transaction.ISesionManager;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class BibliotecaRepoHibernate implements IBibliotecaRepo {

    private ISesionManager sm;
    public BibliotecaRepoHibernate(ISesionManager sm) {
        this.sm = sm;
    }

    @Override
    public List<BibliotecaEntidad> obtenerPorIdUsuario(Long idUsuario) {
        Session session = sm.getSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<BibliotecaEntidad> cq = cb.createQuery(BibliotecaEntidad.class);
        Root<BibliotecaEntidad> root = cq.from(BibliotecaEntidad.class);


        cq.select(root).where(cb.equal(root.get("usuario_id"), idUsuario));

        return session.createQuery(cq).getResultStream().toList();
    }

    @Override
    public Optional<BibliotecaEntidad> obtenerPorIdUsuarioYIdJuego(Long idUsuario, Long idJuego) {
        Session session = sm.getSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<BibliotecaEntidad> cq = cb.createQuery(BibliotecaEntidad.class);
        Root<BibliotecaEntidad> root = cq.from(BibliotecaEntidad.class);


        cq.select(root).where(cb.and(
                        cb.equal(root.get("usuario_id"), idUsuario),
                        cb.equal(root.get("juego_id"), idJuego)
                )
        );

        return session.createQuery(cq).getResultStream().findFirst();
    }

    @Override
    public Optional<BibliotecaEntidad> crear(BibliotecaForm form) {
        Session session = sm.getSession();
        BibliotecaEntidad biblioteca = new BibliotecaEntidad(-1, form.getUsuarioId(), form.getJuegoId(), form.getFechaAdquisicion(), form.getTiempoJuego(), form.getFechaUltimaJugado());
        session.persist(biblioteca);
        return Optional.empty();
    }

    @Override
    public Optional<BibliotecaEntidad> obtenerPorId(Long id) {
        Session session = sm.getSession();

        BibliotecaEntidad biblioteca = session.find(BibliotecaEntidad.class, id);

        return Optional.ofNullable(biblioteca);
    }

    @Override
    public List<BibliotecaEntidad> obtenerTodos() {
        Session session = sm.getSession();

        CriteriaBuilder cBuilder = session.getCriteriaBuilder();

        CriteriaQuery<BibliotecaEntidad> cQuery = cBuilder.createQuery(BibliotecaEntidad.class);
        Root<BibliotecaEntidad> root = cQuery.from(BibliotecaEntidad.class);

        cQuery.select(root).orderBy(cBuilder.asc(root.get("fechaAdquisicion")));

        return session.createQuery(cQuery).getResultList();
    }

    @Override
    public Optional<BibliotecaEntidad> actualizar(Long id, BibliotecaForm form) {
        Session session = sm.getSession();
        Optional<BibliotecaEntidad> bibliotecaOpt = this.obtenerPorId(id);

        if (bibliotecaOpt.isEmpty()) {
            return Optional.empty();
        } else {
            session.merge(new BibliotecaEntidad(id, form.getUsuarioId(), form.getJuegoId(), form.getFechaAdquisicion(), form.getTiempoJuego(), form.getFechaUltimaJugado()));
            return this.obtenerPorId(id);
        }
    }

    @Override
    public boolean eliminar(Long id) {
        Session session = sm.getSession();
        Optional<BibliotecaEntidad> bibliotecaOpt = this.obtenerPorId(id);

        if (bibliotecaOpt.isEmpty()) {
            return false;
        } else {
            session.remove(bibliotecaOpt);
            return true;
        }
    }
}
