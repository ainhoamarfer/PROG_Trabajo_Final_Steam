package org.ainhoamarfer;

import org.ainhoamarfer.controlador.UsuarioControlador;
import org.ainhoamarfer.excepciones.ExcepcionValidacion;
import org.ainhoamarfer.modelo.dtos.ErrorDTO;
import org.ainhoamarfer.modelo.dtos.UsuarioDTO;
import org.ainhoamarfer.modelo.form.UsuarioForm;
import org.ainhoamarfer.repositorio.implementacion_memoria.UsuarioRepo;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SteamProgram {

    public static void main(String[] args) {

        List<ErrorDTO> errores = new ArrayList<>();
        UsuarioRepo usuarioRepo = new UsuarioRepo();
        UsuarioControlador usuarioControlador = new UsuarioControlador(usuarioRepo);

        UsuarioForm form = new UsuarioForm("usuarioEjemplo", "ainhoa@gmail.com", "Contrasena123", "Ainhoa Martinez", "España",
                LocalDate.of(2000, 1, 1), null, "avatar.png", 50.00, null);

        try {
            // Registrar nuevo usuario
            UsuarioDTO usuarioRegistrado = usuarioControlador.registrarNuevoUsuario(form);
            System.out.println("Usuario registrado exitosamente:");
            System.out.println("ID: " + usuarioRegistrado.getId());
            System.out.println("Nombre Usuario: " + usuarioRegistrado.getNombreUsuario());
            System.out.println("Email: " + usuarioRegistrado.getEmail());
            System.out.println("Saldo: " + usuarioRegistrado.getSaldoCartera());

            // Consultar usuario y saldo
            UsuarioDTO usu = usuarioControlador.consultarPerfil(1);
            System.out.println("El email del usuario 1 es: " + usu.getEmail());
            Double saldo = usuarioControlador.consultarSaldoCartera(usu.getId());
            System.out.println("Saldo consultado: " + saldo);

            // Añadir saldo
            Double nuevoSaldo = usuarioControlador.anadirSaldoCartera(25.50, 1);
            System.out.println("Nuevo saldo después de añadir: " + nuevoSaldo);

        } catch (ExcepcionValidacion e) {
            System.out.println("Errores de validación:");
            for (ErrorDTO error : e.getErrores()) {
                System.out.println("- " + error.campo() + ": " + error.mensaje());
            }
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }
}
