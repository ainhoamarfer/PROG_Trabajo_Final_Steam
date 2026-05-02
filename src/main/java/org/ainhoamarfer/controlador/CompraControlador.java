package org.ainhoamarfer.controlador;

import jakarta.transaction.TransactionManager;
import org.ainhoamarfer.excepciones.ExcepcionValidacion;
import org.ainhoamarfer.mapper.Mapper;
import org.ainhoamarfer.modelo.dtos.CompraDTO;
import org.ainhoamarfer.modelo.dtos.ErrorDTO;
import org.ainhoamarfer.modelo.dtos.JuegoDTO;
import org.ainhoamarfer.modelo.dtos.UsuarioDTO;
import org.ainhoamarfer.modelo.entidad.BibliotecaEntidad;
import org.ainhoamarfer.modelo.entidad.CompraEntidad;
import org.ainhoamarfer.modelo.entidad.JuegoEntidad;
import org.ainhoamarfer.modelo.entidad.UsuarioEntidad;
import org.ainhoamarfer.modelo.enums.*;
import org.ainhoamarfer.modelo.form.BibliotecaForm;
import org.ainhoamarfer.modelo.form.CompraForm;
import org.ainhoamarfer.modelo.form.JuegoForm;
import org.ainhoamarfer.modelo.form.UsuarioForm;
import org.ainhoamarfer.repositorio.implementacion_memoria.BibliotecaRepo;
import org.ainhoamarfer.repositorio.implementacion_memoria.UsuarioRepo;
import org.ainhoamarfer.repositorio.implementacion_memoria.CompraRepo;
import org.ainhoamarfer.repositorio.implementacion_memoria.JuegoRepo;
import org.ainhoamarfer.repositorio.interfaz.IBibliotecaRepo;
import org.ainhoamarfer.repositorio.interfaz.ICompraRepo;
import org.ainhoamarfer.repositorio.interfaz.IJuegosRepo;
import org.ainhoamarfer.repositorio.interfaz.IUsuarioRepo;
import org.ainhoamarfer.transaction.ITransactionManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompraControlador {
    public static final int MAX_DESCUENTO = 100;
    public static final int MIN_DESCUENTO = 0;

    /*
    Realizar compra
    Procesar pago
    Consultar historial de compras (Ficheros)
    Consultar detalles de compra
    Solicitar reembolso
    Generar factura (Ficheros)
     */

    private ICompraRepo compraRepo;
    private IJuegosRepo juegoRepo;
    private IUsuarioRepo usuarioRepo;
    private IBibliotecaRepo bibliotecaRepo;

    public CompraControlador(ICompraRepo compraRepo, IJuegosRepo juegoRepo, IUsuarioRepo usuarioRepo, IBibliotecaRepo bibliotecaRepo) {
        this.compraRepo = compraRepo;
        this.juegoRepo = juegoRepo;
        this.usuarioRepo = usuarioRepo;
        this.bibliotecaRepo = bibliotecaRepo;
    }

    static void main() throws ExcepcionValidacion {
        ICompraRepo compraRepo = new CompraRepo();
        IJuegosRepo juegoRepo = new JuegoRepo();
        IUsuarioRepo usuarioRepo = new UsuarioRepo();
        IBibliotecaRepo bibliotecaRepo = new BibliotecaRepo();

        CompraControlador comContr = new CompraControlador(compraRepo, juegoRepo, usuarioRepo, bibliotecaRepo);
        UsuarioControlador usuarioControlador = new UsuarioControlador(usuarioRepo);
        JuegosControlador juegosControlador = new JuegosControlador(juegoRepo);

        UsuarioForm usuformValido = new UsuarioForm(
                "Ainhoa", "ainhoa3mf@gmail.com", "12A.%Kacefefg", "Ainhoa Mar",
                "España", LocalDate.of(1990, 1, 1), LocalDate.now(), "avatar.jpg", 100.0, UsuarioEstadoCuenta.ACTIVA
        );

        JuegoForm juegoFormValido = new JuegoForm(
                "The Witcher 3",
                "Un juego de rol de acción",
                "CD Projekt Red",
                LocalDate.of(2015, 5, 19),
                59.99,
                50,
                "RPG",
                "Español,Inglés",
                JuegoClasificacionEdad.PEGI_18,
                JuegoEstado.DISPONIBLE
        );

        UsuarioDTO usuario = usuarioControlador.registrarNuevoUsuario(usuformValido);
        JuegoDTO juego = juegosControlador.anadirJuego(juegoFormValido);

        System.out.println(usuarioRepo.obtenerPorId(1L));


        //CompraDTO compra = comContr.realizarCompra(new CompraForm(usuario.getId(), juego.getId(), LocalDate.now(), juego.getPrecioBase(), juego.getDescuentoActual(), CompraEstadoEnum.PENDIENTE, CompraMetodoPagoEnum.CARTERA_STEAM));

        //System.out.println(compra.toString());
        //System.out.println(usuario.getNombreUsuario());
        //System.out.println(juego.getTitulo());
        //System.out.println(compra.getFechaCompra());
        //System.out.println(compra.getPorcentajeDescuento() + "%");
    }//

    /**
     * Realizar compra
     * Descripción: Crear una nueva transacción para adquirir un juego
     *
     * @param form  formulario con los datos de la compra
     * @return CompraDTO
     * Validaciones: Usuario activo, juego comprable, no duplicado, saldo suficiente si usa cartera
     */
    public CompraDTO realizarCompra(CompraForm form) throws ExcepcionValidacion {

        List<ErrorDTO> errores = new ArrayList<>();

        int descuento = form.getDescuentoActual();
        if (descuento < MIN_DESCUENTO || descuento > MAX_DESCUENTO || form.getPrecioBase() < 0 || form.getMetodoPago() == null) {
            errores.add(new ErrorDTO("descuentoActual", ErrorType.VALOR_NO_VALIDO));
        }

        Optional<JuegoEntidad> juegoAAdquirirOpt = juegoRepo.obtenerPorId(form.getJuegoId());
        Optional<UsuarioEntidad> usuarioCompradorOpt = usuarioRepo.obtenerPorId(form.getUsuarioId());

        if (juegoAAdquirirOpt.isEmpty()) errores.add(new ErrorDTO("juego", ErrorType.NO_ENCONTRADO));
        if (usuarioCompradorOpt.isEmpty()) errores.add(new ErrorDTO("usuario", ErrorType.NO_ENCONTRADO));

        // Validar que el usuario no haya comprado ya el juego
        compraRepo.obtenerPorIdUsuario(form.getUsuarioId())
                .stream()
                .filter(compra -> compra.getJuegoId() == form.getJuegoId())
                .findFirst()
                .ifPresent(compra -> errores.add(new ErrorDTO("compra", ErrorType.COMPRA_YA_EXISTENTE)));

        if (!errores.isEmpty()) throw new ExcepcionValidacion(errores);

        JuegoEntidad juegoAAdquirir =  juegoAAdquirirOpt.get();
        UsuarioEntidad usuarioComprador = usuarioCompradorOpt.get();

        if (juegoAAdquirir.getEstado() == JuegoEstado.NO_DISPONIBLE) errores.add(new ErrorDTO("juego", ErrorType.NO_DISPONIBLE));

        if (usuarioComprador.getEstadoCuenta() != UsuarioEstadoCuenta.ACTIVA) errores.add(new ErrorDTO("usuario", ErrorType.ESTADO_CUENTA));

        if (juegoAAdquirir.getDescuentoActual() > MAX_DESCUENTO || juegoAAdquirir.getDescuentoActual() < MIN_DESCUENTO) errores.add(new ErrorDTO("descuentoActual", ErrorType.VALOR_NO_VALIDO));

        if (form.getMetodoPago() == CompraMetodoPagoEnum.CARTERA_STEAM) {
            if (form.getPrecioBase() > usuarioComprador.getSaldoCartera()) {
                errores.add(new ErrorDTO("saldo", ErrorType.SALDO_INSUFICIENTE));
            }
        }

        if (!errores.isEmpty()) throw new ExcepcionValidacion(errores);

        CompraForm formNuevaCompra = new CompraForm(form.getUsuarioId(), form.getJuegoId(), LocalDate.now(), juegoAAdquirir.getPrecioBase(), juegoAAdquirir.getDescuentoActual(), CompraEstadoEnum.PENDIENTE, form.getMetodoPago());
        Optional<CompraEntidad> compraOpt = compraRepo.crear(form);
        if (compraOpt.isEmpty()) {
            errores.add(new ErrorDTO("compra", ErrorType.NO_ENCONTRADO));
            throw new ExcepcionValidacion(errores);
        }

        CompraEntidad compra = compraOpt.get();

        return Mapper.mapDeCompra(compra, Mapper.mapDeUsuario(usuarioComprador), Mapper.mapDeJuego(juegoAAdquirir));
    }

    /**
     * Procesar pago
     * Descripción: Completar la transacción con el métod de pago seleccionado
     *
     * @param idCompra   ID de compra
     * @return CompraDTO
     * Validaciones: Compra existe, estado válido para procesar, pago válido
     */
    public CompraDTO procesarPago(long idCompra) throws ExcepcionValidacion {

        List<ErrorDTO> errores = new ArrayList<>();

        Optional<CompraEntidad> compraOpt = compraRepo.obtenerPorId(idCompra);
        if (compraOpt.isEmpty()) {
            errores.add(new ErrorDTO("Compra", ErrorType.NO_ENCONTRADO));
            throw new ExcepcionValidacion(errores);
        }

        CompraEntidad compra = compraOpt.orElse(null);

        if (compra.getEstadoCompra() != CompraEstadoEnum.PENDIENTE || compra.getEstadoCompra() == null) {
            errores.add(new ErrorDTO("Compra en estado realizada", ErrorType.VALOR_NO_VALIDO));
        }
        if (!errores.isEmpty()) {
            throw new ExcepcionValidacion(errores);
        }

        JuegoEntidad juego = juegoRepo.obtenerPorId(compra.getJuegoId()).orElseThrow(NullPointerException::new);
        UsuarioEntidad usuario = usuarioRepo.obtenerPorId(compra.getUsuarioId()).orElseThrow(NullPointerException::new);

        double precioFinal = compra.getPrecioBase() * (100 - compra.getPorcentajeDescuento()) / 100;


        if (compra.getMetodoPago() == CompraMetodoPagoEnum.CARTERA_STEAM) {
            if (precioFinal > usuario.getSaldoCartera()) {
                errores.add(new ErrorDTO("saldo", ErrorType.SALDO_INSUFICIENTE));
                throw new ExcepcionValidacion(errores);
            }
            usuarioRepo.restarSaldoCartera(usuario.getId(), precioFinal);
        }
        if (compra.getMetodoPago() == CompraMetodoPagoEnum.PAYPAL) {

        }
        if (compra.getMetodoPago() == CompraMetodoPagoEnum.TARJETA_CREDITO) {

        }
        if (compra.getMetodoPago() == CompraMetodoPagoEnum.TRANSFERENCIA) {

        }

        if (!errores.isEmpty()) {
            throw new ExcepcionValidacion(errores);
        }

        compraRepo.actualizarEstadoCompra(idCompra, CompraEstadoEnum.COMPLETADA);

        BibliotecaForm biblioForm = new BibliotecaForm(usuario.getId(), juego.getId(), LocalDate.now(), 0.0, null, false);
        bibliotecaRepo.crear(biblioForm);

        UsuarioEntidad usuarioMap = usuarioRepo.obtenerPorId(compra.getUsuarioId()).orElse(null);
        JuegoEntidad juegoMap = juegoRepo.obtenerPorId(compra.getJuegoId()).orElse(null);
        CompraEntidad compraCompletada = compraRepo.obtenerPorId(idCompra).orElseThrow();

        return Mapper.mapDeCompra(compraCompletada, Mapper.mapDeUsuario(usuarioMap), Mapper.mapDeJuego(juegoMap));
    }


    /**
     * Consultar historial de compras (Ficheros)
     * Descripción: Ver todas las compras realizadas por un usuario
     *
     * @param idUsuario    ID del usuario
     * @param filtroEstado filtro de estado opcional
     * @param fechaInicio  rango de fechas opcional
     * @param fechaFin     rango de fechas opcional
     * @return Lista de compras con información resumida y total gastado
     * Datos mostrados: Fecha, juego, precio sin descuento, descuento aplicado, métod de pago, estado
     */
    public List<CompraDTO> consultarHistorialCompras(long idUsuario, String filtroEstado, LocalDate fechaInicio, LocalDate fechaFin) {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Consultar detalles de compra
     * Descripción: Ver información completa de una transacción específica
     *
     * @param idCompra  ID de compra
     * @param idUsuario ID del usuario (para verificar pertenencia)
     * @return CompraDTO
     */
    public CompraDTO consultarDetallesCompra(long idCompra, long idUsuario) throws ExcepcionValidacion {
        List<ErrorDTO> errores = new ArrayList<>();

        CompraEntidad compra = compraRepo.obtenerPorIdUsuarioYIdCompra(idUsuario, idCompra)
                .orElseThrow(() -> {
                    errores.add(new ErrorDTO("compra", ErrorType.NO_ENCONTRADO));
                    return new ExcepcionValidacion(errores);
                });

        UsuarioEntidad usuarioMap = usuarioRepo.obtenerPorId(compra.getUsuarioId()).orElse(null);
        JuegoEntidad juegoMap = juegoRepo.obtenerPorId(compra.getJuegoId()).orElse(null);

        return Mapper.mapDeCompra(compra, Mapper.mapDeUsuario(usuarioMap), Mapper.mapDeJuego(juegoMap));
    }

    /**
     * Solicitar reembolso
     * Descripción: Devolver una compra y reintegrar el dinero a la cartera
     *
     * @param idCompra ID de compra

     * @return CompraDTO
     * Validaciones: Compra completada, dentro del plazo, pocas horas jugadas
     */
    public CompraDTO solicitarReembolso(long idCompra) throws ExcepcionValidacion {
        List<ErrorDTO> errores = new ArrayList<>();

        CompraEntidad compra = compraRepo.obtenerPorIdUsuario(idCompra)
                .stream()
                .findFirst()
                .orElseThrow(() -> {
                    errores.add(new ErrorDTO("compra", ErrorType.NO_ENCONTRADO));
                    return new ExcepcionValidacion(errores);
                });

        //Ver si Compra completada
        if (compra.getEstadoCompra() != CompraEstadoEnum.COMPLETADA) {
            errores.add(new ErrorDTO("La compra aun no se completo, no puedes solicitar reenvolso", ErrorType.VALOR_NO_VALIDO));
            throw new ExcepcionValidacion(errores);
        }
        //dentro del plazo
        LocalDate fechaCompra = compra.getFechaCompra();
        LocalDate fechaLimite = fechaCompra.plusDays(20);
        if (LocalDate.now().isAfter(fechaLimite)) {
            errores.add(new ErrorDTO("plazoReembolso", ErrorType.PLAZO_REEMBOLSO_VENCIDO));
            throw new ExcepcionValidacion(errores);
        }

        //horas jugadas (menos de 2), para esto hay que consultar la biblioteca del usuario y ver el tiempo de juego registrado para ese juego
        BibliotecaEntidad biblioteca = bibliotecaRepo
                .obtenerPorIdUsuarioYIdJuego(compra.getUsuarioId(), compra.getJuegoId())
                .orElseThrow(() -> {
                    errores.add(new ErrorDTO("biblioteca", ErrorType.NO_ENCONTRADO));
                    return new ExcepcionValidacion(errores);
                });

        if (biblioteca.getTiempoJuego() >= 2.0) {
            errores.add(new ErrorDTO("tiempoJuego", ErrorType.TIEMPO_EXPIRADO));
            throw new ExcepcionValidacion(errores);
        }

        if (!errores.isEmpty()) {
            throw new ExcepcionValidacion(errores);
        }

        // PUEDE COMENZAR EL REENVOLSO ----------------------------------------------------------------------

        //Devolver el dinero a la cartera del usuario
        UsuarioEntidad usuario = usuarioRepo.obtenerPorId(compra.getUsuarioId())
                .orElseThrow(() -> new ExcepcionValidacion(List.of(new ErrorDTO("usuario", ErrorType.NO_ENCONTRADO))));

        double importeReembolso = compra.getPrecioBase() * (100 - compra.getPorcentajeDescuento()) / 100;
        double nuevoSaldo = usuario.getSaldoCartera() + importeReembolso;

        usuarioRepo.sumarSaldoCartera(usuario.getId(), nuevoSaldo);

        //compra a REEMBOLSADA
        compraRepo.actualizarEstadoCompra(idCompra, CompraEstadoEnum.REEMBOLSADA);


        CompraEntidad compraReembolsada = compraRepo.obtenerPorId(idCompra)
                .orElse(compra);

        UsuarioEntidad usuarioMap = usuarioRepo.obtenerPorId(compraReembolsada.getUsuarioId()).orElse(null);
        JuegoEntidad juegoMap = juegoRepo.obtenerPorId(compraReembolsada.getJuegoId()).orElse(null);

        return Mapper.mapDeCompra(compraReembolsada, Mapper.mapDeUsuario(usuarioMap), Mapper.mapDeJuego(juegoMap));
    }

    /**
     * Generar factura (Ficheros)
     * Descripción: Crear un comprobante de compra en formato imprimible
     *
     * @param idCompra ID de compra
     * @return Archivo txt de factura o mensaje de error
     * Validaciones: Compra completada
     */
    public String generarFactura(long idCompra) {
        throw new UnsupportedOperationException("Not implemented");
    }

}
