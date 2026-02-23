package org.ainhoamarfer.Controlador;

import org.ainhoamarfer.Mapper.Mapper;
import org.ainhoamarfer.Modelo.Entidad.UsuarioEntidad;
import org.ainhoamarfer.Modelo.Enums.CriterioOrdenacionJuegosEnum;
import org.ainhoamarfer.Modelo.Form.CriteriosBusquedaForm;
import org.ainhoamarfer.Modelo.DTOs.ErrorDTO;
import org.ainhoamarfer.Modelo.Entidad.JuegoEntidad;
import org.ainhoamarfer.Modelo.Enums.ErrorType;
import org.ainhoamarfer.Modelo.Form.JuegoForm;
import org.ainhoamarfer.Repositorio.Interfaz.IJuegosRepo;
import org.ainhoamarfer.Vista.SteamVista;

import java.util.*;
import java.util.stream.Collectors;

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

        repo.obtenerPorTitulo(form.getTitulo())
                .ifPresent(u -> errores.add(new ErrorDTO("nombre", ErrorType.DUPLICADO)));

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
     * Datos mostrados: Título, desarrollador, precio base, descuento actual, clasificación.
     *
     * @param criterioOrdenacion Criterio de ordenación opcional: alfabetico, precio, fecha (puede ser null).
     * @return Lista de `JuegoDTO` con información básica. Metadatos de paginación
     */
    public List<JuegoDTO> consultarCatalogo(CriterioOrdenacionJuegosEnum criterioOrdenacion) {
        int op;
        op = vista.menu();
        CriterioOrdenacionJuegosEnum opcion = CriterioOrdenacionJuegosEnum.values()[op];

        List<JuegoEntidad> juegos = repo.obtenerTodos();

        //De la A a la Z
        if(opcion == CriterioOrdenacionJuegosEnum.ALFABETICO_A_Z){
            juegos.stream().sorted(Comparator.comparing(JuegoEntidad::getTitulo))
                    .toList();
        }

        //De la Z a la A
        if(opcion == CriterioOrdenacionJuegosEnum.ALFABETICO_Z_A){
            juegos.stream().sorted(Comparator.comparing(JuegoEntidad::getTitulo)
                    .reversed())
                    .toList();
        }

        //De menor a mayor precio
        if(opcion == CriterioOrdenacionJuegosEnum.PRECIO_MENOR_A_MAYOR){
            juegos.stream().sorted(Comparator.comparing(JuegoEntidad::getPrecioBase))
                    .toList();
        }

        //De mayor a menor precio
        if(opcion == CriterioOrdenacionJuegosEnum.PRECIO_MAYOR_A_MENOR){
            juegos.stream().sorted(Comparator.comparing(JuegoEntidad::getPrecioBase)
                    .reversed())
                    .toList();
        }

        if(opcion == CriterioOrdenacionJuegosEnum.FECHA_MAS_RECIENTE){
            juegos.stream().sorted(Comparator.comparing(JuegoEntidad::getFechaLanzamiento))
                    .toList();
        }

        if(opcion == CriterioOrdenacionJuegosEnum.FECHA_MAS_ANTIGUA){
            juegos.stream().sorted(Comparator.comparing(JuegoEntidad::getFechaLanzamiento).reversed())
                    .toList();
        }

        List<JuegoDTO> juegosEncontrados = new ArrayList<>();
        for (JuegoEntidad juego : juegos) {
            juegosEncontrados.add(Mapper.mapDeJuego(juego));
        }
        return juegosEncontrados;
    }

    /**
     * Consultar detalles de juego
     * Descripción: Mostrar toda la información completa de un juego específico.
     * Datos mostrados: Todos los campos del juego más TODO estadísticas y reseñas destacadas.
     *
     * @param id ID del juego a consultar.
     * @return `JuegoDTO` con la información
     * @throws IllegalArgumentException si no existe un juego con el ID proporcionado.
     */
    public JuegoDTO consultarDetallesJuego(Long id) throws ExcepcionValidacion {
        List<ErrorDTO> errores = new ArrayList<>();
        Optional<JuegoEntidad> juego = repo.obtenerPorId(id);

        if(juego.isEmpty()){
           errores.add(new ErrorDTO("juego", ErrorType.NO_ENCONTRADO));
            throw new ExcepcionValidacion(errores);
        }

        JuegoEntidad juegoAConsultar = juego.orElse(null);

        return Mapper.mapDeJuego(juegoAConsultar);
    }

    /**
     * Aplicar descuento
     * Descripción: Establecer un porcentaje de descuento temporal a un juego.
     * Validaciones: Juego existe, descuento en rango válido (0-100).
     *
     * @param id ID del juego al que aplicar el descuento.(0-100).
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
