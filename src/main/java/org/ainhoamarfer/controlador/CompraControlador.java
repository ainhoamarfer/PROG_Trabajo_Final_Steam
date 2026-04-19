package org.ainhoamarfer.controlador;

import org.ainhoamarfer.excepciones.ExcepcionValidacion;
import org.ainhoamarfer.mapper.Mapper;
import org.ainhoamarfer.modelo.dtos.CompraDTO;
import org.ainhoamarfer.modelo.dtos.ErrorDTO;
import org.ainhoamarfer.modelo.entidad.BibliotecaEntidad;
import org.ainhoamarfer.modelo.entidad.CompraEntidad;
import org.ainhoamarfer.modelo.entidad.JuegoEntidad;
import org.ainhoamarfer.modelo.entidad.UsuarioEntidad;
import org.ainhoamarfer.modelo.enums.CompraEstadoEnum;
import org.ainhoamarfer.modelo.enums.CompraMetodoPagoEnum;
import org.ainhoamarfer.modelo.enums.ErrorType;
import org.ainhoamarfer.modelo.enums.UsuarioEstadoCuenta;
import org.ainhoamarfer.modelo.form.CompraForm;
import org.ainhoamarfer.repositorio.interfaz.IBibliotecaRepo;
import org.ainhoamarfer.repositorio.interfaz.ICompraRepo;
import org.ainhoamarfer.repositorio.interfaz.IJuegosRepo;
import org.ainhoamarfer.repositorio.interfaz.IUsuarioRepo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompraControlador {

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

    public CompraControlador(ICompraRepo compraRepo, IJuegosRepo juegoRepo, IUsuarioRepo usuarioRepo,  IBibliotecaRepo bibliotecaRepo) {
        this.compraRepo = compraRepo;
        this.juegoRepo = juegoRepo;
        this.usuarioRepo = usuarioRepo;
        this.bibliotecaRepo = bibliotecaRepo;
    }

    /**
     * Realizar compra
     * Descripción: Crear una nueva transacción para adquirir un juego
     *
     * @param idUsuario  ID del usuario
     * @param idJuego    ID del juego
     * @param metodoPago métod de pago (Interfaz con diferentes metodos implementados: tarjeta, saldoCartera)
     * @return CompraDTO
     * Validaciones: Usuario activo, juego comprable, no duplicado, saldo suficiente si usa cartera
     */
    public CompraDTO realizarCompra(long idUsuario, long idJuego, CompraMetodoPagoEnum metodoPago) throws ExcepcionValidacion {

        List<ErrorDTO> errores = new ArrayList<>();

        // Validar que el usuario no haya comprado ya el juego
        compraRepo.obtenerPorIdUsuario(idUsuario)
                .stream()
                .filter(compra -> compra.getJuegoId() == idJuego)
                .findFirst()
                .ifPresentOrElse(compra -> errores.add(new ErrorDTO("compra", ErrorType.COMPRA_YA_EXISTENTE)),
                        () -> {
                            JuegoEntidad juegoAAdquirir = juegoRepo.obtenerPorId(idJuego).orElseThrow(NullPointerException::new);
                            UsuarioEntidad usuarioComprador = usuarioRepo.obtenerPorId(idUsuario).orElseThrow(NullPointerException::new);

                            if (juegoAAdquirir.getPrecioBase() > usuarioComprador.getSaldoCartera() && metodoPago == CompraMetodoPagoEnum.CARTERA_STEAM) {
                                errores.add(new ErrorDTO("precioBase", ErrorType.VALOR_NO_VALIDO));
                            }
                            if (usuarioComprador.getEstadoCuenta() != UsuarioEstadoCuenta.ACTIVA) {
                                errores.add(new ErrorDTO("usuario", ErrorType.ESTADO_CUENTA));
                            }


                            if (!errores.isEmpty()) {
                                CompraForm form = new CompraForm(idUsuario, idJuego, LocalDate.now(), juegoAAdquirir.getPrecioBase(), juegoAAdquirir.getDescuentoActual(), CompraEstadoEnum.PENDIENTE, metodoPago);
                                Optional<CompraEntidad> compra = compraRepo.crear(form);
                            }
                        });

        if (!errores.isEmpty()) {
            throw new ExcepcionValidacion(errores);
        } else {
            CompraEntidad nuevaCompra = compraRepo.obtenerPorIdUsuario(idUsuario)
                    .stream()
                    .filter(compra -> compra.getJuegoId() == idJuego && compra.getUsuarioId() == idUsuario)
                    .findFirst()
                    .orElse(null);

            return Mapper.mapDeCompra(nuevaCompra);
        }

    }

    /**
     * Procesar pago
     * Descripción: Completar la transacción con el métod de pago seleccionado
     *
     * @param idCompra   ID de compra
     * @param metodoPago datos de pago según el métod
     * @return CompraDTO
     * Validaciones: Compra existe, estado válido para procesar, pago válido
     */
    public CompraDTO procesarPago(long idCompra, CompraMetodoPagoEnum metodoPago) throws ExcepcionValidacion {

        List<ErrorDTO> errores = new ArrayList<>();

        Optional<CompraEntidad> compraOpt = compraRepo.obtenerPorId(idCompra).stream().findFirst();
        CompraEntidad compra = compraOpt.orElse(null);

        if (compra == null) {
            errores.add(new ErrorDTO("Compra", ErrorType.NO_ENCONTRADO));
        }
        if (compra.getEstadoCompra() != CompraEstadoEnum.PENDIENTE) {
            errores.add(new ErrorDTO("Compra en estado realizada", ErrorType.VALOR_NO_VALIDO));
        }

        JuegoEntidad juego = juegoRepo.obtenerPorId(compra.getJuegoId()).orElseThrow(NullPointerException::new);
        UsuarioEntidad usuario = usuarioRepo.obtenerPorId(compra.getUsuarioId()).orElseThrow(NullPointerException::new);


        if (metodoPago == CompraMetodoPagoEnum.CARTERA_STEAM) {
            if(juego.getPrecioBase() > usuario.getSaldoCartera()) {
                errores.add(new ErrorDTO("Saldo insuficiente", ErrorType.SALDO_INSUFICIENTE));
            } else {
                usuarioRepo.restarSaldoCartera(compra.getUsuarioId(), juego.getPrecioBase());
                compraRepo.actualizarEstadoCompra(idCompra, CompraEstadoEnum.COMPLETADA);
            }
        }
        if(metodoPago == CompraMetodoPagoEnum.PAYPAL) {

        }
        if (metodoPago == CompraMetodoPagoEnum.TARJETA_CREDITO) {

        }
        if (metodoPago == CompraMetodoPagoEnum.TRANSFERENCIA) {

        }

        if (!errores.isEmpty()) {
            throw new ExcepcionValidacion(errores);
        }

        return Mapper.mapDeCompra(compra);
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

        return Mapper.mapDeCompra(compra);
    }

    /**
     * Solicitar reembolso
     * Descripción: Devolver una compra y reintegrar el dinero a la cartera
     *
     * @param idCompra ID de compra
     * @param motivo   motivo del reembolso
     * @return CompraDTO
     * Validaciones: Compra completada, dentro del plazo, pocas horas jugadas
     */
    public CompraDTO solicitarReembolso(long idCompra, String motivo) throws ExcepcionValidacion {
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
            throw  new ExcepcionValidacion(errores);
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

        double importeReembolso = compra.getPrecioFinal();
        double nuevoSaldo = usuario.getSaldoCartera() + importeReembolso;

        usuarioRepo.sumarSaldoCartera(usuario.getId(), nuevoSaldo);

        //compra a REEMBOLSADA
        compraRepo.actualizarEstadoCompra(idCompra, CompraEstadoEnum.REEMBOLSADA);


        CompraEntidad compraReembolsada = compraRepo.obtenerPorId(idCompra)
                .orElse(compra);

        return Mapper.mapDeCompra(compraReembolsada);
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
