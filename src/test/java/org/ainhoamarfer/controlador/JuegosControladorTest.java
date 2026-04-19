package org.ainhoamarfer.controlador;

import org.ainhoamarfer.excepciones.ExcepcionValidacion;
import org.ainhoamarfer.mapper.Mapper;
import org.ainhoamarfer.modelo.dtos.JuegoDTO;
import org.ainhoamarfer.modelo.entidad.JuegoEntidad;
import org.ainhoamarfer.modelo.enums.CriterioOrdenacionJuegosEnum;
import org.ainhoamarfer.modelo.enums.JuegoClasificacionEdad;
import org.ainhoamarfer.modelo.enums.JuegoEstado;
import org.ainhoamarfer.modelo.form.JuegoForm;
import org.ainhoamarfer.repositorio.interfaz.IJuegosRepo;
import org.ainhoamarfer.vista.SteamVista;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JuegosControladorTest {

    @Mock
    private IJuegosRepo juegoRepo;

    @Mock
    private SteamVista vista;

    @InjectMocks
    private JuegosControlador juegoControlador;

    private JuegoForm formularioValido;
    private JuegoEntidad juegoExistente;
    private List<JuegoEntidad> listaJuegos;

    @BeforeEach
    void setUp() {
        // Crear formulario válido
        formularioValido = new JuegoForm(
                "The Witcher 3",
                "Un juego de rol de acción",
                "CD Projekt Red",
                LocalDate.of(2015, 5, 19),
                59.99,
                0.0,
                "RPG",
                "Español,Inglés",
                JuegoClasificacionEdad.PEGI_18,
                JuegoEstado.DISPONIBLE
        );

        // Crear juego existente (The Witcher 3)
        juegoExistente = new JuegoEntidad(
                1L,
                "The Witcher 3",
                "Un juego de rol de acción",
                "CD Projekt Red",
                LocalDate.of(2015, 5, 19),
                59.99,
                0.0,
                "RPG",
                "Español,Inglés",
                JuegoClasificacionEdad.PEGI_18
        );

        // Crear juego2 (Cyberpunk 2077)
        JuegoEntidad juego2 = new JuegoEntidad(
                2L,
                "Cyberpunk 2077",
                "Un juego de rol futurista",
                "CD Projekt Red",
                LocalDate.of(2020, 12, 10),
                59.99,      // Mismo precio que The Witcher 3, para no afectar orden por precio
                15.0,
                "RPG",
                "Español,Inglés",
                JuegoClasificacionEdad.PEGI_18
        );

        // Crear juego3 (Animal Crossing: New Horizons) - título, precio y fecha distintos
        JuegoEntidad juego3 = new JuegoEntidad(
                3L,
                "Animal Crossing: New Horizons",
                "Simulador de vida en una isla",
                "Nintendo",
                LocalDate.of(2020, 3, 20),
                49.99,      // Precio intermedio
                10.0,
                "Simulación",
                "Inglés",
                JuegoClasificacionEdad.PEGI_3
        );

        // Crear lista de juegos con los tres juegos
        listaJuegos = new ArrayList<>();
        listaJuegos.add(juegoExistente);
        listaJuegos.add(juego2);
        listaJuegos.add(juego3);

    }

    // ============ PRUEBAS: anadirJuego ============

    @Test
    void testAnadirJuegoExitoso() throws ExcepcionValidacion {
        // Arrange
        when(juegoRepo.obtenerPorTitulo(formularioValido.getTitulo()))
                .thenReturn(Optional.empty());
        when(juegoRepo.crear(any(JuegoForm.class)))
                .thenReturn(Optional.of(juegoExistente));

        // Act
        JuegoDTO resultado = juegoControlador.anadirJuego(formularioValido);

        // Assert
        assertNotNull(resultado);
        assertEquals("The Witcher 3", resultado.getTitulo());
        assertEquals("CD Projekt Red", resultado.getDesarrollador());
        verify(juegoRepo, times(1)).obtenerPorTitulo(formularioValido.getTitulo());
        verify(juegoRepo, times(1)).crear(any(JuegoForm.class));
    }

    @Test
    void testAnadirJuegoDuplicado() {
        // Arrange
        when(juegoRepo.obtenerPorTitulo(formularioValido.getTitulo()))
                .thenReturn(Optional.of(juegoExistente));

        // Act & Assert
        assertThrows(ExcepcionValidacion.class, () -> {
            juegoControlador.anadirJuego(formularioValido);
        });

        verify(juegoRepo, times(1)).obtenerPorTitulo(formularioValido.getTitulo());
        verify(juegoRepo, never()).crear(any(JuegoForm.class));
    }

    @Test
    void testAnadirJuegoCreacionRetornaVacio() throws ExcepcionValidacion {
        // Arrange
        when(juegoRepo.obtenerPorTitulo(formularioValido.getTitulo()))
                .thenReturn(Optional.empty());
        when(juegoRepo.crear(any(JuegoForm.class)))
                .thenReturn(Optional.empty());

        // Act
        JuegoDTO resultado = juegoControlador.anadirJuego(formularioValido);

        // Assert
        assertNull(resultado);
        verify(juegoRepo, times(1)).crear(any(JuegoForm.class));
    }

    // ============ PRUEBAS: consultarDetallesJuego ============

    @Test
    void testConsultarDetallesJuegoExitoso() throws ExcepcionValidacion {
        // Arrange
        when(juegoRepo.obtenerPorId(1L))
                .thenReturn(Optional.of(juegoExistente));

        // Act
        JuegoDTO resultado = juegoControlador.consultarDetallesJuego(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals("The Witcher 3", resultado.getTitulo());
        assertEquals(59.99, resultado.getPrecioBase());
        verify(juegoRepo, times(1)).obtenerPorId(1L);
    }

    @Test
    void testConsultarDetallesJuegoNoExiste() {
        // Arrange
        when(juegoRepo.obtenerPorId(999L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ExcepcionValidacion.class, () -> {
            juegoControlador.consultarDetallesJuego(999L);
        });

        verify(juegoRepo, times(1)).obtenerPorId(999L);
    }

    // ============ PRUEBAS: aplicarDescuento ============

    @Test
    void testAplicarDescuentoExitoso() throws ExcepcionValidacion {
        // Arrange
        double porcentaje = 25.0;

        when(juegoRepo.obtenerPorId(1L))
                .thenReturn(Optional.of(juegoExistente));
        when(juegoRepo.actualizar(anyLong(), any(JuegoForm.class)))
                .thenReturn(Optional.of(juegoExistente));

        // Act
        JuegoDTO resultado = juegoControlador.aplicarDescuento(1L, porcentaje);

        // Assert
        assertNotNull(resultado);
        assertEquals("The Witcher 3", resultado.getTitulo());
        verify(juegoRepo, times(1)).obtenerPorId(1L);
        verify(juegoRepo, times(1)).actualizar(anyLong(), any(JuegoForm.class));
    }

    @Test
    void testAplicarDescuentoJuegoNoExiste() {
        // Arrange
        double porcentaje = 25.0;

        when(juegoRepo.obtenerPorId(999L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ExcepcionValidacion.class, () -> {
            juegoControlador.aplicarDescuento(999L, porcentaje);
        });

        verify(juegoRepo, times(1)).obtenerPorId(999L);
        verify(juegoRepo, never()).actualizar(anyLong(), any(JuegoForm.class));
    }

    @Test
    void testAplicarDescuentoActualizacionFalla() {
        // Arrange
        double porcentaje = 25.0;

        when(juegoRepo.obtenerPorId(1L))
                .thenReturn(Optional.of(juegoExistente));
        when(juegoRepo.actualizar(anyLong(), any(JuegoForm.class)))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ExcepcionValidacion.class, () -> {
            juegoControlador.aplicarDescuento(1L, porcentaje);
        });

        verify(juegoRepo, times(1)).actualizar(anyLong(), any(JuegoForm.class));
    }

    // ============ PRUEBAS: cambiarEstadoJuego ============

    @Test
    void testCambiarEstadoJuegoExitoso() throws ExcepcionValidacion {
        // Arrange
        String nuevoEstado = "NO_DISPONIBLE";

        when(juegoRepo.obtenerPorId(1L))
                .thenReturn(Optional.of(juegoExistente));
        when(juegoRepo.actualizar(anyLong(), any(JuegoForm.class)))
                .thenReturn(Optional.of(juegoExistente));

        // Act
        JuegoDTO resultado = juegoControlador.cambiarEstadoJuego(1L, nuevoEstado);

        // Assert
        assertNotNull(resultado);
        assertEquals("The Witcher 3", resultado.getTitulo());
        verify(juegoRepo, times(1)).obtenerPorId(1L);
        verify(juegoRepo, times(1)).actualizar(anyLong(), any(JuegoForm.class));
    }

    @Test
    void testCambiarEstadoJuegoNoExiste() {
        // Arrange
        String nuevoEstado = "NO_DISPONIBLE";

        when(juegoRepo.obtenerPorId(999L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ExcepcionValidacion.class, () -> {
            juegoControlador.cambiarEstadoJuego(999L, nuevoEstado);
        });

        verify(juegoRepo, times(1)).obtenerPorId(999L);
        verify(juegoRepo, never()).actualizar(anyLong(), any(JuegoForm.class));
    }

    @Test
    void testCambiarEstadoJuegoActualizacionFalla() {
        // Arrange
        String nuevoEstado = "NO_DISPONIBLE";

        when(juegoRepo.obtenerPorId(1L))
                .thenReturn(Optional.of(juegoExistente));
        when(juegoRepo.actualizar(anyLong(), any(JuegoForm.class)))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ExcepcionValidacion.class, () -> {
            juegoControlador.cambiarEstadoJuego(1L, nuevoEstado);
        });

        verify(juegoRepo, times(1)).actualizar(anyLong(), any(JuegoForm.class));
    }

    @Test
    void testCambiarEstadoJuegoEstadoInvalido() {
        // Arrange
        String estadoInvalido = "ESTADO_INEXISTENTE";

        when(juegoRepo.obtenerPorId(1L))
                .thenReturn(Optional.of(juegoExistente));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            juegoControlador.cambiarEstadoJuego(1L, estadoInvalido);
        });
    }

    // ============ PRUEBAS: consultarCatalogo ============

    @Test
    void testConsultarCatalogo_SinCriterioOrdenacion_RetornaListaOriginal() {
        when(juegoRepo.obtenerTodos()).thenReturn(listaJuegos);
        try (MockedStatic<Mapper> mockedMapper = mockStatic(Mapper.class)) {
            mockedMapper.when(() -> Mapper.mapDeJuego(any(JuegoEntidad.class)))
                    .thenAnswer(inv -> {
                        JuegoEntidad ent = inv.getArgument(0);
                        return new JuegoDTO(
                                ent.getId(), ent.getTitulo(), "", "", null, 0, 0, "", "", null, null
                        );
                    });

            List<JuegoDTO> resultado = juegoControlador.consultarCatalogo(null);

            assertEquals(3, resultado.size());
            // Orden original: The Witcher 3, Cyberpunk 2077, Animal Crossing
            assertEquals("The Witcher 3", resultado.get(0).getTitulo());
            assertEquals("Cyberpunk 2077", resultado.get(1).getTitulo());
            assertEquals("Animal Crossing: New Horizons", resultado.get(2).getTitulo());
            verify(juegoRepo, times(1)).obtenerTodos();
        }
    }

    @Test
    void testConsultarCatalogo_OrdenAlfabeticoAZ() {
        when(juegoRepo.obtenerTodos()).thenReturn(listaJuegos);
        try (MockedStatic<Mapper> mockedMapper = mockStatic(Mapper.class)) {
            mockedMapper.when(() -> Mapper.mapDeJuego(any(JuegoEntidad.class)))
                    .thenAnswer(inv -> {
                        JuegoEntidad ent = inv.getArgument(0);
                        return new JuegoDTO(
                                ent.getId(), ent.getTitulo(), "", "", null, 0, 0, "", "", null, null
                        );
                    });

            List<JuegoDTO> resultado = juegoControlador.consultarCatalogo(CriterioOrdenacionJuegosEnum.ALFABETICO_A_Z);

            assertEquals(3, resultado.size());
            // Orden A-Z: Animal Crossing, Cyberpunk 2077, The Witcher 3
            assertEquals("Animal Crossing: New Horizons", resultado.get(0).getTitulo());
            assertEquals("Cyberpunk 2077", resultado.get(1).getTitulo());
            assertEquals("The Witcher 3", resultado.get(2).getTitulo());
        }
    }

    @Test
    void testConsultarCatalogo_OrdenAlfabeticoZA() {
        when(juegoRepo.obtenerTodos()).thenReturn(listaJuegos);
        try (MockedStatic<Mapper> mockedMapper = mockStatic(Mapper.class)) {
            mockedMapper.when(() -> Mapper.mapDeJuego(any(JuegoEntidad.class)))
                    .thenAnswer(inv -> {
                        JuegoEntidad ent = inv.getArgument(0);
                        return new JuegoDTO(
                                ent.getId(), ent.getTitulo(), "", "", null, 0, 0, "", "", null, null
                        );
                    });

            List<JuegoDTO> resultado = juegoControlador.consultarCatalogo(CriterioOrdenacionJuegosEnum.ALFABETICO_Z_A);

            assertEquals(3, resultado.size());
            // Orden Z-A: The Witcher 3, Cyberpunk 2077, Animal Crossing
            assertEquals("The Witcher 3", resultado.get(0).getTitulo());
            assertEquals("Cyberpunk 2077", resultado.get(1).getTitulo());
            assertEquals("Animal Crossing: New Horizons", resultado.get(2).getTitulo());
        }
    }

    @Test
    void testConsultarCatalogo_OrdenPrecioMenorAMayor() {
        when(juegoRepo.obtenerTodos()).thenReturn(listaJuegos);
        try (MockedStatic<Mapper> mockedMapper = mockStatic(Mapper.class)) {
            mockedMapper.when(() -> Mapper.mapDeJuego(any(JuegoEntidad.class)))
                    .thenAnswer(inv -> {
                        JuegoEntidad ent = inv.getArgument(0);
                        return new JuegoDTO(
                                ent.getId(), ent.getTitulo(), "", "", null,
                                ent.getPrecioBase(), 0, "", "", null, null
                        );
                    });

            List<JuegoDTO> resultado = juegoControlador.consultarCatalogo(CriterioOrdenacionJuegosEnum.PRECIO_MENOR_A_MAYOR);

            assertEquals(3, resultado.size());
            // Precios: Animal Crossing 49.99, The Witcher 59.99, Cyberpunk 59.99
            // Nota: Cyberpunk y The Witcher tienen mismo precio, el orden entre ellos puede ser cualquiera.
            // Comprobamos los títulos esperados según el precio.
            assertEquals("Animal Crossing: New Horizons", resultado.get(0).getTitulo());
            // Los dos siguientes pueden aparecer en cualquier orden debido al mismo precio.
            // Verificamos que ambos estén presentes.
            List<String> titulos = List.of(resultado.get(1).getTitulo(), resultado.get(2).getTitulo());
            assertTrue(titulos.contains("The Witcher 3"));
            assertTrue(titulos.contains("Cyberpunk 2077"));
        }
    }

    @Test
    void testConsultarCatalogo_OrdenPrecioMayorAMenor() {
        when(juegoRepo.obtenerTodos()).thenReturn(listaJuegos);
        try (MockedStatic<Mapper> mockedMapper = mockStatic(Mapper.class)) {
            mockedMapper.when(() -> Mapper.mapDeJuego(any(JuegoEntidad.class)))
                    .thenAnswer(inv -> {
                        JuegoEntidad ent = inv.getArgument(0);
                        return new JuegoDTO(
                                ent.getId(), ent.getTitulo(), "", "", null,
                                ent.getPrecioBase(), 0, "", "", null, null
                        );
                    });

            List<JuegoDTO> resultado = juegoControlador.consultarCatalogo(CriterioOrdenacionJuegosEnum.PRECIO_MAYOR_A_MENOR);

            assertEquals(3, resultado.size());
            // Precios: The Witcher 59.99, Cyberpunk 59.99, Animal Crossing 49.99
            // Los dos primeros pueden alternar.
            List<String> titulosCaros = List.of(resultado.get(0).getTitulo(), resultado.get(1).getTitulo());
            assertTrue(titulosCaros.contains("The Witcher 3"));
            assertTrue(titulosCaros.contains("Cyberpunk 2077"));
            assertEquals("Animal Crossing: New Horizons", resultado.get(2).getTitulo());
        }
    }

    @Test
    void testConsultarCatalogo_OrdenFechaMasReciente() {
        when(juegoRepo.obtenerTodos()).thenReturn(listaJuegos);
        try (MockedStatic<Mapper> mockedMapper = mockStatic(Mapper.class)) {
            mockedMapper.when(() -> Mapper.mapDeJuego(any(JuegoEntidad.class)))
                    .thenAnswer(inv -> {
                        JuegoEntidad ent = inv.getArgument(0);
                        return new JuegoDTO(
                                ent.getId(), ent.getTitulo(), "", "", ent.getFechaLanzamiento(),
                                0, 0, "", "", null, null
                        );
                    });

            List<JuegoDTO> resultado = juegoControlador.consultarCatalogo(CriterioOrdenacionJuegosEnum.FECHA_MAS_RECIENTE);

            assertEquals(3, resultado.size());
            // Fechas: Cyberpunk 2020-12-10, Animal Crossing 2020-03-20, The Witcher 2015-05-19
            assertEquals("Cyberpunk 2077", resultado.get(0).getTitulo());
            assertEquals("Animal Crossing: New Horizons", resultado.get(1).getTitulo());
            assertEquals("The Witcher 3", resultado.get(2).getTitulo());
        }
    }

    @Test
    void testConsultarCatalogo_OrdenFechaMasAntigua() {
        when(juegoRepo.obtenerTodos()).thenReturn(listaJuegos);
        try (MockedStatic<Mapper> mockedMapper = mockStatic(Mapper.class)) {
            mockedMapper.when(() -> Mapper.mapDeJuego(any(JuegoEntidad.class)))
                    .thenAnswer(inv -> {
                        JuegoEntidad ent = inv.getArgument(0);
                        return new JuegoDTO(
                                ent.getId(), ent.getTitulo(), "", "", ent.getFechaLanzamiento(),
                                0, 0, "", "", null, null
                        );
                    });

            List<JuegoDTO> resultado = juegoControlador.consultarCatalogo(CriterioOrdenacionJuegosEnum.FECHA_MAS_ANTIGUA);

            assertEquals(3, resultado.size());
            // Fechas: The Witcher 2015-05-19, Animal Crossing 2020-03-20, Cyberpunk 2020-12-10
            assertEquals("The Witcher 3", resultado.get(0).getTitulo());
            assertEquals("Animal Crossing: New Horizons", resultado.get(1).getTitulo());
            assertEquals("Cyberpunk 2077", resultado.get(2).getTitulo());
        }
    }
}