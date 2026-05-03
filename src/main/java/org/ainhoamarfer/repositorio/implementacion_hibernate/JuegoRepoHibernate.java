package org.ainhoamarfer.repositorio.implementacion_hibernate;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.ainhoamarfer.modelo.entidad.JuegoEntidad;
import org.ainhoamarfer.modelo.entidad.UsuarioEntidad;
import org.ainhoamarfer.modelo.form.JuegoForm;
import org.ainhoamarfer.repositorio.interfaz.IJuegosRepo;
import org.ainhoamarfer.transaction.ISesionManager;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class JuegoRepoHibernate implements IJuegosRepo {

    private ISesionManager sm;
    public JuegoRepoHibernate(ISesionManager sm) {
        this.sm = sm;
    }

    @Override
    public Optional<JuegoEntidad> obtenerPorTitulo(String titulo) {
        Session session = sm.getSession();
        //query builder, para hacer select con where
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<JuegoEntidad> cq = cb.createQuery(JuegoEntidad.class);
        Root<JuegoEntidad> root = cq.from(JuegoEntidad.class);

        //select * from Juegos, puedes quitar el order by
        cq.select(root).where(cb.equal(root.get("titulo"), titulo));

        return session.createQuery(cq).getResultStream().findFirst();
    }

    @Override
    public Optional<JuegoEntidad> crear(JuegoForm form) {

        Session session = sm.getSession();

        //hibernate ignora el campo id porque se lo indicamos en juegoEntidad en los atributos, le decimos cualquier número
        JuegoEntidad juego = new JuegoEntidad(-1, form.getTitulo(), form.getDescripcion(), form.getDesarrollador(), form.getFechaLanzamiento(), form.getPrecioBase(),
                form.getDescuentoActual(), form.getCategoria(), form.getIdiomas(), form.getClasificacionEdad(), form.getEstado());
        session.persist(juego);

        return Optional.of(juego);
    }

    @Override
    public Optional<JuegoEntidad> obtenerPorId(Long id) {
        Session session = sm.getSession();

        JuegoEntidad juego = session.find(JuegoEntidad.class, id);

        return Optional.ofNullable(juego);
    }

    @Override
    public List<JuegoEntidad> obtenerTodos() {
        Session session = sm.getSession();

        //query builder, para modificar la consulta base, es un select *, operación de lectura
        CriteriaBuilder cBuilder = session.getCriteriaBuilder();

        CriteriaQuery<JuegoEntidad> cQuery = cBuilder.createQuery(JuegoEntidad.class);
        Root<JuegoEntidad> root = cQuery.from(JuegoEntidad.class);

        //select * from Juegos, puedes quitar el order by
        cQuery.select(root).orderBy(cBuilder.asc(root.get("titulo")));

        return session.createQuery(cQuery).getResultList();
    }

    @Override
    public Optional<JuegoEntidad> actualizar(Long id, JuegoForm form) {
        Session session = sm.getSession();
        Optional<JuegoEntidad> juegoOpt = this.obtenerPorId(id);

        if (juegoOpt.isEmpty()) {
            return Optional.empty();
        } else {
            session.merge(new JuegoEntidad(id, form.getTitulo(), form.getDescripcion(), form.getDesarrollador(), form.getFechaLanzamiento(), form.getPrecioBase(),
                    form.getDescuentoActual(), form.getCategoria(), form.getIdiomas(), form.getClasificacionEdad(), form.getEstado()));
            return obtenerPorId(id);
        }
    }

    @Override
    public boolean eliminar(Long id) {
        Session session = sm.getSession();
        Optional<JuegoEntidad> juegoOpt = obtenerPorId(id);

        if (juegoOpt.isEmpty()) {
            return false;
        } else {
            session.remove(juegoOpt);
            return true;
        }
    }
}
