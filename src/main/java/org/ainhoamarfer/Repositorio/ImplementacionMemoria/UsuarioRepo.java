package org.ainhoamarfer.Repositorio.ImplementacionMemoria;

import org.ainhoamarfer.Modelo.Entidad.UsuarioEntidad;
import org.ainhoamarfer.Modelo.Form.UsuarioForm;
import org.ainhoamarfer.Repositorio.Interfaz.IUsuarioRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioRepo implements IUsuarioRepo {

    private static final List<UsuarioEntidad> usuarios = new ArrayList<>();
    private static Long idContador = 1L;

    @Override
    public Optional<UsuarioEntidad> crear(UsuarioForm form) {
        Long id = idContador;
        idContador = id + 1L;
        UsuarioEntidad usuario = new UsuarioEntidad(id, form.getNombreUsuario(), form.getEmail(), form.getContrasena(), form.getNombreReal(),
                form.getPais(), form.getFechaNaci(), form.getFechaRegistro(), form.getAvatar(), form.getSaldoCartera(),form.getEstadoCuenta());
        usuarios.add(usuario);

        return Optional.of(usuario);
    }

    @Override
    public Optional<UsuarioEntidad> obtenerPorId(Long id) {
        return usuarios.stream()
                .filter(u -> id.equals(u.getId()))
                .findFirst();
    }

    public Optional<UsuarioEntidad> obtenerPorNombreUsuario(String nombreUsuario) {
        return usuarios.stream()
                .filter(u -> nombreUsuario.equals(u.getNombreUsuario()))
                .findFirst();
    }

    @Override
    public List<UsuarioEntidad> obtenerTodos() {
        return new ArrayList<>(usuarios);
    }

    @Override
    public Optional<UsuarioEntidad> actualizar(Long id, UsuarioForm form) {
        Optional<UsuarioEntidad> usuarioOpt = this.obtenerPorId(id);

        if (usuarioOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuario no encontrado");
        } else {
            UsuarioEntidad usuarioActualizado = new UsuarioEntidad(id, form.getNombreUsuario(), form.getEmail(), form.getContrasena(), form.getNombreReal(),
                    form.getPais(), form.getFechaNaci(), form.getFechaRegistro(), form.getAvatar(), form.getSaldoCartera(), form.getEstadoCuenta());
            usuarios.removeIf((u) -> id.equals(u.getId()));
            usuarios.add(usuarioActualizado);
            return Optional.of(usuarioActualizado);
        }
    }

    @Override
    public boolean eliminar(Long id) {
        return usuarios.removeIf((u) -> id.equals(u.getId()));
    }
}
