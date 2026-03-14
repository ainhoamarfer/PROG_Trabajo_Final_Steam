package org.ainhoamarfer.controlador;

import org.ainhoamarfer.excepciones.ExcepcionValidacion;
import org.ainhoamarfer.modelo.dtos.CompraDTO;
import org.ainhoamarfer.modelo.dtos.ErrorDTO;
import org.ainhoamarfer.modelo.entidad.CompraEntidad;
import org.ainhoamarfer.modelo.entidad.JuegoEntidad;
import org.ainhoamarfer.modelo.entidad.UsuarioEntidad;
import org.ainhoamarfer.modelo.enums.CompraEstadoEnum;
import org.ainhoamarfer.modelo.enums.CompraMetodoPagoEnum;
import org.ainhoamarfer.modelo.enums.ErrorType;
import org.ainhoamarfer.modelo.enums.UsuarioEstadoCuenta;
import org.ainhoamarfer.modelo.form.CompraForm;
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

    public CompraControlador(ICompraRepo compraRepo, IJuegosRepo juegoRepo, IUsuarioRepo usuarioRepo) {
        this.compraRepo = compraRepo;
        this.juegoRepo = juegoRepo;
        this.usuarioRepo = usuarioRepo;
    }

    /**
     * Realizar compra
     * Descripción: Crear una nueva transacción para adquirir un juego
     *
     * @param idUsuario  ID del usuario
     * @param idJuego    ID del juego
     * @param metodoPago métod de pago (Interfaz con diferentes metodos implementados: tarjeta, saldoCartera)
     * @return ID de compra creada o mensaje de error
     * Validaciones: Usuario activo, juego comprable, no duplicado, saldo suficiente si usa cartera
     */
    public String realizarCompra(long idUsuario, long idJuego, CompraMetodoPagoEnum metodoPago) throws ExcepcionValidacion {

        List<ErrorDTO> errores = new ArrayList<>();


        Optional<CompraEntidad> compraExistente = compraRepo.obtenerPorIdUsuario(idUsuario)
                .stream()
                .filter(compra -> compra.getJuegoId() == idJuego)
                .findFirst();

        compraExistente.ifPresent(compra ->
                errores.add(new ErrorDTO("compra", ErrorType.COMPRA_YA_EXISTENTE))
        );

        JuegoEntidad juegoAAdquirir = juegoRepo.obtenerPorId(idJuego).orElse(null);
        if (juegoAAdquirir == null) {
            errores.add(new ErrorDTO("juegoId", ErrorType.NO_ENCONTRADO));

            UsuarioEntidad usuario = usuarioRepo.obtenerPorId(idUsuario).orElse(null);
            if (usuario == null || !usuario.getEstadoCuenta() == UsuarioEstadoCuenta.SUSPENDIDA) {
                errores.add(new ErrorDTO("usuario", ErrorType.USUARIO_INVALIDO));
                return errores.toString();
            }

            if (!errores.isEmpty()) {
                throw new ExcepcionValidacion(errores);
            } else {

                CompraForm form = new CompraForm(idUsuario, idJuego, juegoAAdquirir.getPrecioBase(), juegoAAdquirir.getDescuentoActual(), CompraEstadoEnum.PENDIENTE, metodoPago);
                boolean saldoSuficiente = usuarioRepo.comprobarSiSaldoCarteraSuficiente(idUsuario, juegoAAdquirir.getPrecioBase() - juegoAAdquirir.getDescuentoActual());
               if(saldoSuficiente){

               }

            }

            throw new UnsupportedOperationException("Not implemented");
        }
        return "rrggrgrgr";
    }

    /**
     * Procesar pago
     * Descripción: Completar la transacción con el métod de pago seleccionado
     *
     * @param idCompra  ID de compra
     * @param metodoPago datos de pago según el métod
     * @return Confirmación de pago o mensaje de error
     * Validaciones: Compra existe, estado válido para procesar, pago válido
     */
    public String procesarPago (long idCompra, CompraMetodoPagoEnum metodoPago) {

        List<ErrorDTO> errores = new ArrayList<>();

        Optional<CompraEntidad> compraOpt = compraRepo.obtenerPorId(idCompra);
        CompraEntidad compra = compraOpt.orElse(null);
        JuegoEntidad juego = juegoRepo.obtenerPorId(compra.getJuegoId()).orElse(null);
        boolean saldoSuficiente = usuarioRepo.comprobarSiSaldoCarteraSuficiente(compra.getUsuarioId(), compra.getPrecioSinDes());
        if (compra == null) {
            errores.add(new ErrorDTO("compraId", ErrorType.NO_ENCONTRADO));
        } else if (compra.getEstado() != CompraEstadoEnum.PENDIENTE) {
            errores.add(new ErrorDTO("compra", ErrorType.VALOR_NO_VALIDO));
        }

        if (!errores.isEmpty()) {
            throw new ExcepcionValidacion(errores);
        }

        if (metodoPago == CompraMetodoPagoEnum.CARTERA_STEAM) {


            usuarioRepo.actualizarSaldoCartera(compra.getUsuarioId(), juego.getPrecioBase());
                compraRepo.actualizarEstadoCompra(idCompra, CompraEstadoEnum.COMPLETADA);

        } else if (metodoPago == CompraMetodoPagoEnum.CARTERA_STEAM) {
            errores.add(new ErrorDTO("saldoCartera", ErrorType.SALDO_INSUFICIENTE));
            throw new ExcepcionValidacion(errores);
        } else if (metodoPago == CompraMetodoPagoEnum.TARJETA_CREDITO) {
            Optional<CompraEntidad> compraOpt = compraRepo.crear(form);
            CompraEntidad compra = compraOpt.orElse(null);
        }

        throw new UnsupportedOperationException("Not implemented");
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
     * @return Información detallada de la compra o compra no encontrada
     * Datos mostrados: Todos los campos de compra, información del juego, factura/recibo
     */
    public CompraDTO consultarDetallesCompra(long idCompra, long idUsuario) {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Solicitar reembolso
     * Descripción: Devolver una compra y reintegrar el dinero a la cartera
     *
     * @param idCompra ID de compra
     * @param motivo   motivo del reembolso
     * @return Confirmación de reembolso con nuevo saldo o mensaje de denegación
     * Validaciones: Compra completada, dentro del plazo, pocas horas jugadas
     */
    public String solicitarReembolso(long idCompra, String motivo) {
        throw new UnsupportedOperationException("Not implemented");
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
