package org.ainhoamarfer.controlador;

import org.ainhoamarfer.excepciones.ExcepcionValidacion;
import org.ainhoamarfer.modelo.dtos.JuegoDTO;
import org.ainhoamarfer.modelo.entidad.JuegoEntidad;
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

        // Crear juego existente
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

        JuegoEntidad juego2 = new JuegoEntidad(
                2L,
                "Cyberpunk 2077",
                "Un juego de rol futurista",
                "CD Projekt Red",
                LocalDate.of(2020, 12, 10),
                59.99,
                15.0,
                "RPG",
                "Español,Inglés",
                JuegoClasificacionEdad.PEGI_18
        );

        // Crear lista de juegos
        listaJuegos = new ArrayList<>();
        listaJuegos.add(juegoExistente);
        listaJuegos.add(juego2);
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

   // @Test
   // void testConsultarCatalogoExitoso() throws ExcepcionValidacion {
   //     // Arrange
   //     when(vista.menu()).thenReturn(0);
   //     when(juegoRepo.obtenerTodos()).thenReturn(listaJuegos);
//
   //     // Act
   //     List<JuegoDTO> resultado = juegoControlador.consultarDetallesJuego(juegoExistente.getId());
//
   //     // Assert
   //     assertNotNull(resultado);
   //     assertFalse(resultado.isEmpty());
   //     assertEquals(2, resultado.size());
   //     verify(juegoRepo, times(1)).obtenerTodos();
   // }

   // @Test
   // void testConsultarCatalogoCatalogoVacio() {
   //     // Arrange
   //     when(vista.menu()).thenReturn(0);
   //     when(juegoRepo.obtenerTodos()).thenReturn(new ArrayList<>());
//
   //     // Act
   //     List<JuegoDTO> resultado = juegoControlador.consultarCatalogo(null);
//
   //     // Assert
   //     assertNotNull(resultado);
   //     assertTrue(resultado.isEmpty());
   //     verify(juegoRepo, times(1)).obtenerTodos();
   // }

}

