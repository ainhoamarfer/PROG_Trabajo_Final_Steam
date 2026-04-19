package org.ainhoamarfer.repositorio.implementacion_memoria;

import org.ainhoamarfer.modelo.entidad.UsuarioEntidad;
import org.ainhoamarfer.modelo.form.UsuarioForm;
import org.ainhoamarfer.repositorio.interfaz.IUsuarioRepo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioRepo implements IUsuarioRepo {

    private static final List<UsuarioEntidad> USUARIOS = new ArrayList<>();
    private static Long idContador = 1L;

    @Override
    public Optional<UsuarioEntidad> crear(UsuarioForm form) {
        Long id = idContador;
        idContador = id + 1L;
        UsuarioEntidad usuario = new UsuarioEntidad(id, form.getNombreUsuario(), form.getEmail(), form.getContrasena(), form.getNombreReal(),
                form.getPais(), form.getFechaNaci(), LocalDate.now(), form.getAvatar(), form.getSaldoCartera());
        USUARIOS.add(usuario);

        return Optional.of(usuario);
    }

    @Override
    public Optional<UsuarioEntidad> obtenerPorId(Long id) {
        return USUARIOS.stream()
                .filter(u -> id.equals(u.getId()))
                .findFirst();
    }

    public Optional<UsuarioEntidad> obtenerPorNombreUsuario(String nombreUsuario) {
        return USUARIOS.stream()
                .filter(u -> nombreUsuario.equals(u.getNombreUsuario()))
                .findFirst();
    }



    public void restarSaldoCartera(Long idUsuario, Double precioJuego) {
        Optional<UsuarioEntidad> usuarioOpt = obtenerPorId(idUsuario);
        UsuarioEntidad usuario = usuarioOpt.orElse(null);
        double nuevoSaldo = usuario.getSaldoCartera() - precioJuego;

        UsuarioForm form = new UsuarioForm(usuario.getNombreUsuario(), usuario.getEmail(), usuario.getContrasena(), usuario.getNombreReal(),
                usuario.getPais(), usuario.getFechaNaci(), usuario.getFechaRegistro(), usuario.getAvatar(), nuevoSaldo, usuario.getEstadoCuenta());
        actualizar(idUsuario, form);
    }

    @Override
    public void sumarSaldoCartera(Long idUsuario, Double precioJuego) {
        Optional<UsuarioEntidad> usuarioOpt = obtenerPorId(idUsuario);
        UsuarioEntidad usuario = usuarioOpt.orElse(null);
        double nuevoSaldo = usuario.getSaldoCartera() + precioJuego;

        UsuarioForm form = new UsuarioForm(usuario.getNombreUsuario(), usuario.getEmail(), usuario.getContrasena(), usuario.getNombreReal(),
                usuario.getPais(), usuario.getFechaNaci(), usuario.getFechaRegistro(), usuario.getAvatar(), nuevoSaldo, usuario.getEstadoCuenta());
        actualizar(idUsuario, form);
    }

    @Override
    public List<UsuarioEntidad> obtenerTodos() {
        return new ArrayList<>(USUARIOS);
    }

    @Override
    public Optional<UsuarioEntidad> actualizar(Long id, UsuarioForm form) {
        Optional<UsuarioEntidad> usuarioOpt = this.obtenerPorId(id);

        if (usuarioOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado");
        } else {
            UsuarioEntidad usuarioActualizado = new UsuarioEntidad(id, form.getNombreUsuario(), form.getEmail(), form.getContrasena(), form.getNombreReal(),
                    form.getPais(), form.getFechaNaci(), form.getFechaRegistro(), form.getAvatar(), form.getSaldoCartera());
            USUARIOS.removeIf((u) -> id.equals(u.getId()));
            USUARIOS.add(usuarioActualizado);
            return Optional.of(usuarioActualizado);
        }
    }

    @Override
    public boolean eliminar(Long id) {
        return USUARIOS.removeIf((u) -> id.equals(u.getId()));
    }
}
