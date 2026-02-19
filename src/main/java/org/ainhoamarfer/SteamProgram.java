package org.ainhoamarfer;

import org.ainhoamarfer.Controlador.UsuarioControlador;
import org.ainhoamarfer.Excepciones.ExcepcionValidacion;
import org.ainhoamarfer.Modelo.DTOs.ErrorDTO;
import org.ainhoamarfer.Modelo.DTOs.UsuarioDTO;
import org.ainhoamarfer.Modelo.Form.UsuarioForm;
import org.ainhoamarfer.Repositorio.ImplementacionMemoria.UsuarioRepo;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SteamProgram {

    public static void main(String[] args) {

        List<ErrorDTO> errores = new ArrayList<>();
        UsuarioRepo usuarioRepo = new UsuarioRepo();
        UsuarioControlador controlador = new UsuarioControlador(usuarioRepo);

        UsuarioForm form = new UsuarioForm("usuarioEjemplo", "usuario@example.com", "password123", "Nombre Real", "españa",
                LocalDate.of(2000, 1, 1), null, "avatar.png", 50.00, null);

        try {
            // Registrar nuevo usuario
            UsuarioDTO usuarioRegistrado = controlador.registrarNuevoUsuario(form);
            System.out.println("Usuario registrado exitosamente:");
            System.out.println("ID: " + usuarioRegistrado.getId());
            System.out.println("Nombre Usuario: " + usuarioRegistrado.getNombreUsuario());
            System.out.println("Email: " + usuarioRegistrado.getEmail());
            System.out.println("Saldo: " + usuarioRegistrado.getSaldoCartera());

            // Consultar saldo
            long idUsuario = usuarioRegistrado.getId();
            Double saldo = controlador.consultarSaldoCartera(idUsuario);
            System.out.println("Saldo consultado: " + saldo);

            // Añadir saldo
            Double nuevoSaldo = controlador.anadirSaldoCartera(25.50, idUsuario);
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
