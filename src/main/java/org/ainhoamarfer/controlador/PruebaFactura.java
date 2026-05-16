package org.ainhoamarfer.controlador;

import org.ainhoamarfer.excepciones.ExcepcionValidacion;
import org.ainhoamarfer.modelo.dtos.CompraDTO;
import org.ainhoamarfer.modelo.entidad.*;
import org.ainhoamarfer.modelo.enums.*;
import org.ainhoamarfer.modelo.form.CompraForm;
import org.ainhoamarfer.modelo.form.JuegoForm;
import org.ainhoamarfer.modelo.form.UsuarioForm;
import org.ainhoamarfer.repositorio.implementacion_memoria.BibliotecaRepo;
import org.ainhoamarfer.repositorio.implementacion_memoria.CompraRepo;
import org.ainhoamarfer.repositorio.implementacion_memoria.JuegoRepo;
import org.ainhoamarfer.repositorio.implementacion_memoria.UsuarioRepo;
import org.ainhoamarfer.repositorio.interfaz.*;
import org.ainhoamarfer.transaction.NoOpTransactionManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;


public class PruebaFactura {

    public static void main(String[] args) {
        IUsuarioRepo usuarioRepo = new UsuarioRepo();
        IJuegosRepo juegoRepo = new JuegoRepo();
        ICompraRepo compraRepo = new CompraRepo();
        IBibliotecaRepo bibliotecaRepo = new BibliotecaRepo();
        NoOpTransactionManager tm = new NoOpTransactionManager();


        CompraControlador controlador = new CompraControlador(compraRepo, juegoRepo, usuarioRepo, bibliotecaRepo, tm);

        try {
            UsuarioForm usuarioForm = new UsuarioForm(
                    "testUser",            // nombreUsuario
                    "test@steam.com",      // email
                    "Abc123456",           // contrasena
                    "Test User",           // nombreReal
                    "españa",              // país (válido)
                    LocalDate.of(1990, 1, 1), // fechaNacimiento
                    null,                  // fechaRegistro (se asigna automáticamente)
                    "avatar.png",          // avatar
                    100.0,                 // saldo inicial (suficiente para la compra)
                    UsuarioEstadoCuenta.ACTIVA
            );
            UsuarioEntidad usuario = usuarioRepo.crear(usuarioForm)
                    .orElseThrow(() -> new RuntimeException("No se pudo crear usuario"));
            System.out.println("✅ Usuario creado - ID: " + usuario.getId() + ", Saldo: " + usuario.getSaldoCartera());

            JuegoForm juegoForm = new JuegoForm(
                    "The Legend of Testing",
                    "Un juego de prueba para validar compras.",
                    "TestDev",
                    LocalDate.now().minusDays(5),
                    29.99,                     // precio base
                    10,                        // descuento actual (10%)
                    "Aventura",
                    "Español, Inglés",
                    JuegoClasificacionEdad.PEGI_12,
                    JuegoEstado.DISPONIBLE
            );
            JuegoEntidad juego = juegoRepo.crear(juegoForm)
                    .orElseThrow(() -> new RuntimeException("No se pudo crear juego"));
            System.out.println("✅ Juego creado - ID: " + juego.getId() + ", Título: " + juego.getTitulo());

            CompraForm compraForm = new CompraForm(
                    usuario.getId(),
                    juego.getId(),
                    LocalDate.now(),
                    juego.getPrecioBase(),
                    juego.getDescuentoActual(),
                    CompraEstadoEnum.PENDIENTE,
                    CompraMetodoPagoEnum.CARTERA_STEAM
            );
            CompraDTO compraPendiente = controlador.realizarCompra(compraForm);
            System.out.println("Compra creada - ID: " + compraPendiente.getId() + ", Estado: " + compraPendiente.getEstadoCompra());

            // ----- Procesar pago (COMPLETADA y añade a biblioteca) -----
            CompraDTO compraCompletada = controlador.procesarPago(compraPendiente.getId());
            System.out.println("Pago procesado - Estado: " + compraCompletada.getEstadoCompra());

            // ----- Generar factura -----
            String rutaFactura = controlador.generarFactura(compraCompletada.getId());
            System.out.println("\n  Factura generada en: " + rutaFactura);

            // ----- Leer y mostrar contenido -----
            String contenido = Files.readString(Path.of(rutaFactura));
            System.out.println("\n--- CONTENIDO DE LA FACTURA ---\n");
            System.out.println(contenido);

        } catch (ExcepcionValidacion e) {
            System.err.println("Error de validación: " + e.getErrores());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error de E/S: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
