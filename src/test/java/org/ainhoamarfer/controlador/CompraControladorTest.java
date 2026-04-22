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
import org.ainhoamarfer.modelo.dtos.JuegoDTO;
import org.ainhoamarfer.modelo.dtos.UsuarioDTO;
import org.ainhoamarfer.modelo.entidad.BibliotecaEntidad;
import org.ainhoamarfer.modelo.entidad.CompraEntidad;
import org.ainhoamarfer.modelo.entidad.JuegoEntidad;
import org.ainhoamarfer.modelo.entidad.UsuarioEntidad;
import org.ainhoamarfer.modelo.enums.*;
import org.ainhoamarfer.modelo.form.CompraForm;
import org.ainhoamarfer.repositorio.interfaz.IBibliotecaRepo;
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

@ExtendWith(MockitoExtension.class)
public class CompraControladorTest {

    @Mock
    private ICompraRepo compraRepo;

    @Mock
    private IJuegosRepo juegoRepo;

    @Mock
    private IUsuarioRepo usuarioRepo;

    @Mock
    private IBibliotecaRepo bibliotecaRepo;

    @InjectMocks
    private CompraControlador controlador;

    private final long idUsuario = 1L;
    private final long idJuego = 100L;
    private final long idCompra = 10L;
    private final double precioOriginal = 49.99;
    private final double descuento = 10.0;
    private final double precioFinal = precioOriginal * (1 - descuento / 100.0);
    private final double saldoCartera = 100.0;
    private final LocalDate fecha = LocalDate.now();

    private UsuarioEntidad usuario;
    private JuegoEntidad juego;
    private CompraEntidad compraPendiente;
    private UsuarioDTO usuarioDTO;
    private JuegoDTO juegoDTO;
    private CompraDTO compraDTO;

    @BeforeEach
    void setUp() {
        usuario = new UsuarioEntidad(
                idUsuario, "usuarioTest", "test@email.com", "Password1",
                "Nombre Real", "España", LocalDate.of(1990, 1, 1), LocalDate.now(),
                "avatar.jpg", saldoCartera
        );
        usuarioDTO = new UsuarioDTO(
                idUsuario, "usuarioTest", "test@email.com", "Nombre Real",
                "España", LocalDate.of(1990, 1, 1), LocalDate.now(), "avatar.jpg",
                saldoCartera, UsuarioEstadoCuenta.ACTIVA
        );

        juego = new JuegoEntidad(
                idJuego, "Juego Test", "Descripción del juego", "Desarrollador Test",
                LocalDate.of(2023, 5, 10), precioOriginal, descuento,
                "Aventura", "Español, Inglés", JuegoClasificacionEdad.PEGI_12
        );
        juegoDTO = new JuegoDTO(
                idJuego, "Juego Test", "Descripción del juego", "Desarrollador Test",
                LocalDate.of(2023, 5, 10), precioOriginal, descuento,
                "Aventura", "Español, Inglés", JuegoClasificacionEdad.PEGI_12, JuegoEstado.DISPONIBLE
        );

        compraPendiente = new CompraEntidad(
                idCompra, idUsuario, idJuego, fecha, precioOriginal, descuento,
                CompraMetodoPagoEnum.CARTERA_STEAM
        );

        compraDTO = new CompraDTO(
                idCompra, idUsuario, usuarioDTO, idJuego, juegoDTO, fecha,
                precioFinal, descuento, precioOriginal, CompraEstadoEnum.PENDIENTE,
                CompraMetodoPagoEnum.CARTERA_STEAM
        );
    }

    // -----------------------------------------------------------------
    // Tests para realizarCompra
    // -----------------------------------------------------------------
    @Test
    void realizarCompra_CuandoUsuarioActivoYConSaldoSuficiente_DeberiaCrearCompraYRetornarDTO() throws ExcepcionValidacion {
        when(compraRepo.obtenerPorIdUsuario(idUsuario)).thenReturn(Optional.empty());
        when(juegoRepo.obtenerPorId(idJuego)).thenReturn(Optional.of(juego));
        when(usuarioRepo.obtenerPorId(idUsuario)).thenReturn(Optional.of(usuario));
        when(compraRepo.crear(any(CompraForm.class))).thenReturn(Optional.of(compraPendiente));

        try (MockedStatic<Mapper> mockedMapper = mockStatic(Mapper.class)) {
            mockedMapper.when(() -> Mapper.mapDeUsuario(usuario)).thenReturn(usuarioDTO);
            mockedMapper.when(() -> Mapper.mapDeJuego(juego)).thenReturn(juegoDTO);
            mockedMapper.when(() -> Mapper.mapDeCompra(compraPendiente, usuarioDTO, juegoDTO))
                    .thenReturn(compraDTO);

            CompraDTO resultado = controlador.realizarCompra(idUsuario, idJuego, CompraMetodoPagoEnum.CARTERA_STEAM);

            assertNotNull(resultado);
            assertEquals(idCompra, resultado.getId());
            verify(compraRepo).crear(any(CompraForm.class));
        }
    }

    @Test
    void realizarCompra_CuandoUsuarioYaComproElJuego_DeberiaLanzarExcepcionValidacion() {
        when(compraRepo.obtenerPorIdUsuario(idUsuario)).thenReturn(Optional.of(compraPendiente));

        ExcepcionValidacion ex = assertThrows(ExcepcionValidacion.class,
                () -> controlador.realizarCompra(idUsuario, idJuego, CompraMetodoPagoEnum.CARTERA_STEAM));

        List<ErrorDTO> errores = ex.getErrores();
        assertEquals(1, errores.size());
        assertEquals("compra", errores.get(0).campo());
        assertEquals(ErrorType.COMPRA_YA_EXISTENTE, errores.get(0).mensaje());
        verify(compraRepo, never()).crear(any());
    }

    @Test
    void realizarCompra_CuandoSaldoInsuficienteYMetodoCartera_DeberiaLanzarExcepcionValidacion() {
        UsuarioEntidad usuarioPobre = new UsuarioEntidad(
                idUsuario, "user", "test@mail.com", "Pass1", "Name", "España",
                LocalDate.of(1990,1,1), LocalDate.now(), "avatar.jpg", 10.0
        );
        when(compraRepo.obtenerPorIdUsuario(idUsuario)).thenReturn(Optional.empty());
        when(juegoRepo.obtenerPorId(idJuego)).thenReturn(Optional.of(juego));
        when(usuarioRepo.obtenerPorId(idUsuario)).thenReturn(Optional.of(usuarioPobre));

        ExcepcionValidacion ex = assertThrows(ExcepcionValidacion.class,
                () -> controlador.realizarCompra(idUsuario, idJuego, CompraMetodoPagoEnum.CARTERA_STEAM));

        List<ErrorDTO> errores = ex.getErrores();
        assertEquals(1, errores.size());
        assertEquals("precioBase", errores.get(0).campo());
        assertEquals(ErrorType.VALOR_NO_VALIDO, errores.get(0).mensaje());
        verify(compraRepo, never()).crear(any());
    }

    @Test
    void realizarCompra_CuandoUsuarioNoActivo_DeberiaLanzarExcepcionValidacion() {
        UsuarioEntidad usuarioSuspendido = new UsuarioEntidad(
                idUsuario, "user", "test@mail.com", "Pass1", "Name", "España",
                LocalDate.of(1990,1,1), LocalDate.now(), "avatar.jpg", 100.0
        );
        // Forzar estado SUSPENDIDA mediante mock (no hay setter)
        UsuarioEntidad spyUsuario = spy(usuarioSuspendido);
        when(spyUsuario.getEstadoCuenta()).thenReturn(UsuarioEstadoCuenta.SUSPENDIDA);

        when(compraRepo.obtenerPorIdUsuario(idUsuario)).thenReturn(Optional.empty());
        when(juegoRepo.obtenerPorId(idJuego)).thenReturn(Optional.of(juego));
        when(usuarioRepo.obtenerPorId(idUsuario)).thenReturn(Optional.of(spyUsuario));

        ExcepcionValidacion ex = assertThrows(ExcepcionValidacion.class,
                () -> controlador.realizarCompra(idUsuario, idJuego, CompraMetodoPagoEnum.CARTERA_STEAM));

        List<ErrorDTO> errores = ex.getErrores();
        assertEquals(1, errores.size());
        assertEquals("usuario", errores.get(0).campo());
        assertEquals(ErrorType.ESTADO_CUENTA, errores.get(0).mensaje());
        verify(compraRepo, never()).crear(any());
    }

    // -----------------------------------------------------------------
    // Tests para procesarPago
    // -----------------------------------------------------------------
    @Test
    void procesarPago_CuandoCompraPendienteYSaldoSuficiente_DeberiaCompletarPagoYRetornarDTO() throws ExcepcionValidacion {
        when(compraRepo.obtenerPorId(idCompra)).thenReturn(Optional.of(compraPendiente));
        when(juegoRepo.obtenerPorId(idJuego)).thenReturn(Optional.of(juego));
        when(usuarioRepo.obtenerPorId(idUsuario)).thenReturn(Optional.of(usuario));
        doNothing().when(usuarioRepo).restarSaldoCartera(idUsuario, precioOriginal);
        doNothing().when(compraRepo).actualizarEstadoCompra(idCompra, CompraEstadoEnum.COMPLETADA);

        try (MockedStatic<Mapper> mockedMapper = mockStatic(Mapper.class)) {
            mockedMapper.when(() -> Mapper.mapDeUsuario(usuario)).thenReturn(usuarioDTO);
            mockedMapper.when(() -> Mapper.mapDeJuego(juego)).thenReturn(juegoDTO);
            mockedMapper.when(() -> Mapper.mapDeCompra(compraPendiente, usuarioDTO, juegoDTO))
                    .thenReturn(compraDTO);

            CompraDTO resultado = controlador.procesarPago(idCompra, CompraMetodoPagoEnum.CARTERA_STEAM);

            assertNotNull(resultado);
            verify(usuarioRepo).restarSaldoCartera(idUsuario, precioOriginal);
            verify(compraRepo).actualizarEstadoCompra(idCompra, CompraEstadoEnum.COMPLETADA);
        }
    }

    @Test
    void procesarPago_CuandoCompraNoExiste_DeberiaLanzarExcepcionValidacion() {
        when(compraRepo.obtenerPorId(idCompra)).thenReturn(Optional.empty());

        ExcepcionValidacion ex = assertThrows(ExcepcionValidacion.class,
                () -> controlador.procesarPago(idCompra, CompraMetodoPagoEnum.CARTERA_STEAM));

        List<ErrorDTO> errores = ex.getErrores();
        assertEquals(1, errores.size());
        assertEquals("Compra", errores.get(0).campo());
        assertEquals(ErrorType.NO_ENCONTRADO, errores.get(0).mensaje());
    }

    @Test
    void procesarPago_CuandoCompraNoEstaPendiente_DeberiaLanzarExcepcionValidacion() {
        CompraEntidad compraCompletada = new CompraEntidad(
                idCompra, idUsuario, idJuego, fecha, precioOriginal, descuento,
                CompraMetodoPagoEnum.CARTERA_STEAM
        );
        compraCompletada.setEstadoCompra(CompraEstadoEnum.COMPLETADA);
        when(compraRepo.obtenerPorId(idCompra)).thenReturn(Optional.of(compraCompletada));

        ExcepcionValidacion ex = assertThrows(ExcepcionValidacion.class,
                () -> controlador.procesarPago(idCompra, CompraMetodoPagoEnum.CARTERA_STEAM));

        List<ErrorDTO> errores = ex.getErrores();
        assertEquals(1, errores.size());
        assertEquals("Compra en estado realizada", errores.get(0).campo());
        assertEquals(ErrorType.VALOR_NO_VALIDO, errores.get(0).mensaje());
    }

    @Test
    void procesarPago_CuandoSaldoInsuficiente_DeberiaLanzarExcepcionValidacion() {
        UsuarioEntidad usuarioPobre = new UsuarioEntidad(
                idUsuario, "user", "test@mail.com", "Pass1", "Name", "España",
                LocalDate.of(1990,1,1), LocalDate.now(), "avatar.jpg", 10.0
        );
        when(compraRepo.obtenerPorId(idCompra)).thenReturn(Optional.of(compraPendiente));
        when(juegoRepo.obtenerPorId(idJuego)).thenReturn(Optional.of(juego));
        when(usuarioRepo.obtenerPorId(idUsuario)).thenReturn(Optional.of(usuarioPobre));

        ExcepcionValidacion ex = assertThrows(ExcepcionValidacion.class,
                () -> controlador.procesarPago(idCompra, CompraMetodoPagoEnum.CARTERA_STEAM));

        List<ErrorDTO> errores = ex.getErrores();
        assertEquals(1, errores.size());
        assertEquals("Saldo insuficiente", errores.get(0).campo());
        assertEquals(ErrorType.SALDO_INSUFICIENTE, errores.get(0).mensaje());
        verify(usuarioRepo, never()).restarSaldoCartera(anyLong(), anyDouble());
        verify(compraRepo, never()).actualizarEstadoCompra(anyLong(), any());
    }

    // -----------------------------------------------------------------
    // Tests para consultarDetallesCompra
    // -----------------------------------------------------------------
    @Test
    void consultarDetallesCompra_CuandoExisteYPertenece_DeberiaRetornarDTO() throws ExcepcionValidacion {
        when(compraRepo.obtenerPorIdUsuarioYIdCompra(idUsuario, idCompra))
                .thenReturn(Optional.of(compraPendiente));
        when(usuarioRepo.obtenerPorId(idUsuario)).thenReturn(Optional.of(usuario));
        when(juegoRepo.obtenerPorId(idJuego)).thenReturn(Optional.of(juego));

        try (MockedStatic<Mapper> mockedMapper = mockStatic(Mapper.class)) {
            mockedMapper.when(() -> Mapper.mapDeUsuario(usuario)).thenReturn(usuarioDTO);
            mockedMapper.when(() -> Mapper.mapDeJuego(juego)).thenReturn(juegoDTO);
            mockedMapper.when(() -> Mapper.mapDeCompra(compraPendiente, usuarioDTO, juegoDTO))
                    .thenReturn(compraDTO);

            CompraDTO resultado = controlador.consultarDetallesCompra(idCompra, idUsuario);

            assertNotNull(resultado);
            assertEquals(idCompra, resultado.getId());
        }
    }

    @Test
    void consultarDetallesCompra_CuandoNoExiste_DeberiaLanzarExcepcionValidacion() {
        when(compraRepo.obtenerPorIdUsuarioYIdCompra(idUsuario, idCompra))
                .thenReturn(Optional.empty());

        ExcepcionValidacion ex = assertThrows(ExcepcionValidacion.class,
                () -> controlador.consultarDetallesCompra(idCompra, idUsuario));

        List<ErrorDTO> errores = ex.getErrores();
        assertEquals(1, errores.size());
        assertEquals("compra", errores.get(0).campo());
        assertEquals(ErrorType.NO_ENCONTRADO, errores.get(0).mensaje());
    }

    // -----------------------------------------------------------------
    // Tests para solicitarReembolso
    // -----------------------------------------------------------------
    @Test
    void solicitarReembolso_CuandoCondicionesValidas_DeberiaReembolsarYRetornarDTO() throws ExcepcionValidacion {
        // Compra completada dentro del plazo
        CompraEntidad compraCompletada = new CompraEntidad(
                idCompra, idUsuario, idJuego, LocalDate.now().minusDays(5),
                precioOriginal, descuento, CompraMetodoPagoEnum.CARTERA_STEAM
        );
        compraCompletada.setEstadoCompra(CompraEstadoEnum.COMPLETADA);

        BibliotecaEntidad biblioteca = new BibliotecaEntidad(
                1L, idUsuario, idJuego, LocalDate.now().minusDays(5), 1.5, LocalDate.now().minusDays(1)
        );

        when(compraRepo.obtenerPorIdUsuario(idCompra)).thenReturn(Optional.of(compraCompletada));
        when(bibliotecaRepo.obtenerPorIdUsuarioYIdJuego(idUsuario, idJuego)).thenReturn(Optional.of(biblioteca));
        when(usuarioRepo.obtenerPorId(idUsuario)).thenReturn(Optional.of(usuario));
        doNothing().when(usuarioRepo).sumarSaldoCartera(eq(idUsuario), anyDouble());
        doNothing().when(compraRepo).actualizarEstadoCompra(idCompra, CompraEstadoEnum.REEMBOLSADA);
        when(compraRepo.obtenerPorId(idCompra)).thenReturn(Optional.of(compraCompletada));
        when(juegoRepo.obtenerPorId(idJuego)).thenReturn(Optional.of(juego));

        try (MockedStatic<Mapper> mockedMapper = mockStatic(Mapper.class)) {
            mockedMapper.when(() -> Mapper.mapDeUsuario(usuario)).thenReturn(usuarioDTO);
            mockedMapper.when(() -> Mapper.mapDeJuego(juego)).thenReturn(juegoDTO);
            mockedMapper.when(() -> Mapper.mapDeCompra(compraCompletada, usuarioDTO, juegoDTO))
                    .thenReturn(compraDTO);

            CompraDTO resultado = controlador.solicitarReembolso(idCompra, "motivo");

            assertNotNull(resultado);
            verify(usuarioRepo).sumarSaldoCartera(eq(idUsuario), anyDouble());
            verify(compraRepo).actualizarEstadoCompra(idCompra, CompraEstadoEnum.REEMBOLSADA);
        }
    }

    @Test
    void solicitarReembolso_CuandoCompraNoCompletada_DeberiaLanzarExcepcionValidacion() {
        // compraPendiente tiene estado PENDIENTE
        when(compraRepo.obtenerPorIdUsuario(idCompra)).thenReturn(Optional.of(compraPendiente));

        ExcepcionValidacion ex = assertThrows(ExcepcionValidacion.class,
                () -> controlador.solicitarReembolso(idCompra, "motivo"));

        List<ErrorDTO> errores = ex.getErrores();
        assertEquals(1, errores.size());
        assertEquals("La compra aun no se completo, no puedes solicitar reenvolso", errores.get(0).campo());
    }

    @Test
    void solicitarReembolso_CuandoPlazoVencido_DeberiaLanzarExcepcionValidacion() {
        CompraEntidad compraAntigua = new CompraEntidad(
                idCompra, idUsuario, idJuego, LocalDate.now().minusDays(30),
                precioOriginal, descuento, CompraMetodoPagoEnum.CARTERA_STEAM
        );
        compraAntigua.setEstadoCompra(CompraEstadoEnum.COMPLETADA);
        when(compraRepo.obtenerPorIdUsuario(idCompra)).thenReturn(Optional.of(compraAntigua));

        ExcepcionValidacion ex = assertThrows(ExcepcionValidacion.class,
                () -> controlador.solicitarReembolso(idCompra, "motivo"));

        List<ErrorDTO> errores = ex.getErrores();
        assertEquals(1, errores.size());
        assertEquals("plazoReembolso", errores.get(0).campo());
        assertEquals(ErrorType.PLAZO_REEMBOLSO_VENCIDO, errores.get(0).mensaje());
    }

    @Test
    void solicitarReembolso_CuandoHorasJugadasExcedidas_DeberiaLanzarExcepcionValidacion() {
        CompraEntidad compraReciente = new CompraEntidad(
                idCompra, idUsuario, idJuego, LocalDate.now().minusDays(1),
                precioOriginal, descuento, CompraMetodoPagoEnum.CARTERA_STEAM
        );
        compraReciente.setEstadoCompra(CompraEstadoEnum.COMPLETADA);
        BibliotecaEntidad biblioteca = new BibliotecaEntidad(
                1L, idUsuario, idJuego, LocalDate.now().minusDays(1), 5.0, LocalDate.now()
        );
        when(compraRepo.obtenerPorIdUsuario(idCompra)).thenReturn(Optional.of(compraReciente));
        when(bibliotecaRepo.obtenerPorIdUsuarioYIdJuego(idUsuario, idJuego)).thenReturn(Optional.of(biblioteca));

        ExcepcionValidacion ex = assertThrows(ExcepcionValidacion.class,
                () -> controlador.solicitarReembolso(idCompra, "motivo"));

        List<ErrorDTO> errores = ex.getErrores();
        assertEquals(1, errores.size());
        assertEquals("tiempoJuego", errores.get(0).campo());
        assertEquals(ErrorType.TIEMPO_EXPIRADO, errores.get(0).mensaje());
    }

    // -----------------------------------------------------------------
    // Métodos no implementados
    // -----------------------------------------------------------------
    @Test
    void consultarHistorialCompras_DeberiaLanzarUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class,
                () -> controlador.consultarHistorialCompras(idUsuario, "", null, null));
    }

    @Test
    void generarFactura_DeberiaLanzarUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class,
                () -> controlador.generarFactura(idCompra));
    }

}
