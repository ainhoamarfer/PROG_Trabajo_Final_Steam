package org.ainhoamarfer.controlador;

import org.ainhoamarfer.excepciones.ExcepcionValidacion;
import org.ainhoamarfer.modelo.dtos.CompraDTO;
import org.ainhoamarfer.modelo.dtos.ErrorDTO;
import org.ainhoamarfer.modelo.dtos.JuegoDTO;
import org.ainhoamarfer.modelo.dtos.UsuarioDTO;
import org.ainhoamarfer.modelo.entidad.JuegoEntidad;
import org.ainhoamarfer.modelo.entidad.UsuarioEntidad;
import org.ainhoamarfer.modelo.enums.*;
import org.ainhoamarfer.modelo.form.BibliotecaForm;
import org.ainhoamarfer.modelo.form.CompraForm;
import org.ainhoamarfer.modelo.form.JuegoForm;
import org.ainhoamarfer.modelo.form.UsuarioForm;
import org.ainhoamarfer.repositorio.implementacion_memoria.BibliotecaRepo;
import org.ainhoamarfer.repositorio.implementacion_memoria.CompraRepo;
import org.ainhoamarfer.repositorio.implementacion_memoria.JuegoRepo;
import org.ainhoamarfer.repositorio.implementacion_memoria.UsuarioRepo;
import org.ainhoamarfer.repositorio.interfaz.IBibliotecaRepo;
import org.ainhoamarfer.repositorio.interfaz.ICompraRepo;
import org.ainhoamarfer.repositorio.interfaz.IJuegosRepo;
import org.ainhoamarfer.repositorio.interfaz.IUsuarioRepo;

import java.time.LocalDate;

public class MainPruebas {

    public static void main(String[] args) {

        IUsuarioRepo usuarioRepo = new UsuarioRepo();
        IJuegosRepo juegoRepo = new JuegoRepo();
        ICompraRepo compraRepo = new CompraRepo();
        IBibliotecaRepo bibliotecaRepo = new BibliotecaRepo();

        UsuarioControlador usuarioControlador = new UsuarioControlador(usuarioRepo);
        JuegosControlador juegosControlador = new JuegosControlador(juegoRepo);
        CompraControlador compraControlador = new CompraControlador(compraRepo, juegoRepo, usuarioRepo, bibliotecaRepo);

        try {
            //usuario saldo suficiente
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

            UsuarioDTO usuario = usuarioControlador.registrarNuevoUsuario(usuarioForm);
            System.out.println("Usuario registrado: " + usuario.getNombreUsuario() +
                    " (ID: " + usuario.getId() + ", Saldo: " + usuario.getSaldoCartera() + ")");

            // 4. Registrar un juego de prueba
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
            JuegoDTO juego = juegosControlador.anadirJuego(juegoForm);
            System.out.println("Juego añadido: " + juego.getTitulo() +
                    " (ID: " + juego.getId() + ", Precio base: " + juego.getPrecioBase() +
                    ", Descuento: " + juego.getDescuentoActual() + "%)");

            // 5. Realizar la compra con método de pago Cartera Steam
            CompraForm compraForm = new CompraForm(
                    usuario.getId(),
                    juego.getId(),
                    LocalDate.now(),
                    juego.getPrecioBase(),
                    juego.getDescuentoActual(),
                    CompraEstadoEnum.PENDIENTE,
                    CompraMetodoPagoEnum.CARTERA_STEAM
            );
            CompraDTO compraRealizada = compraControlador.realizarCompra(compraForm);
            System.out.println("Compra creada - ID: " + compraRealizada.getId() +
                    ", Estado: " + compraRealizada.getEstadoCompra() +
                    ", Precio final: " + compraRealizada.getPrecioBase());

            // 6. Procesar el pago
            CompraDTO compraCompletada = compraControlador.procesarPago(compraRealizada.getId());
            System.out.println("Pago procesado. Nuevo estado: " + compraCompletada.getEstadoCompra() +
                    ", Saldo del usuario tras compra: " +
                    usuarioControlador.consultarSaldoCartera(usuario.getId()));

            // 7. Consultar detalles de la compra
            CompraDTO detalles = compraControlador.consultarDetallesCompra(compraCompletada.getId(), usuario.getId());
            System.out.println("Detalles de la compra: " +
                    "Juego: " + detalles.getJuego().getTitulo() +
                    ", Fecha: " + detalles.getFechaCompra() +
                    ", Método de pago: " + detalles.getMetodoPago());

            // 8. Solicitar reembolso (horas jugadas = 0, dentro del plazo)
            CompraDTO compraReembolsada = compraControlador.solicitarReembolso(compraCompletada.getId());
            System.out.println("Reembolso solicitado. Nuevo estado: " + compraReembolsada.getEstadoCompra() +
                    ", Saldo del usuario tras reembolso: " +
                    usuarioControlador.consultarSaldoCartera(usuario.getId()));

        } catch (ExcepcionValidacion e) {
            System.err.println("Error de validación:");
            e.getErrores().forEach(err ->
                    System.err.println(" - Campo: " + err.campo() + ", Tipo: " + err.mensaje())
            );
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
