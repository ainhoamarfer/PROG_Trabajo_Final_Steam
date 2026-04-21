package org.ainhoamarfer.controlador;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.ainhoamarfer.excepciones.ExcepcionValidacion;
import org.ainhoamarfer.mapper.Mapper;
import org.ainhoamarfer.modelo.dtos.CompraDTO;
import org.ainhoamarfer.modelo.dtos.ErrorDTO;
import org.ainhoamarfer.modelo.entidad.CompraEntidad;
import org.ainhoamarfer.modelo.entidad.JuegoEntidad;
import org.ainhoamarfer.modelo.entidad.UsuarioEntidad;
import org.ainhoamarfer.modelo.enums.CompraEstadoEnum;
import org.ainhoamarfer.modelo.enums.CompraMetodoPagoEnum;
import org.ainhoamarfer.modelo.enums.ErrorType;
import org.ainhoamarfer.modelo.enums.JuegoClasificacionEdad;
import org.ainhoamarfer.modelo.enums.UsuarioEstadoCuenta;
import org.ainhoamarfer.modelo.form.CompraForm;
import org.ainhoamarfer.repositorio.interfaz.ICompraRepo;
import org.ainhoamarfer.repositorio.interfaz.IJuegosRepo;
import org.ainhoamarfer.repositorio.interfaz.IUsuarioRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

//@ExtendWith(MockitoExtension.class)
//public class CompraControladorTest {

    //@Mock
    //private ICompraRepo compraRepo;
//
    //@Mock
    //private IJuegosRepo juegoRepo;
//
    //@Mock
    //private IUsuarioRepo usuarioRepo;
//
    //@InjectMocks
    //private CompraControlador controlador;
//
    //private final long idUsuario = 1L;
    //private final long idJuego = 100L;
    //private final long idCompra = 10L;
    //private final double precioOriginal = 49.99;
    //private final double porcentajeDescuento = 10.0;
    //private final double precioFinal = precioOriginal * (1 - porcentajeDescuento / 100.0); // 44.99 aprox
    //private final double saldoCartera = 100.0;
    //private final LocalDate fecha = LocalDate.now();
//
    //private UsuarioEntidad usuario;
    //private JuegoEntidad juego;
    //private CompraEntidad compraPendiente;
    //private CompraDTO compraDTO;
//
    //@BeforeEach
    //void setUp() {
    //    usuario = new UsuarioEntidad(
    //            idUsuario,
    //            "usuarioTest",
    //            "test@email.com",
    //            "Password1",
    //            "Nombre Real",
    //            "España",
    //            LocalDate.of(1990, 1, 1),
    //            LocalDate.of(2020, 1, 1),
    //            "avatar.jpg",
    //            saldoCartera
    //    );
//
    //    juego = new JuegoEntidad(
    //            idJuego,
    //            "Juego Test",
    //            "Descripción del juego",
    //            "Desarrollador Test",
    //            LocalDate.of(2023, 5, 10),
    //            precioOriginal,
    //            porcentajeDescuento,
    //            "Aventura",
    //            "Español, Inglés",
    //            JuegoClasificacionEdad.PEGI_12
    //    );
//
    //    compraPendiente = new CompraEntidad(
    //            idCompra,
    //            idUsuario,
    //            idJuego,
    //            fecha,
    //            precioOriginal,
    //            porcentajeDescuento,
    //            CompraMetodoPagoEnum.CARTERA_STEAM
    //    );
//
    //    compraDTO = new CompraDTO(
    //            idCompra,
    //            idUsuario,
    //            usuario,
    //            idJuego,
    //            juego,
    //            fecha,
    //            precioFinal,
    //            porcentajeDescuento,
    //            precioOriginal,
    //            CompraEstadoEnum.PENDIENTE,
    //            CompraMetodoPagoEnum.CARTERA_STEAM
    //    );
    //}

    // -----------------------------------------------------------------
    // Tests para realizarCompra
    // -----------------------------------------------------------------

    //@Test
    //void realizarCompra_CuandoUsuarioActivoYConSaldoSuficiente_DeberiaCrearCompraYRetornarDTO() throws ExcepcionValidacion {
    //    when(compraRepo.obtenerPorIdUsuario(idUsuario)).thenReturn(Optional.empty());
    //    when(juegoRepo.obtenerPorId(idJuego)).thenReturn(Optional.of(juego));
    //    when(usuarioRepo.obtenerPorId(idUsuario)).thenReturn(Optional.of(usuario));
    //    when(compraRepo.crear(any(CompraForm.class))).thenReturn(Optional.of(compraPendiente));
    //    when(compraRepo.obtenerPorIdUsuario(idUsuario)).thenReturn(Optional.of(compraPendiente));
//
    //    try (MockedStatic<Mapper> mockedMapper = mockStatic(Mapper.class)) {
    //        mockedMapper.when(() -> Mapper.mapDeCompra(compraPendiente)).thenReturn(compraDTO);
//
    //        CompraDTO resultado = controlador.realizarCompra(idUsuario, idJuego, CompraMetodoPagoEnum.CARTERA_STEAM);
//
    //        assertNotNull(resultado);
    //        assertEquals(idCompra, resultado.getId());
    //        assertEquals(precioFinal, resultado.getPrecioFinal()); // Verificar precio con descuento
    //        verify(compraRepo).crear(any(CompraForm.class));
    //    }
    //}
//
    //@Test
    //void realizarCompra_CuandoUsuarioYaComproElJuego_DeberiaLanzarExcepcionValidacion() {
    //    CompraEntidad existente = new CompraEntidad(
    //            99L, idUsuario, idJuego, fecha, precioOriginal, porcentajeDescuento, CompraMetodoPagoEnum.CARTERA_STEAM
    //    );
    //    when(compraRepo.obtenerPorIdUsuario(idUsuario)).thenReturn(Optional.of(existente));
//
    //    ExcepcionValidacion ex = assertThrows(ExcepcionValidacion.class,
    //            () -> controlador.realizarCompra(idUsuario, idJuego, CompraMetodoPagoEnum.CARTERA_STEAM));
//
    //    List<ErrorDTO> errores = ex.getErrores();
    //    assertEquals(1, errores.size());
    //    assertEquals("compra", errores.get(0).campo());
    //    assertEquals(ErrorType.COMPRA_YA_EXISTENTE, errores.get(0).mensaje());
//
    //    verify(compraRepo, never()).crear(any());
    //}
//
    //@Test
    //void realizarCompra_CuandoSaldoInsuficienteYMetodoCartera_DeberiaLanzarExcepcionValidacion() {
    //    UsuarioEntidad usuarioPobre = new UsuarioEntidad(
    //            idUsuario, "user", "test@mail.com", "Pass1", "Name", "España",
    //            LocalDate.of(1990,1,1), LocalDate.now(), "avatar.jpg", 10.0
    //    );
    //    when(compraRepo.obtenerPorIdUsuario(idUsuario)).thenReturn(Optional.empty());
    //    when(juegoRepo.obtenerPorId(idJuego)).thenReturn(Optional.of(juego));
    //    when(usuarioRepo.obtenerPorId(idUsuario)).thenReturn(Optional.of(usuarioPobre));
    //    when(compraRepo.crear(any(CompraForm.class))).thenReturn(Optional.of(compraPendiente));
//
    //    ExcepcionValidacion ex = assertThrows(ExcepcionValidacion.class,
    //            () -> controlador.realizarCompra(idUsuario, idJuego, CompraMetodoPagoEnum.CARTERA_STEAM));
//
    //    List<ErrorDTO> errores = ex.getErrores();
    //    assertEquals(1, errores.size());
    //    assertEquals("precioBase", errores.get(0).campo());
    //    assertEquals(ErrorType.VALOR_NO_VALIDO, errores.get(0).mensaje());
//
    //    verify(compraRepo).crear(any(CompraForm.class));
    //}
//
    //@Test
    //void realizarCompra_CuandoUsuarioNoActivo_DeberiaLanzarExcepcionValidacion() {
    //    UsuarioEntidad usuarioSuspendido = mock(UsuarioEntidad.class);
    //    when(usuarioSuspendido.getSaldoCartera()).thenReturn(saldoCartera);
    //    when(usuarioSuspendido.getEstadoCuenta()).thenReturn(UsuarioEstadoCuenta.SUSPENDIDA);
//
    //    when(compraRepo.obtenerPorIdUsuario(idUsuario)).thenReturn(Optional.empty());
    //    when(juegoRepo.obtenerPorId(idJuego)).thenReturn(Optional.of(juego));
    //    when(usuarioRepo.obtenerPorId(idUsuario)).thenReturn(Optional.of(usuarioSuspendido));
    //    when(compraRepo.crear(any(CompraForm.class))).thenReturn(Optional.of(compraPendiente));
//
    //    ExcepcionValidacion ex = assertThrows(ExcepcionValidacion.class,
    //            () -> controlador.realizarCompra(idUsuario, idJuego, CompraMetodoPagoEnum.CARTERA_STEAM));
//
    //    List<ErrorDTO> errores = ex.getErrores();
    //    assertEquals(1, errores.size());
    //    assertEquals("usuario", errores.get(0).campo());
    //    assertEquals(ErrorType.ESTADO_CUENTA, errores.get(0).mensaje());
//
    //    verify(compraRepo).crear(any(CompraForm.class));
    //}
//
    //@Test
    //void realizarCompra_CuandoJuegoNoExiste_DeberiaLanzarNullPointerException() {
    //    when(compraRepo.obtenerPorIdUsuario(idUsuario)).thenReturn(Optional.empty());
    //    when(juegoRepo.obtenerPorId(idJuego)).thenReturn(Optional.empty());
//
    //    assertThrows(NullPointerException.class,
    //            () -> controlador.realizarCompra(idUsuario, idJuego, CompraMetodoPagoEnum.CARTERA_STEAM));
    //}
//
    //// -----------------------------------------------------------------
    //// Tests para procesarPago
    //// -----------------------------------------------------------------
//
    //@Test
    //void procesarPago_CuandoCompraPendienteYSaldoSuficiente_DeberiaCompletarPagoYRetornarDTO() throws ExcepcionValidacion {
    //    when(compraRepo.obtenerPorId(idCompra)).thenReturn(Optional.of(compraPendiente));
    //    when(juegoRepo.obtenerPorId(idJuego)).thenReturn(Optional.of(juego));
    //    when(usuarioRepo.obtenerPorId(idUsuario)).thenReturn(Optional.of(usuario));
    //    doNothing().when(usuarioRepo).restarSaldoCartera(idUsuario, precioFinal);
    //    doNothing().when(compraRepo).actualizarEstadoCompra(idCompra, CompraEstadoEnum.COMPLETADA);
//
    //    try (MockedStatic<Mapper> mockedMapper = mockStatic(Mapper.class)) {
    //        mockedMapper.when(() -> Mapper.mapDeCompra(compraPendiente)).thenReturn(compraDTO);
//
    //        CompraDTO resultado = controlador.procesarPago(idCompra, CompraMetodoPagoEnum.CARTERA_STEAM);
//
    //        assertNotNull(resultado);
    //        verify(usuarioRepo).restarSaldoCartera(idUsuario, precioFinal);
    //        verify(compraRepo).actualizarEstadoCompra(idCompra, CompraEstadoEnum.COMPLETADA);
    //    }
    //}
//
    //@Test
    //void procesarPago_CuandoCompraNoExiste_DeberiaLanzarExcepcionValidacion() {
    //    when(compraRepo.obtenerPorId(idCompra)).thenReturn(Optional.empty());
//
    //    ExcepcionValidacion ex = assertThrows(ExcepcionValidacion.class,
    //            () -> controlador.procesarPago(idCompra, CompraMetodoPagoEnum.CARTERA_STEAM));
//
    //    List<ErrorDTO> errores = ex.getErrores();
    //    assertEquals(1, errores.size());
    //    assertEquals("Compra", errores.get(0).campo());
    //    assertEquals(ErrorType.NO_ENCONTRADO, errores.get(0).mensaje());
    //}
//
    //@Test
    //void procesarPago_CuandoCompraNoEstaPendiente_DeberiaLanzarExcepcionValidacion() {
    //    // Crear un mock de CompraEntidad que simule una compra ya completada
    //    CompraEntidad compraCompletada = mock(CompraEntidad.class);
    //    when(compraCompletada.getEstadoCompra()).thenReturn(CompraEstadoEnum.COMPLETADA);
    //    // El controlador también accede a estos campos antes de validar el estado
    //    when(compraCompletada.getId()).thenReturn(idCompra);
    //    when(compraCompletada.getUsuarioId()).thenReturn(idUsuario);
    //    when(compraCompletada.getJuegoId()).thenReturn(idJuego);
    //    // Si en tu controlador se usan más getters, añádelos aquí
//
    //    when(compraRepo.obtenerPorId(idCompra)).thenReturn(Optional.of(compraCompletada));
//
    //    ExcepcionValidacion ex = assertThrows(ExcepcionValidacion.class,
    //            () -> controlador.procesarPago(idCompra, CompraMetodoPagoEnum.CARTERA_STEAM));
//
    //    List<ErrorDTO> errores = ex.getErrores();
    //    assertEquals(1, errores.size());
    //    assertEquals("Compra en estado realizada", errores.get(0).campo());
    //    assertEquals(ErrorType.VALOR_NO_VALIDO, errores.get(0).mensaje());
    //}
//
    //@Test
    //void procesarPago_CuandoSaldoInsuficiente_DeberiaLanzarExcepcionValidacion() {
    //    UsuarioEntidad usuarioPobre = new UsuarioEntidad(
    //            idUsuario, "user", "test@mail.com", "Pass1", "Name", "España",
    //            LocalDate.of(1990,1,1), LocalDate.now(), "avatar.jpg", 10.0
    //    );
    //    when(compraRepo.obtenerPorId(idCompra)).thenReturn(Optional.of(compraPendiente));
    //    when(juegoRepo.obtenerPorId(idJuego)).thenReturn(Optional.of(juego));
    //    when(usuarioRepo.obtenerPorId(idUsuario)).thenReturn(Optional.of(usuarioPobre));
//
    //    ExcepcionValidacion ex = assertThrows(ExcepcionValidacion.class,
    //            () -> controlador.procesarPago(idCompra, CompraMetodoPagoEnum.CARTERA_STEAM));
//
    //    List<ErrorDTO> errores = ex.getErrores();
    //    assertEquals(1, errores.size());
    //    assertEquals("Saldo insuficiente", errores.get(0).campo());
    //    assertEquals(ErrorType.SALDO_INSUFICIENTE, errores.get(0).mensaje());
//
    //    verify(usuarioRepo, never()).restarSaldoCartera(anyLong(), anyDouble());
    //    verify(compraRepo, never()).actualizarEstadoCompra(anyLong(), any());
    //}
//
    //// -----------------------------------------------------------------
    //// Tests para consultarDetallesCompra
    //// -----------------------------------------------------------------
//
    //@Test
    //void consultarDetallesCompra_CuandoExisteYPertenece_DeberiaRetornarDTO() throws ExcepcionValidacion {
    //    when(compraRepo.obtenerPorIdUsuarioYIdCompra(idUsuario, idCompra))
    //            .thenReturn(Optional.of(compraPendiente));
//
    //    try (MockedStatic<Mapper> mockedMapper = mockStatic(Mapper.class)) {
    //        mockedMapper.when(() -> Mapper.mapDeCompra(compraPendiente)).thenReturn(compraDTO);
//
    //        CompraDTO resultado = controlador.consultarDetallesCompra(idCompra, idUsuario);
//
    //        assertNotNull(resultado);
    //        assertEquals(idCompra, resultado.getId());
    //    }
    //}
//
    //@Test
    //void consultarDetallesCompra_CuandoNoExiste_DeberiaLanzarExcepcionValidacion() {
    //    when(compraRepo.obtenerPorIdUsuarioYIdCompra(idUsuario, idCompra))
    //            .thenReturn(Optional.empty());
//
    //    ExcepcionValidacion ex = assertThrows(ExcepcionValidacion.class,
    //            () -> controlador.consultarDetallesCompra(idCompra, idUsuario));
//
    //    List<ErrorDTO> errores = ex.getErrores();
    //    assertEquals(1, errores.size());
    //    assertEquals("compra", errores.get(0).campo());
    //    assertEquals(ErrorType.NO_ENCONTRADO, errores.get(0).mensaje());
    //}
//
    //// -----------------------------------------------------------------
    //// Métodos no implementados
    //// -----------------------------------------------------------------
//
    //@Test
    //void consultarHistorialCompras_DeberiaLanzarUnsupportedOperationException() {
    //    assertThrows(UnsupportedOperationException.class,
    //            () -> controlador.consultarHistorialCompras(idUsuario, "", null, null));
    //}
//
    //@Test
    //void solicitarReembolso_DeberiaLanzarUnsupportedOperationException() {
    //    assertThrows(UnsupportedOperationException.class,
    //            () -> controlador.solicitarReembolso(idCompra, "motivo"));
    //}
//
    //@Test
    //void generarFactura_DeberiaLanzarUnsupportedOperationException() {
    //    assertThrows(UnsupportedOperationException.class,
    //            () -> controlador.generarFactura(idCompra));
    //}

}
