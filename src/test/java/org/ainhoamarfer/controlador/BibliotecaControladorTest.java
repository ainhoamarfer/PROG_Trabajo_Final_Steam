package org.ainhoamarfer.controlador;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.ainhoamarfer.excepciones.ExcepcionValidacion;
import org.ainhoamarfer.mapper.Mapper;
import org.ainhoamarfer.modelo.dtos.BibliotecaDTO;
import org.ainhoamarfer.modelo.dtos.ErrorDTO;
import org.ainhoamarfer.modelo.dtos.JuegoDTO;
import org.ainhoamarfer.modelo.dtos.UsuarioDTO;
import org.ainhoamarfer.modelo.entidad.BibliotecaEntidad;
import org.ainhoamarfer.modelo.entidad.JuegoEntidad;
import org.ainhoamarfer.modelo.entidad.UsuarioEntidad;
import org.ainhoamarfer.modelo.enums.ErrorType;
import org.ainhoamarfer.modelo.enums.JuegoClasificacionEdad;
import org.ainhoamarfer.modelo.enums.UsuarioEstadoCuenta;
import org.ainhoamarfer.modelo.form.BibliotecaForm;
import org.ainhoamarfer.repositorio.interfaz.IBibliotecaRepo;
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
public class BibliotecaControladorTest {

@Mock
private IBibliotecaRepo biblioRepo;

        @Mock
        private IJuegosRepo juegoRepo;

        @Mock
        private IUsuarioRepo usuarioRepo;

        @InjectMocks
        private BibliotecaControlador controlador;

        private final long idUsuario = 1L;
        private final long idJuego = 100L;
        private final long idBiblioteca = 10L;
        private final LocalDate fecha = LocalDate.now();

        private UsuarioEntidad usuario;
        private JuegoEntidad juego;
        private UsuarioDTO usuarioDTO;
        private JuegoDTO juegoDTO;
        private BibliotecaEntidad bibliotecaExistente;
        private BibliotecaEntidad bibliotecaNueva;
        private BibliotecaDTO bibliotecaDTO;

        @BeforeEach
        void setUp() {
            usuario = new UsuarioEntidad(
                    idUsuario, "user", "email@test.com", "pass", "Real Name",
                    "España", LocalDate.of(1990, 1, 1), LocalDate.now(), "avatar.jpg", 100.0
            );
            usuarioDTO = new UsuarioDTO(
                    idUsuario, "user", "email@test.com", "Real Name",
                    "España", LocalDate.of(1990, 1, 1), LocalDate.now(), "avatar.jpg", 100.0, UsuarioEstadoCuenta.ACTIVA
            );

            juego = new JuegoEntidad(
                    idJuego, "Juego Test", "Desc", "Dev", LocalDate.of(2020, 1, 1),
                    49.99, 0.0, "Cat", "ES", JuegoClasificacionEdad.PEGI_12
            );
            juegoDTO = new JuegoDTO(
                    idJuego, "Juego Test", "Desc", "Dev", LocalDate.of(2020, 1, 1),
                    49.99, 0.0, "Cat", "ES", JuegoClasificacionEdad.PEGI_12, null
            );

            bibliotecaExistente = new BibliotecaEntidad(
                    99L, idUsuario, 999L, fecha, 10.5, fecha
            );

            bibliotecaNueva = new BibliotecaEntidad(
                    idBiblioteca, idUsuario, idJuego, fecha, 0.0, null
            );

            bibliotecaDTO = new BibliotecaDTO(
                    idBiblioteca, idUsuario, usuarioDTO, idJuego, juegoDTO,
                    fecha, 0.0, null, false
            );
        }

        // -----------------------------------------------------------------
        // anadirJuegoBiblioteca
        // -----------------------------------------------------------------
        @Test
        void anadirJuegoBiblioteca_CuandoNoExisteEnBiblioteca_DeberiaCrearYRetornarDTO() throws ExcepcionValidacion {
            when(biblioRepo.obtenerPorIdUsuario(idUsuario)).thenReturn(List.of(bibliotecaExistente));
            when(juegoRepo.obtenerPorId(idJuego)).thenReturn(Optional.of(juego));
            when(biblioRepo.crear(any(BibliotecaForm.class))).thenReturn(Optional.of(bibliotecaNueva));
            when(usuarioRepo.obtenerPorId(idUsuario)).thenReturn(Optional.of(usuario));
            when(juegoRepo.obtenerPorId(idJuego)).thenReturn(Optional.of(juego));

            try (MockedStatic<Mapper> mockedMapper = mockStatic(Mapper.class)) {
                mockedMapper.when(() -> Mapper.mapDeUsuario(usuario)).thenReturn(usuarioDTO);
                mockedMapper.when(() -> Mapper.mapDeJuego(juego)).thenReturn(juegoDTO);
                mockedMapper.when(() -> Mapper.mapDeBiblioteca(bibliotecaNueva, usuarioDTO, juegoDTO))
                        .thenReturn(bibliotecaDTO);

                BibliotecaDTO resultado = controlador.anadirJuegoBiblioteca(idUsuario, idJuego);

                assertNotNull(resultado);
                assertEquals(idBiblioteca, resultado.getId());
                verify(biblioRepo).crear(any(BibliotecaForm.class));
            }
        }

        @Test
        void anadirJuegoBiblioteca_CuandoJuegoYaExisteEnBiblioteca_DeberiaLanzarExcepcionValidacion() {
            BibliotecaEntidad bibliotecaConJuego = new BibliotecaEntidad(
                    88L, idUsuario, idJuego, fecha, 5.0, fecha
            );
            when(biblioRepo.obtenerPorIdUsuario(idUsuario)).thenReturn(List.of(bibliotecaConJuego));
            when(juegoRepo.obtenerPorId(idJuego)).thenReturn(Optional.of(juego));

            ExcepcionValidacion ex = assertThrows(ExcepcionValidacion.class,
                    () -> controlador.anadirJuegoBiblioteca(idUsuario, idJuego));

            List<ErrorDTO> errores = ex.getErrores();
            assertEquals(1, errores.size());
            assertEquals("Este juego ya existe en esta biblioteca", errores.get(0).campo());
            assertEquals(ErrorType.DUPLICADO, errores.get(0).mensaje());

            verify(biblioRepo, never()).crear(any());
        }

        @Test
        void anadirJuegoBiblioteca_CuandoCreacionFalla_DeberiaRetornarNull() throws ExcepcionValidacion {
            when(biblioRepo.obtenerPorIdUsuario(idUsuario)).thenReturn(List.of());
            when(juegoRepo.obtenerPorId(idJuego)).thenReturn(Optional.of(juego));
            when(biblioRepo.crear(any(BibliotecaForm.class))).thenReturn(Optional.empty());
            // No se llama a usuarioRepo ni juegoRepo porque biblioteca es null

            BibliotecaDTO resultado = controlador.anadirJuegoBiblioteca(idUsuario, idJuego);
            assertNull(resultado);
            verify(biblioRepo).crear(any(BibliotecaForm.class));
        }

        // -----------------------------------------------------------------
        // verBibliotecaPersonal
        // -----------------------------------------------------------------
        @Test
        void verBibliotecaPersonal_CuandoExistenBibliotecasSinOrden_RetornaTodas() throws ExcepcionValidacion {
            List<BibliotecaEntidad> lista = List.of(bibliotecaExistente, bibliotecaNueva);
            when(biblioRepo.obtenerPorIdUsuario(idUsuario)).thenReturn(lista);
            // Para obtener usuario y juego (solo se llama una vez con el primer elemento)
            when(usuarioRepo.obtenerPorId(idUsuario)).thenReturn(Optional.of(usuario));
            when(juegoRepo.obtenerPorId(anyLong())).thenReturn(Optional.of(juego));

            try (MockedStatic<Mapper> mockedMapper = mockStatic(Mapper.class)) {
                mockedMapper.when(() -> Mapper.mapDeUsuario(usuario)).thenReturn(usuarioDTO);
                mockedMapper.when(() -> Mapper.mapDeJuego(juego)).thenReturn(juegoDTO);
                mockedMapper.when(() -> Mapper.mapDeBiblioteca(any(), eq(usuarioDTO), eq(juegoDTO)))
                        .thenReturn(bibliotecaDTO);

                List<BibliotecaDTO> resultado = controlador.verBibliotecaPersonal(idUsuario, "");

                assertEquals(2, resultado.size());
            }
        }

        @Test
        void verBibliotecaPersonal_CuandoNoExistenBibliotecas_LanzaExcepcionValidacion() {
            when(biblioRepo.obtenerPorIdUsuario(idUsuario)).thenReturn(List.of());

            ExcepcionValidacion ex = assertThrows(ExcepcionValidacion.class,
                    () -> controlador.verBibliotecaPersonal(idUsuario, null));

            assertEquals(ErrorType.NO_ENCONTRADO, ex.getErrores().get(0).mensaje());
        }

        @Test
        void verBibliotecaPersonal_OrdenFechaAdquisicion_RetornaOrdenDescendente() throws ExcepcionValidacion {
            BibliotecaEntidad antigua = new BibliotecaEntidad(1L, idUsuario, 101L, fecha.minusDays(10), 5.0, null);
            BibliotecaEntidad reciente = new BibliotecaEntidad(2L, idUsuario, 102L, fecha, 2.0, null);
            when(biblioRepo.obtenerPorIdUsuario(idUsuario)).thenReturn(List.of(antigua, reciente));
            when(usuarioRepo.obtenerPorId(idUsuario)).thenReturn(Optional.of(usuario));
            when(juegoRepo.obtenerPorId(anyLong())).thenReturn(Optional.of(juego));

            try (MockedStatic<Mapper> mockedMapper = mockStatic(Mapper.class)) {
                mockedMapper.when(() -> Mapper.mapDeUsuario(usuario)).thenReturn(usuarioDTO);
                mockedMapper.when(() -> Mapper.mapDeJuego(juego)).thenReturn(juegoDTO);
                mockedMapper.when(() -> Mapper.mapDeBiblioteca(any(), eq(usuarioDTO), eq(juegoDTO)))
                        .thenAnswer(inv -> {
                            BibliotecaEntidad b = inv.getArgument(0);
                            return new BibliotecaDTO(b.getId(), 0, null, 0, null,
                                    b.getFechaAdquisicion(), 0, null, false);
                        });

                List<BibliotecaDTO> resultado = controlador.verBibliotecaPersonal(idUsuario, "fecha de adquisición");

                assertEquals(2, resultado.size());
                assertEquals(fecha, resultado.get(0).getFechaAdquisicion());
                assertEquals(fecha.minusDays(10), resultado.get(1).getFechaAdquisicion());
            }
        }

        // -----------------------------------------------------------------
        // eliminarJuegoBiblioteca
        // -----------------------------------------------------------------
        @Test
        void eliminarJuegoBiblioteca_CuandoExiste_DeberiaEliminarYRetornarMensajeExito() {
            when(biblioRepo.obtenerPorIdUsuarioYIdJuego(idUsuario, idJuego))
                    .thenReturn(Optional.of(bibliotecaNueva));
            when(usuarioRepo.obtenerPorId(idUsuario)).thenReturn(Optional.of(usuario));
            when(juegoRepo.obtenerPorId(idJuego)).thenReturn(Optional.of(juego));
            when(biblioRepo.eliminar(idBiblioteca)).thenReturn(true);

            try (MockedStatic<Mapper> mockedMapper = mockStatic(Mapper.class)) {
                mockedMapper.when(() -> Mapper.mapDeUsuario(usuario)).thenReturn(usuarioDTO);
                mockedMapper.when(() -> Mapper.mapDeJuego(juego)).thenReturn(juegoDTO);
                mockedMapper.when(() -> Mapper.mapDeBiblioteca(bibliotecaNueva, usuarioDTO, juegoDTO))
                        .thenReturn(bibliotecaDTO);

                String resultado = controlador.eliminarJuegoBiblioteca(idUsuario, idJuego);

                assertEquals("Juego eliminado de la biblioteca", resultado);
                verify(biblioRepo).eliminar(idBiblioteca);
            }
        }

        @Test
        void eliminarJuegoBiblioteca_CuandoNoExiste_RetornaMensajeNoEncontrado() {
            when(biblioRepo.obtenerPorIdUsuarioYIdJuego(idUsuario, idJuego))
                    .thenReturn(Optional.empty());

            String resultado = controlador.eliminarJuegoBiblioteca(idUsuario, idJuego);

            assertEquals("Juego no encontrado en la biblioteca", resultado);
            verify(biblioRepo, never()).eliminar(anyLong());
        }

        // -----------------------------------------------------------------
        // actualizarTiempoJuego
        // -----------------------------------------------------------------
        @Test
        void actualizarTiempoJuego_CuandoExisteYHorasPositivas_ActualizaYRetornaDTO() throws ExcepcionValidacion {
            double horasIniciales = 10.0;
            double horasAAnadir = 5.5;
            BibliotecaEntidad biblioteca = new BibliotecaEntidad(idBiblioteca, idUsuario, idJuego, fecha, horasIniciales, null);

            when(biblioRepo.obtenerPorIdUsuarioYIdJuego(idUsuario, idJuego)).thenReturn(Optional.of(biblioteca));
            when(biblioRepo.actualizar(eq(idBiblioteca), any(BibliotecaForm.class))).thenReturn(Optional.of(biblioteca));
            when(biblioRepo.obtenerPorId(idBiblioteca)).thenReturn(Optional.of(biblioteca));
            when(usuarioRepo.obtenerPorId(idUsuario)).thenReturn(Optional.of(usuario));
            when(juegoRepo.obtenerPorId(idJuego)).thenReturn(Optional.of(juego));

            try (MockedStatic<Mapper> mockedMapper = mockStatic(Mapper.class)) {
                mockedMapper.when(() -> Mapper.mapDeUsuario(usuario)).thenReturn(usuarioDTO);
                mockedMapper.when(() -> Mapper.mapDeJuego(juego)).thenReturn(juegoDTO);
                mockedMapper.when(() -> Mapper.mapDeBiblioteca(biblioteca, usuarioDTO, juegoDTO))
                        .thenReturn(bibliotecaDTO);

                BibliotecaDTO resultado = controlador.actualizarTiempoJuego(idUsuario, idJuego, horasAAnadir);

                assertNotNull(resultado);
                verify(biblioRepo).actualizar(eq(idBiblioteca), any(BibliotecaForm.class));
            }
        }

        @Test
        void actualizarTiempoJuego_CuandoBibliotecaNoExiste_LanzaExcepcionValidacion() {
            when(biblioRepo.obtenerPorIdUsuarioYIdJuego(idUsuario, idJuego)).thenReturn(Optional.empty());

            ExcepcionValidacion ex = assertThrows(ExcepcionValidacion.class,
                    () -> controlador.actualizarTiempoJuego(idUsuario, idJuego, 5.0));

            assertEquals(ErrorType.NO_ENCONTRADO, ex.getErrores().get(0).mensaje());
            verify(biblioRepo, never()).actualizar(anyLong(), any());
        }

        @Test
        void actualizarTiempoJuego_CuandoHorasNoPositivas_LanzaExcepcionValidacion() {
            BibliotecaEntidad biblioteca = new BibliotecaEntidad(idBiblioteca, idUsuario, idJuego, fecha, 10.0, null);
            when(biblioRepo.obtenerPorIdUsuarioYIdJuego(idUsuario, idJuego)).thenReturn(Optional.of(biblioteca));

            ExcepcionValidacion ex = assertThrows(ExcepcionValidacion.class,
                    () -> controlador.actualizarTiempoJuego(idUsuario, idJuego, -1.0));

            assertEquals(ErrorType.VALOR_NO_VALIDO, ex.getErrores().get(0).mensaje());
            verify(biblioRepo, never()).actualizar(anyLong(), any());
        }

        // -----------------------------------------------------------------
        // consultarUltimaSesion
        // -----------------------------------------------------------------
        @Test
        void consultarUltimaSesion_CuandoExiste_RetornaDTO() throws ExcepcionValidacion {
            LocalDate ultimaSesion = LocalDate.of(2024, 4, 15);
            BibliotecaEntidad biblioteca = new BibliotecaEntidad(idBiblioteca, idUsuario, idJuego, fecha, 20.0, ultimaSesion);
            when(biblioRepo.obtenerPorIdUsuarioYIdJuego(idUsuario, idJuego)).thenReturn(Optional.of(biblioteca));
            when(usuarioRepo.obtenerPorId(idUsuario)).thenReturn(Optional.of(usuario));
            when(juegoRepo.obtenerPorId(idJuego)).thenReturn(Optional.of(juego));

            try (MockedStatic<Mapper> mockedMapper = mockStatic(Mapper.class)) {
                mockedMapper.when(() -> Mapper.mapDeUsuario(usuario)).thenReturn(usuarioDTO);
                mockedMapper.when(() -> Mapper.mapDeJuego(juego)).thenReturn(juegoDTO);
                mockedMapper.when(() -> Mapper.mapDeBiblioteca(biblioteca, usuarioDTO, juegoDTO))
                        .thenReturn(bibliotecaDTO);

                BibliotecaDTO resultado = controlador.consultarUltimaSesion(idUsuario, idJuego);

                assertNotNull(resultado);
                assertEquals(ultimaSesion, resultado.getFechaUltimaJugado());
            }
        }

        @Test
        void consultarUltimaSesion_CuandoNoExiste_LanzaExcepcionValidacion() {
            when(biblioRepo.obtenerPorIdUsuarioYIdJuego(idUsuario, idJuego)).thenReturn(Optional.empty());

            ExcepcionValidacion ex = assertThrows(ExcepcionValidacion.class,
                    () -> controlador.consultarUltimaSesion(idUsuario, idJuego));

            assertEquals(ErrorType.NO_ENCONTRADO, ex.getErrores().get(0).mensaje());
        }
}
