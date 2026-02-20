package org.ainhoamarfer.Controlador;

import org.ainhoamarfer.Mapper.Mapper;
import org.ainhoamarfer.Modelo.Form.CriteriosBusquedaForm;
import org.ainhoamarfer.Modelo.DTOs.ErrorDTO;
import org.ainhoamarfer.Modelo.Entidad.JuegoEntidad;
import org.ainhoamarfer.Modelo.Enums.ErrorType;
import org.ainhoamarfer.Modelo.Form.JuegoForm;
import org.ainhoamarfer.Repositorio.Interfaz.IJuegosRepo;
import org.ainhoamarfer.Vista.SteamVista;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.ainhoamarfer.Modelo.DTOs.JuegoDTO;
import org.ainhoamarfer.Excepciones.ExcepcionValidacion;

public class JuegosControlador {

    /*
    Añadir juego al catálogo
    Buscar juegos
    Consultar catálogo completo
    Consultar detalles de juego
    Aplicar descuento
    Cambiar estado del juego
     */

    private IJuegosRepo repo;
    private CriteriosBusquedaForm criteriosBusqueda;
    private SteamVista vista;

    public JuegosControlador(IJuegosRepo repo, SteamVista vista) {
        this.repo = repo;
        this.vista = vista;
    }

    /**
     * Añadir juego al catálogo
     * Registrar un nuevo videojuego en el catálogo de Steam.
     *
     * @param form Datos del juego a crear
     * @return JuegoDTO con los datos y su ID.
     * @throws ExcepcionValidacion restricciones definidas en la sección de validación de Juego.
     */
    public JuegoDTO anadirJuego(JuegoForm form) throws ExcepcionValidacion {
        List<ErrorDTO> errores = form.validar(form);

        repo.obtenerPorTitulo(form.getTitulo()).ifPresent(u -> errores.add(new ErrorDTO("nombre", ErrorType.DUPLICADO)));

        if (!errores.isEmpty()) {
            throw new ExcepcionValidacion(errores);
        }

        Optional<JuegoEntidad> juegoOpt = repo.crear(form);
        JuegoEntidad juego = juegoOpt.orElse(null);

        return Mapper.mapDeJuego(juego);
    }


    /**
     * Buscar juegos
     * Descripción: Filtrar y buscar juegos en el catálogo según múltiples criterios.
     *
     * @param criterios criterios de filtro para la busqueda. Parámetros opcionales pueden ser null.
     * @return Lista de JuegoDTO con información resumida que cumplen los criterios.
     */
    public List<JuegoDTO> buscarJuegos(CriteriosBusquedaForm criterios) throws ExcepcionValidacion {
        List<ErrorDTO> errores = criteriosBusqueda.validar(criterios);

        if (!errores.isEmpty()) {
            throw new ExcepcionValidacion(errores);
        }

        List<JuegoEntidad> juegos = repo.obtenerTodos();

        List<JuegoEntidad> juegosCumplenCriterios = new ArrayList<>();
        for (JuegoEntidad juego : juegos) {
            if(Objects.equals(criterios.getCategoria(), juego.getCategoria()) || Objects.equals(criterios.getDescripcion(), juego.getDescripcion()) || Objects.equals(criterios.getDesarrollador(), juego.getDesarrollador())
            || criterios.getFechaLanzamiento() == juego.getFechaLanzamiento() || Objects.equals(criterios.getPrecioBase(), juego.getPrecioBase()) || Objects.equals(criterios.getTitulo(), juego.getTitulo())) {
                juegosCumplenCriterios.add(juego);
            }
        }

        List<JuegoDTO> juegosEncontrados = new ArrayList<>();
        for (JuegoEntidad juego : juegosCumplenCriterios) {
            juegosEncontrados.add(Mapper.mapDeJuego(juego));
        }

        return juegosEncontrados;
    }

    /**
     * Consultar catálogo completo
     * Listar todos los juegos disponibles en la plataforma.
     * Entrada: `orden` (opcional: "alfabetico", "precio", "fecha"), `pagina` (indexada en 0),
     *         `tamanoPagina` para paginación.
     * Salida: Lista paginada de juegos con información básica y metadatos de paginación.
     * Datos mostrados: Título, desarrollador, precio base, descuento actual, clasificación.
     *
     * @param orden Criterio de ordenación opcional: "alfabetico", "precio", "fecha" (puede ser null).
     * @param pagina Índice de página (0-based).
     * @param tamanoPagina Tamaño de la página (número de elementos por página).
     * @return Lista de `JuegoDTO` para la página solicitada (información básica). Metadatos de paginación
     *         se deben proporcionar por la capa de servicio o envoltorio de respuesta si se requiere.
     */
    public List<JuegoDTO> consultarCatalogo(String orden, int pagina, int tamanoPagina) {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Consultar detalles de juego
     * Descripción: Mostrar toda la información completa de un juego específico.
     * Entrada: `id` del juego.
     * Salida: Información completa del juego (`JuegoDTO`) o lanza `IllegalArgumentException`
     *         si no existe.
     * Datos mostrados: Todos los campos del juego más estadísticas y reseñas destacadas.
     *
     * @param id ID del juego a consultar.
     * @return `JuegoDTO` con la información completa del juego, estadísticas y reseñas destacadas.
     * @throws IllegalArgumentException si no existe un juego con el ID proporcionado.
     */
    public JuegoDTO consultarDetallesJuego(Long id) {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Aplicar descuento
     * Descripción: Establecer un porcentaje de descuento temporal a un juego.
     * Entrada: `id` del juego, `porcentaje` de descuento (0-100).
     * Salida: Juego con precio final actualizado (`JuegoDTO`) o lanza `ExcepcionValidacion`.
     * Validaciones: Juego existe, descuento en rango válido (0-100).
     *
     * @param id ID del juego al que aplicar el descuento.
     * @param porcentaje Porcentaje de descuento a aplicar (0-100).
     * @return `JuegoDTO` con el descuento aplicado y el precio final calculado.
     * @throws ExcepcionValidacion si el juego no existe o el porcentaje está fuera del rango permitido.
     */
    public JuegoDTO aplicarDescuento(Long id, double porcentaje) throws ExcepcionValidacion {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Cambiar estado del juego
     * Descripción: Modificar el estado de disponibilidad de un juego.
     * Entrada: `id` del juego, `nuevoEstado` (por ejemplo: "DISPONIBLE", "NO_DISPONIBLE", "PROXIMAMENTE").
     * Salida: Confirmación del cambio (retorna `JuegoDTO` actualizado) o lanza `ExcepcionValidacion`.
     * Validaciones: Juego existe, estado válido.
     *
     * @param id ID del juego cuyo estado se desea cambiar.
     * @param nuevoEstado Nuevo estado de disponibilidad (ej. "DISPONIBLE", "NO_DISPONIBLE", "PROXIMAMENTE").
     * @return `JuegoDTO` con el estado actualizado.
     * @throws ExcepcionValidacion si el juego no existe o el estado proporcionado no es válido.
     */
    public JuegoDTO cambiarEstadoJuego(Long id, String nuevoEstado) throws ExcepcionValidacion {
        throw new UnsupportedOperationException("Not implemented");
    }
}
