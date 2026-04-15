package org.ainhoamarfer.controlador;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.ainhoamarfer.excepciones.ExcepcionValidacion;
import org.ainhoamarfer.mapper.Mapper;
import org.ainhoamarfer.modelo.dtos.ErrorDTO;
import org.ainhoamarfer.modelo.dtos.UsuarioDTO;
import org.ainhoamarfer.modelo.entidad.UsuarioEntidad;
import org.ainhoamarfer.modelo.enums.ErrorType;
import org.ainhoamarfer.modelo.enums.UsuarioEstadoCuenta;
import org.ainhoamarfer.modelo.form.UsuarioForm;
import org.ainhoamarfer.repositorio.interfaz.IUsuarioRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UsuarioControladorTest {

    @Mock
    private IUsuarioRepo usuarioRepo;

    @InjectMocks
    private UsuarioControlador controlador;

    // Datos de prueba comunes
    private final long idUsuario = 1L;
    private final String nombreUsuario = "testUser";
    private final String email = "test@email.com";
    private final String contrasena = "Password1";
    private final String nombreReal = "Nombre Real";
    private final String pais = "España";
    private final LocalDate fechaNaci = LocalDate.of(1990, 1, 1);
    private final LocalDate fechaRegistro = LocalDate.now();
    private final String avatar = "avatar.jpg";
    private final double saldoInicial = 50.0;
    private final UsuarioEstadoCuenta estadoActivo = UsuarioEstadoCuenta.ACTIVA;

    private UsuarioForm formValido;
    private UsuarioEntidad usuarioEntidad;
    private UsuarioDTO usuarioDTO;

    @BeforeEach
    void setUp() {
        formValido = new UsuarioForm(
                nombreUsuario, email, contrasena, nombreReal,
                pais, fechaNaci, fechaRegistro, avatar, saldoInicial, estadoActivo
        );

        usuarioEntidad = new UsuarioEntidad(
                idUsuario, nombreUsuario, email, contrasena, nombreReal,
                pais, fechaNaci, avatar, saldoInicial
        );

        usuarioDTO = new UsuarioDTO(
                idUsuario, nombreUsuario, email, contrasena, nombreReal,
                fechaNaci, fechaRegistro, avatar, saldoInicial, estadoActivo
        );
    }

    // -----------------------------------------------------------------
    // Tests para registrarNuevoUsuario
    // -----------------------------------------------------------------

    @Test
    void registrarNuevoUsuario_CuandoFormValidoYNombreNoDuplicado_DeberiaCrearUsuarioYRetornarDTO() throws ExcepcionValidacion {
        // Arrange
        when(usuarioRepo.obtenerPorNombreUsuario(nombreUsuario)).thenReturn(Optional.empty());
        when(usuarioRepo.crear(formValido)).thenReturn(Optional.of(usuarioEntidad));

        try (MockedStatic<Mapper> mockedMapper = mockStatic(Mapper.class)) {
            mockedMapper.when(() -> Mapper.mapDeUsuario(usuarioEntidad)).thenReturn(usuarioDTO);

            // Act
            UsuarioDTO resultado = controlador.registrarNuevoUsuario(formValido);

            // Assert
            assertNotNull(resultado);
            assertEquals(idUsuario, resultado.getId());
            verify(usuarioRepo).obtenerPorNombreUsuario(nombreUsuario);
            verify(usuarioRepo).crear(formValido);
        }
    }

    @Test
    void registrarNuevoUsuario_CuandoFormInvalido_DeberiaLanzarExcepcionValidacion() {
        // Arrange: mockeamos el método validar del form para que devuelva errores
        UsuarioForm formMock = mock(UsuarioForm.class);
        List<ErrorDTO> erroresValidacion = List.of(new ErrorDTO("email", ErrorType.FORMATO_INVALIDO));
        when(formMock.validar(formMock)).thenReturn(erroresValidacion);
        // Aunque el form mock no tiene datos reales, el método usa form.validar(form) y luego
        // obtiene nombre con getNombreUsuario() para la verificación de duplicado.
        when(formMock.getNombreUsuario()).thenReturn("dummy");

        // Act & Assert
        ExcepcionValidacion ex = assertThrows(ExcepcionValidacion.class,
                () -> controlador.registrarNuevoUsuario(formMock));

        assertEquals(erroresValidacion, ex.getErrores());
        verify(usuarioRepo, never()).crear(any());
    }

    @Test
    void registrarNuevoUsuario_CuandoNombreYaExiste_DeberiaLanzarExcepcionValidacionConErrorDuplicado() {
        // Arrange: form válido, pero ya existe usuario con ese nombre
        when(usuarioRepo.obtenerPorNombreUsuario(nombreUsuario)).thenReturn(Optional.of(usuarioEntidad));
        // El método validar del form devolverá lista vacía (suponemos que el form es válido)
        // Como no mockeamos form.validar, usamos el real, que para datos válidos devuelve vacío.

        // Act & Assert
        ExcepcionValidacion ex = assertThrows(ExcepcionValidacion.class,
                () -> controlador.registrarNuevoUsuario(formValido));

        List<ErrorDTO> errores = ex.getErrores();
        assertEquals(1, errores.size());
        assertEquals("nombre", errores.get(0).campo());
        assertEquals(ErrorType.DUPLICADO, errores.get(0).mensaje());

        verify(usuarioRepo, never()).crear(any());
    }

    // -----------------------------------------------------------------
    // Tests para consultarPerfil
    // -----------------------------------------------------------------

    @Test
    void consultarPerfil_CuandoUsuarioExiste_DeberiaRetornarDTO() throws ExcepcionValidacion {
        // Arrange
        when(usuarioRepo.obtenerPorId(idUsuario)).thenReturn(Optional.of(usuarioEntidad));

        try (MockedStatic<Mapper> mockedMapper = mockStatic(Mapper.class)) {
            mockedMapper.when(() -> Mapper.mapDeUsuario(usuarioEntidad)).thenReturn(usuarioDTO);

            // Act
            UsuarioDTO resultado = controlador.consultarPerfil(idUsuario);

            // Assert
            assertNotNull(resultado);
            assertEquals(idUsuario, resultado.getId());
        }
    }

    @Test
    void consultarPerfil_CuandoUsuarioNoExiste_DeberiaLanzarExcepcionValidacion() {
        // Arrange
        when(usuarioRepo.obtenerPorId(idUsuario)).thenReturn(Optional.empty());

        // Act & Assert
        ExcepcionValidacion ex = assertThrows(ExcepcionValidacion.class,
                () -> controlador.consultarPerfil(idUsuario));

        List<ErrorDTO> errores = ex.getErrores();
        assertEquals(1, errores.size());
        assertEquals("Usuario", errores.get(0).campo());
        assertEquals(ErrorType.USUARIO_INVALIDO, errores.get(0).mensaje());
    }

    // -----------------------------------------------------------------
    // Tests para anadirSaldoCartera
    // -----------------------------------------------------------------

    @Test
    void anadirSaldoCartera_CuandoUsuarioActivoYRecargaValida_DeberiaActualizarSaldoYRetornarNuevoSaldo() throws ExcepcionValidacion {
        // Arrange
        double recarga = 25.0;
        double nuevoSaldoEsperado = saldoInicial + recarga;

        when(usuarioRepo.obtenerPorId(idUsuario)).thenReturn(Optional.of(usuarioEntidad));
        when(usuarioRepo.actualizar(eq(idUsuario), any(UsuarioForm.class)))
                .thenReturn(Optional.of(usuarioEntidad)); // No es crítico el retorno

        try (MockedStatic<Util> mockedUtil = mockStatic(Util.class)) {
            mockedUtil.when(() -> Util.validarRecargaCartera(recarga)).thenReturn(true);

            // Act
            Double resultado = controlador.anadirSaldoCartera(recarga, idUsuario);

            // Assert
            assertEquals(nuevoSaldoEsperado, resultado);
            verify(usuarioRepo).actualizar(eq(idUsuario), any(UsuarioForm.class));
        }
    }

    @Test
    void anadirSaldoCartera_CuandoUsuarioNoExiste_DeberiaLanzarExcepcionValidacion() {
        // Arrange
        when(usuarioRepo.obtenerPorId(idUsuario)).thenReturn(Optional.empty());

        // Act & Assert
        ExcepcionValidacion ex = assertThrows(ExcepcionValidacion.class,
                () -> controlador.anadirSaldoCartera(10.0, idUsuario));

        assertEquals(ErrorType.USUARIO_INVALIDO, ex.getErrores().get(0).mensaje());
        verify(usuarioRepo, never()).actualizar(anyLong(), any());
    }

    @Test
    void anadirSaldoCartera_CuandoCuentaBloqueada_DeberiaLanzarExcepcionValidacion() {
        // Arrange: mock de UsuarioEntidad que solo responde a getEstadoCuenta()
        UsuarioEntidad usuarioMock = mock(UsuarioEntidad.class);
        when(usuarioMock.getEstadoCuenta()).thenReturn(UsuarioEstadoCuenta.SUSPENDIDA);
        // No es necesario stubear getSaldoCartera, getNombreUsuario, etc. porque no se usarán

        when(usuarioRepo.obtenerPorId(idUsuario)).thenReturn(Optional.of(usuarioMock));

        // Act & Assert
        ExcepcionValidacion ex = assertThrows(ExcepcionValidacion.class,
                () -> controlador.anadirSaldoCartera(10.0, idUsuario));

        List<ErrorDTO> errores = ex.getErrores();
        assertEquals(1, errores.size());
        assertEquals("Estado cuenta", errores.get(0).campo());
        assertEquals(ErrorType.ESTADO_CUENTA, errores.get(0).mensaje());

        verify(usuarioRepo, never()).actualizar(anyLong(), any());
    }

    @Test
    void anadirSaldoCartera_CuandoRecargaInvalida_DeberiaLanzarExcepcionValidacion() {
        // Arrange
        double recargaInvalida = -5.0;
        when(usuarioRepo.obtenerPorId(idUsuario)).thenReturn(Optional.of(usuarioEntidad));

        try (MockedStatic<Util> mockedUtil = mockStatic(Util.class)) {
            mockedUtil.when(() -> Util.validarRecargaCartera(recargaInvalida)).thenReturn(false);

            // Act & Assert
            ExcepcionValidacion ex = assertThrows(ExcepcionValidacion.class,
                    () -> controlador.anadirSaldoCartera(recargaInvalida, idUsuario));

            List<ErrorDTO> errores = ex.getErrores();
            assertEquals(1, errores.size());
            assertEquals("Dinero recarga", errores.get(0).campo());
            assertEquals(ErrorType.VALOR_NO_VALIDO, errores.get(0).mensaje());

            verify(usuarioRepo, never()).actualizar(anyLong(), any());
        }
    }

    // -----------------------------------------------------------------
    // Tests para consultarSaldoCartera
    // -----------------------------------------------------------------

    @Test
    void consultarSaldoCartera_CuandoUsuarioExiste_DeberiaRetornarSaldo() throws ExcepcionValidacion {
        // Arrange
        when(usuarioRepo.obtenerPorId(idUsuario)).thenReturn(Optional.of(usuarioEntidad));

        // Act
        Double saldo = controlador.consultarSaldoCartera(idUsuario);

        // Assert
        assertEquals(saldoInicial, saldo);
    }

    @Test
    void consultarSaldoCartera_CuandoUsuarioNoExiste_DeberiaLanzarExcepcionValidacion() {
        // Arrange
        when(usuarioRepo.obtenerPorId(idUsuario)).thenReturn(Optional.empty());

        // Act & Assert
        ExcepcionValidacion ex = assertThrows(ExcepcionValidacion.class,
                () -> controlador.consultarSaldoCartera(idUsuario));

        assertEquals(ErrorType.USUARIO_INVALIDO, ex.getErrores().get(0).mensaje());
    }

}

