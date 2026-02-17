package org.ainhoamarfer;

import org.ainhoamarfer.Controlador.UsuarioControlador;
import org.ainhoamarfer.Excepciones.ExcepcionValidacion;
import org.ainhoamarfer.Modelo.DTOs.ErrorDTO;
import org.ainhoamarfer.Modelo.DTOs.UsuarioDTO;
import org.ainhoamarfer.Modelo.Form.UsuarioForm;
import org.ainhoamarfer.Repositorio.ImplementacionMemoria.UsuarioRepo;
import org.ainhoamarfer.Repositorio.Interfaz.IJuegosRepo;

import java.time.LocalDate;

public class SteamProgram {

    public static void main(String[] args) {

        UsuarioRepo usuarioRepo = new UsuarioRepo();
        UsuarioControlador controlador = new UsuarioControlador(usuarioRepo);


        // Crear un formulario de usuario de ejemplo
        UsuarioForm form = new UsuarioForm(
                "usuarioEjemplo",  // nombreUsuario
                "usuario@example.com",  // email
                "password123",  // contrasena
                "Nombre Real",  // nombreReal
                "españa",  // pais (en minúsculas, como en la validación)
                LocalDate.of(2000, 1, 1),  // fechaNaci
                null,  // fechaRegistro (se setea automáticamente)
                "avatar.png",  // avatar
                50.00,  // saldoCartera
                null  // estadoCuenta (se setea a ACTIVA por defecto)
        );

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
