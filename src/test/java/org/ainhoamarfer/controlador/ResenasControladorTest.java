package org.ainhoamarfer.controlador;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.ainhoamarfer.excepciones.ExcepcionValidacion;
import org.ainhoamarfer.mapper.Mapper;
import org.ainhoamarfer.modelo.dtos.ErrorDTO;
import org.ainhoamarfer.modelo.dtos.ResenaDTO;
import org.ainhoamarfer.modelo.entidad.BibliotecaEntidad;
import org.ainhoamarfer.modelo.entidad.ResenaEntidad;
import org.ainhoamarfer.modelo.enums.ErrorType;
import org.ainhoamarfer.modelo.enums.ResenaEstado;
import org.ainhoamarfer.modelo.form.ResenaForm;
import org.ainhoamarfer.repositorio.interfaz.IBibliotecaRepo;
import org.ainhoamarfer.repositorio.interfaz.IResenaRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ResenasControladorTest {

@Mock
private IResenaRepo repoResena;

    @Mock
    private IBibliotecaRepo repoBiblioteca;

    @InjectMocks
    private ResenasControlador controlador;

    private final long idUsuario = 1L;
    private final long idJuego = 100L;
    private final long idResena = 10L;
    private final boolean recomendado = true;
    private final String texto = "Excelente juego";
    private final double horasJugadas = 120.5;
    private final LocalDate fecha = LocalDate.now();

    private BibliotecaEntidad biblioteca;
    private ResenaEntidad resenaEntidad;
    private ResenaDTO resenaDTO;

    @BeforeEach
    void setUp() {
        // Mock de BibliotecaEntidad (solo necesitamos getTiempoJuego)
        biblioteca = mock(BibliotecaEntidad.class);
        lenient().when(biblioteca.getTiempoJuego()).thenReturn((double) horasJugadas);

        // ResenaEntidad usando constructor real
        resenaEntidad = new ResenaEntidad(
                idResena,
                idUsuario,
                idJuego,
                recomendado,
                texto,
                horasJugadas,
                fecha,
                fecha,
                ResenaEstado.PUBLICADA
        );

        // ResenaDTO: asumimos constructor con todos los campos.
        // Si tu clase ResenaDTO no tiene este constructor, puedes crear un mock y definir sus getters.
        resenaDTO = new ResenaDTO(
                idResena,
                idUsuario,
                idJuego,
                recomendado,
                texto,
                horasJugadas,
                fecha,
                fecha,
                ResenaEstado.PUBLICADA
        );
    }

    // -----------------------------------------------------------------
    // Tests para escribirResena
    // -----------------------------------------------------------------
    @Test
    void escribirResena_CuandoBibliotecaExiste_DeberiaCrearYRetornarDTO() throws ExcepcionValidacion {
        when(repoBiblioteca.obtenerPorIdUsuarioYIdJuego(idUsuario, idJuego))
                .thenReturn(Optional.of(biblioteca));
        when(repoResena.crear(any(ResenaForm.class))).thenReturn(Optional.of(resenaEntidad));

        try (MockedStatic<Mapper> mockedMapper = mockStatic(Mapper.class)) {
            mockedMapper.when(() -> Mapper.mapDeResena(resenaEntidad)).thenReturn(resenaDTO);

            ResenaDTO resultado = controlador.escribirResena(idUsuario, idJuego, recomendado, texto);

            assertNotNull(resultado);
            assertEquals(idResena, resultado.getId());
            verify(repoBiblioteca).obtenerPorIdUsuarioYIdJuego(idUsuario, idJuego);
            verify(repoResena).crear(any(ResenaForm.class));
        }
    }

    @Test
    void escribirResena_CuandoBibliotecaNoExiste_DeberiaLanzarExcepcionValidacion() {
        when(repoBiblioteca.obtenerPorIdUsuarioYIdJuego(idUsuario, idJuego))
                .thenReturn(Optional.empty());

        ExcepcionValidacion ex = assertThrows(ExcepcionValidacion.class,
                () -> controlador.escribirResena(idUsuario, idJuego, recomendado, texto));

        List<ErrorDTO> errores = ex.getErrores();
        assertEquals(1, errores.size());
        assertEquals("Biblioteca", errores.get(0).campo());
        assertEquals(ErrorType.NO_ENCONTRADO, errores.get(0).mensaje());

        verify(repoResena, never()).crear(any());
    }

    // -----------------------------------------------------------------
    // Tests para eliminarResena
    // -----------------------------------------------------------------
    @Test
    void eliminarResena_CuandoExisteYPertenece_DeberiaEliminarYRetornarMensaje() throws ExcepcionValidacion {
        when(repoResena.obtenerPorId(idResena)).thenReturn(Optional.of(resenaEntidad));
        doNothing().when(repoResena).eliminar(idResena);

        String resultado = controlador.eliminarResena(idResena, idUsuario);

        assertEquals("Reseña eliminada exitosamente", resultado);
        verify(repoResena).eliminar(idResena);
    }

    @Test
    void eliminarResena_CuandoNoExiste_DeberiaLanzarExcepcionValidacion() {
        when(repoResena.obtenerPorId(idResena)).thenReturn(Optional.empty());

        ExcepcionValidacion ex = assertThrows(ExcepcionValidacion.class,
                () -> controlador.eliminarResena(idResena, idUsuario));

        List<ErrorDTO> errores = ex.getErrores();
        assertEquals(1, errores.size());
        assertEquals("reseña", errores.get(0).campo());
        assertEquals(ErrorType.NO_ENCONTRADO, errores.get(0).mensaje());

        verify(repoResena, never()).eliminar(anyLong());
    }

    @Test
    void eliminarResena_CuandoNoPerteneceAlUsuario_DeberiaLanzarExcepcionValidacion() {
        long otroUsuario = 999L;
        when(repoResena.obtenerPorId(idResena)).thenReturn(Optional.of(resenaEntidad));

        ExcepcionValidacion ex = assertThrows(ExcepcionValidacion.class,
                () -> controlador.eliminarResena(idResena, otroUsuario));

        List<ErrorDTO> errores = ex.getErrores();
        assertEquals(1, errores.size());
        assertEquals("reseña", errores.get(0).campo());
        assertEquals(ErrorType.NO_PERTENECE_AL_USUARIO, errores.get(0).mensaje());

        verify(repoResena, never()).eliminar(anyLong());
    }

    // -----------------------------------------------------------------
    // Tests para verResenasJuego
    // -----------------------------------------------------------------
    @Test
    void verResenasJuego_SinFiltroYTodas_DeberiaRetornarListaOrdenadaPorFechaDescendente() {
        ResenaEntidad antigua = crearResenaEntidad(1L, fecha.minusDays(5), true);
        ResenaEntidad reciente = crearResenaEntidad(2L, fecha, true);
        when(repoResena.obtenerTodos()).thenReturn(List.of(antigua, reciente));

        try (MockedStatic<Mapper> mockedMapper = mockStatic(Mapper.class)) {
            mockedMapper.when(() -> Mapper.mapDeResena(any(ResenaEntidad.class)))
                    .thenAnswer(inv -> {
                        ResenaEntidad ent = inv.getArgument(0);
                        return new ResenaDTO(
                                ent.getId(), 0, 0, false, "", 0,
                                ent.getFechaPublicacion(), null, null
                        );
                    });

            List<ResenaDTO> resultado = controlador.verResenasJuego(idJuego, "", "recientes");

            assertEquals(2, resultado.size());
            assertEquals(2L, resultado.get(0).getId()); // más reciente primero
        }
    }

    @Test
    void verResenasJuego_ConFiltroPositivas_DeberiaRetornarSoloRecomendadas() {
        ResenaEntidad positiva = crearResenaEntidad(1L, fecha, true);
        ResenaEntidad negativa = crearResenaEntidad(2L, fecha, false);
        when(repoResena.obtenerTodos()).thenReturn(List.of(positiva, negativa));

        try (MockedStatic<Mapper> mockedMapper = mockStatic(Mapper.class)) {
            mockedMapper.when(() -> Mapper.mapDeResena(any(ResenaEntidad.class)))
                    .thenAnswer(inv -> {
                        ResenaEntidad ent = inv.getArgument(0);
                        return new ResenaDTO(
                                ent.getId(), 0, 0, ent.isRecomendado(), "", 0,
                                null, null, null
                        );
                    });

            List<ResenaDTO> resultado = controlador.verResenasJuego(idJuego, "positivas", "");

            assertEquals(1, resultado.size());
            assertTrue(resultado.get(0).isRecomendado());
        }
    }

    // -----------------------------------------------------------------
    // Tests para ocultarResena
    // -----------------------------------------------------------------
    @Test
    void ocultarResena_CuandoExistePerteneceYNoEstaOculta_DeberiaActualizarEstado() throws ExcepcionValidacion {
        when(repoResena.obtenerPorId(idResena)).thenReturn(Optional.of(resenaEntidad));
        doNothing().when(repoResena).actualizarEstadoResena(idResena, ResenaEstado.OCULTA);

        String resultado = controlador.ocultarResena(idResena, idUsuario);

        assertEquals("Reseña cambiada a no visible", resultado);
        verify(repoResena).actualizarEstadoResena(idResena, ResenaEstado.OCULTA);
    }

    @Test
    void ocultarResena_CuandoYaEstaOculta_DeberiaLanzarExcepcionValidacion() {
        ResenaEntidad oculta = new ResenaEntidad(
                idResena, idUsuario, idJuego, true, texto,
                horasJugadas, fecha, fecha, ResenaEstado.OCULTA
        );
        when(repoResena.obtenerPorId(idResena)).thenReturn(Optional.of(oculta));

        ExcepcionValidacion ex = assertThrows(ExcepcionValidacion.class,
                () -> controlador.ocultarResena(idResena, idUsuario));

        List<ErrorDTO> errores = ex.getErrores();
        assertEquals(1, errores.size());
        assertEquals("Reseña", errores.get(0).campo());
        assertEquals(ErrorType.NO_ENCONTRADO, errores.get(0).mensaje());

        verify(repoResena, never()).actualizarEstadoResena(anyLong(), any());
    }

    // -----------------------------------------------------------------
    // Tests para verResenasUsuario
    // -----------------------------------------------------------------
    @Test
    void verResenasUsuario_CuandoExistenYCoincideFiltro_DeberiaRetornarListaFiltrada() throws ExcepcionValidacion {
        ResenaEntidad publicada = crearResenaEntidad(1L, fecha, true);
        ResenaEntidad oculta = new ResenaEntidad(
                2L, idUsuario, idJuego, true, texto,
                horasJugadas, fecha, fecha, ResenaEstado.OCULTA
        );
        when(repoResena.obtenerPorIdUsuario(idUsuario))
                .thenReturn(Optional.of(publicada));

        try (MockedStatic<Mapper> mockedMapper = mockStatic(Mapper.class)) {
            mockedMapper.when(() -> Mapper.mapDeResena(any(ResenaEntidad.class)))
                    .thenAnswer(inv -> {
                        ResenaEntidad ent = inv.getArgument(0);
                        return new ResenaDTO(
                                ent.getId(), 0, 0, false, "", 0,
                                null, null, ent.getEstado()
                        );
                    });

            List<ResenaDTO> resultado = controlador.verResenasUsuario(idUsuario, "PUBLICADA");

            assertEquals(1, resultado.size());
            assertEquals(ResenaEstado.PUBLICADA, resultado.get(0).getEstado());
        }
    }

    @Test
    void verResenasUsuario_CuandoNoExistenResenas_DeberiaLanzarExcepcionValidacion() {
        when(repoResena.obtenerPorIdUsuario(idUsuario)).thenReturn(Optional.empty());

        ExcepcionValidacion ex = assertThrows(ExcepcionValidacion.class,
                () -> controlador.verResenasUsuario(idUsuario, "PUBLICADA"));

        List<ErrorDTO> errores = ex.getErrores();
        assertEquals(1, errores.size());
        assertEquals("Reseñas", errores.get(0).campo());
        assertEquals(ErrorType.NO_ENCONTRADO, errores.get(0).mensaje());
    }

    // -----------------------------------------------------------------
    // Test para consultarEstadisticasResenas
    // -----------------------------------------------------------------
    @Test
    void consultarEstadisticasResenas_DeberiaLanzarUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class,
                () -> controlador.consultarEstadisticasResenas(idJuego));
    }

    // -----------------------------------------------------------------
    // Método auxiliar
    // -----------------------------------------------------------------
    private ResenaEntidad crearResenaEntidad(long id, LocalDate fechaPublicacion, boolean recomendado) {
        return new ResenaEntidad(
                id,
                idUsuario,
                idJuego,
                recomendado,
                "Texto reseña " + id,
                horasJugadas,
                fechaPublicacion,
                fechaPublicacion,
                ResenaEstado.PUBLICADA
        );
    }
}
