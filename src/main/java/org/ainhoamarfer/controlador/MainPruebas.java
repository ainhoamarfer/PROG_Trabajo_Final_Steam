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
import org.ainhoamarfer.repositorio.implementacion_memoria.*;
import org.ainhoamarfer.repositorio.interfaz.*;
import org.ainhoamarfer.transaction.ITransactionManager;
import org.ainhoamarfer.transaction.NoOpTransactionManager;

import java.time.LocalDate;

public class MainPruebas {

    public static void main(String[] args) {


        IUsuarioRepo usuarioRepo = new UsuarioRepo();
        IJuegosRepo juegoRepo = new JuegoRepo();
        ICompraRepo compraRepo = new CompraRepo();
        IBibliotecaRepo bibliotecaRepo = new BibliotecaRepo();
        IResenaRepo resenaRepo = new ResenaRepo();

        ITransactionManager tm = new NoOpTransactionManager();

        // Controladores con los repositorios y tm
        UsuarioControlador usuarioControlador = new UsuarioControlador(usuarioRepo, tm);
        JuegosControlador juegosControlador = new JuegosControlador(juegoRepo, tm);
        CompraControlador compraControlador = new CompraControlador(compraRepo, juegoRepo, usuarioRepo, bibliotecaRepo, tm);

        try {

            UsuarioForm usuarioForm = new UsuarioForm(
                    "testUser",
                    "test@steam.com",
                    "Abc123456",
                    "Test User",
                    "España",
                    LocalDate.of(1990, 1, 1),
                    null,
                    "avatar.png",
                    0.0,
                    UsuarioEstadoCuenta.ACTIVA
            );

            System.out.println("-------------------------------CREAR USUARIO--------------------------------------------");
            UsuarioDTO usuario = usuarioControlador.registrarNuevoUsuario(usuarioForm);
            System.out.println("Usuario creado: " + usuario.getNombreUsuario() + " (ID: " + usuario.getId() + ", Saldo: " + usuario.getSaldoCartera() + ")");
            System.out.println("----------------------------------Añadir saldo-----------------------------------------");

            double nuevoSaldo = usuarioControlador.anadirSaldoCartera(100.0, usuario.getId());
            System.out.println("Saldo después de añadir: " + nuevoSaldo);
            System.out.println("----------------------------------Crear juego-----------------------------------------");

            JuegoForm juegoForm = new JuegoForm(
                    "The Legend of Testing",
                    "Un juego de prueba para validar compras.",
                    "TestDev",
                    LocalDate.now().minusDays(5),
                    29.99,
                    10,
                    "Aventura",
                    "Español, Inglés",
                    JuegoClasificacionEdad.PEGI_12,
                    JuegoEstado.DISPONIBLE
            );

            JuegoDTO juego = juegosControlador.anadirJuego(juegoForm);
            System.out.println("Juego añadido: " + juego.getTitulo() +
                    " (ID: " + juego.getId() + ", Precio base: " + juego.getPrecioBase() +
                    ", Descuento: " + juego.getDescuentoActual() + "%)");
            System.out.println("----------------------------------Realizar compra-----------------------------------------");

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
                    ", Precio base: " + compraRealizada.getPrecioBase());
            System.out.println("-----------------------------------Procesar pago----------------------------------------");

            CompraDTO compraCompletada = compraControlador.procesarPago(compraRealizada.getId());
            double saldoTrasCompra = usuarioControlador.consultarSaldoCartera(usuario.getId());
            System.out.println("Pago procesado. Estado: " + compraCompletada.getEstadoCompra() +
                    ", Saldo usuario: " + saldoTrasCompra);
            System.out.println("---------------------------------Consultar detalles de compra------------------------------------------");

            CompraDTO detalles = compraControlador.consultarDetallesCompra(compraCompletada.getId(), usuario.getId());
            System.out.println("Detalles: Juego=" + detalles.getJuego().getTitulo() +
                    ", Fecha=" + detalles.getFechaCompra() +
                    ", Método=" + detalles.getMetodoPago());
            System.out.println("----------------------------------Solicitar reembolso-----------------------------------------");

            // 6. Solicitar reembolso (dentro de plazo, horas jugadas = 0)
            CompraDTO compraReembolsada = compraControlador.solicitarReembolso(compraCompletada.getId());
            double saldoTrasReembolso = usuarioControlador.consultarSaldoCartera(usuario.getId());
            System.out.println("Reembolso solicitado. Estado: " + compraReembolsada.getEstadoCompra() +
                    ", Saldo usuario: " + saldoTrasReembolso);
            System.out.println("---------------------------------------------------------------------------");
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
