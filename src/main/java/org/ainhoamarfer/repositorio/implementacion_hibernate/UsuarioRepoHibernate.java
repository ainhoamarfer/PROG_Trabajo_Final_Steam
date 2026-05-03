package org.ainhoamarfer.repositorio.implementacion_hibernate;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.ainhoamarfer.modelo.entidad.ResenaEntidad;
import org.ainhoamarfer.modelo.entidad.UsuarioEntidad;
import org.ainhoamarfer.modelo.form.UsuarioForm;
import org.ainhoamarfer.repositorio.interfaz.IUsuarioRepo;
import org.ainhoamarfer.transaction.ISesionManager;
import org.hibernate.Session;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class UsuarioRepoHibernate implements IUsuarioRepo {

    private final ISesionManager sm;

    public UsuarioRepoHibernate(ISesionManager sm) {
        this.sm = sm;
    }

    @Override
    public Optional<UsuarioEntidad> crear(UsuarioForm form) {
        Session session = sm.getSession();

        UsuarioEntidad usuario = new UsuarioEntidad(
                -1, form.getNombreUsuario(), form.getEmail(), form.getContrasena(), form.getNombreReal(), form.getPais(), form.getFechaNaci(),
                LocalDate.now(), form.getAvatar(), form.getSaldoCartera());

        session.persist(usuario);
        return Optional.of(usuario);
    }

    @Override
    public Optional<UsuarioEntidad> obtenerPorId(Long id) {
        Session session = sm.getSession();

        UsuarioEntidad usuario = session.find(UsuarioEntidad.class, id);

        return Optional.ofNullable(usuario);
    }

    @Override
    public Optional<UsuarioEntidad> obtenerPorNombreUsuario(String nombreUsuario) {
        Session session = sm.getSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<UsuarioEntidad> cq = cb.createQuery(UsuarioEntidad.class);
        Root<UsuarioEntidad> root = cq.from(UsuarioEntidad.class);

        cq.select(root).where(cb.equal(root.get("nombre_usuario"), nombreUsuario));
        return session.createQuery(cq).getResultStream().findFirst();
    }

    @Override
    public List<UsuarioEntidad> obtenerTodos() {
        Session session = sm.getSession();

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<UsuarioEntidad> cq = cb.createQuery(UsuarioEntidad.class);
        Root<UsuarioEntidad> root = cq.from(UsuarioEntidad.class);

        cq.select(root).orderBy(cb.asc(root.get("fecha_registro")));
        return session.createQuery(cq).getResultList();
    }

    @Override
    public Optional<UsuarioEntidad> actualizar(Long id, UsuarioForm form) {
        Session session = sm.getSession();
        Optional<UsuarioEntidad> usuarioOpt = this.obtenerPorId(id);

        if (usuarioOpt.isEmpty()) {
            return Optional.empty();
        } else {
            session.merge(new UsuarioEntidad(
                    id, form.getNombreUsuario(), form.getEmail(), form.getContrasena(), form.getNombreReal(), form.getPais(), form.getFechaNaci(),
                    usuarioOpt.get().getFechaRegistro(), form.getAvatar(), form.getSaldoCartera()
            ));
            return obtenerPorId(id);
        }
    }

    @Override
    public boolean eliminar(Long id) {
        Session session = sm.getSession();
        Optional<UsuarioEntidad> usuarioOpt = this.obtenerPorId(id);

        if (usuarioOpt.isEmpty()) {
            return false;
        } else {
            session.remove(usuarioOpt.get());
            return true;
        }
    }

    @Override
    public void restarSaldoCartera(Long idUsuario, Double precioJuego) {
        Session session = sm.getSession();
        Optional<UsuarioEntidad> usuarioOpt = obtenerPorId(idUsuario);

        if (usuarioOpt.isPresent()) {
            UsuarioEntidad usuario = usuarioOpt.orElse(null);

            double nuevoSaldo = usuario.getSaldoCartera() - precioJuego;
            UsuarioEntidad actualizado = new UsuarioEntidad(idUsuario, usuario.getNombreUsuario(), usuario.getEmail(), usuario.getContrasena(), usuario.getNombreReal(),
                    usuario.getPais(), usuario.getFechaNaci(), usuario.getFechaRegistro(), usuario.getAvatar(), nuevoSaldo);

            session.merge(actualizado);
        }
    }

    @Override
    public void sumarSaldoCartera(Long idUsuario, Double precioJuego) {
        Session session = sm.getSession();
        Optional<UsuarioEntidad> usuarioOpt = obtenerPorId(idUsuario);

        if (usuarioOpt.isPresent()) {
            UsuarioEntidad usuario = usuarioOpt.orElse(null);

            double nuevoSaldo = usuario.getSaldoCartera() + precioJuego;
            UsuarioEntidad actualizado = new UsuarioEntidad(idUsuario, usuario.getNombreUsuario(), usuario.getEmail(), usuario.getContrasena(), usuario.getNombreReal(),
                    usuario.getPais(), usuario.getFechaNaci(), usuario.getFechaRegistro(), usuario.getAvatar(), nuevoSaldo);

            session.merge(actualizado);
        }
    }

    //TODO: implementar este método
    @Override
    public Optional<UsuarioEntidad> obtenerPorEmail(String email) {
        return Optional.empty();
    }
}
