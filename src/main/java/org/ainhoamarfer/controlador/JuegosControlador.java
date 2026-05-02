package org.ainhoamarfer.controlador;

import org.ainhoamarfer.mapper.Mapper;
import org.ainhoamarfer.modelo.enums.CriterioOrdenacionJuegosEnum;
import org.ainhoamarfer.modelo.enums.JuegoEstado;
import org.ainhoamarfer.modelo.form.CriteriosBusquedaForm;
import org.ainhoamarfer.modelo.dtos.ErrorDTO;
import org.ainhoamarfer.modelo.entidad.JuegoEntidad;
import org.ainhoamarfer.modelo.enums.ErrorType;
import org.ainhoamarfer.modelo.form.JuegoForm;
import org.ainhoamarfer.repositorio.interfaz.IJuegosRepo;
import org.ainhoamarfer.transaction.ITransactionManager;

import java.util.*;
import java.util.stream.Collectors;

import org.ainhoamarfer.modelo.dtos.JuegoDTO;
import org.ainhoamarfer.excepciones.ExcepcionValidacion;

public class JuegosControlador {
    public static final int MIN_PORCENTAJE = 0;
    public static final int MAX_PORCENTAJE = 100;

    /*
    Añadir juego al catálogo
    Buscar juegos
    Consultar catálogo completo
    Consultar detalles de juego
    Aplicar descuento
    Cambiar estado del juego
     */

    private IJuegosRepo juegosrRepo;
    private CriteriosBusquedaForm criteriosBusqueda;
    //private ITransactionManager tm;

    public JuegosControlador(IJuegosRepo JuegosrRepo) {
        this.juegosrRepo = JuegosrRepo;
        //this.tm = tm;

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

        //comienza aquí la transacción porque antes no se necesita la base de datos
        //Optional<JuegoEntidad> juegoCreado = tm.inTransaction(() -> {
            Optional<JuegoEntidad> juegoOpt = juegosrRepo.obtenerPorTitulo(form.getTitulo());
            if (juegoOpt.isPresent()) {
                errores.add(new ErrorDTO("juego", ErrorType.DUPLICADO));
                //necesario lanzar esta excepcion para que se haga el rollback, si ocurre no ejecuta la lambda pero sigue el resto del métod
                throw new ExcepcionValidacion(errores);
            }

            //return juegosrRepo.crear(form);
        Optional <JuegoEntidad> juegoCreado = juegosrRepo.crear(form);
        //});

        if (!errores.isEmpty()) {
            throw new ExcepcionValidacion(errores);
        }

        JuegoEntidad juego = juegoCreado.orElse(null);

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
        List<JuegoEntidad> juegos = juegosrRepo.obtenerTodos();

        List<JuegoEntidad> juegosCumplenCriterios = new ArrayList<>();
        for (JuegoEntidad juego : juegos) {

            boolean coincide = false;
            if (criterios.getTitulo() != null && juego.getTitulo().toLowerCase().contains(criterios.getTitulo().toLowerCase())) {
                coincide = true;
            }
            if (criterios.getDescripcion() != null && juego.getDescripcion() != null && juego.getDescripcion().toLowerCase().contains(criterios.getDescripcion().toLowerCase())) {
                coincide = true;
            }
            if (criterios.getDesarrollador() != null && juego.getDesarrollador().toLowerCase().contains(criterios.getDesarrollador().toLowerCase())) {
                coincide = true;
            }
            if (criterios.getCategoria() != null && juego.getCategoria().equalsIgnoreCase(criterios.getCategoria())) {
                coincide = true;
            } // etc.
            if (coincide) juegosCumplenCriterios.add(juego);
        }

        return juegosCumplenCriterios.stream()
                .map(Mapper::mapDeJuego)
                .collect(Collectors.toList());
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
        List<JuegoEntidad> juegos = juegosrRepo.obtenerTodos();

        if (criterioOrdenacion != null) {
            switch (criterioOrdenacion) {
                case ALFABETICO_A_Z:
                    juegos = juegos.stream().sorted(Comparator.comparing(JuegoEntidad::getTitulo)).toList();
                    break;

                case ALFABETICO_Z_A:
                    juegos = juegos.stream().sorted(Comparator.comparing(JuegoEntidad::getTitulo).reversed()).toList();
                    break;

                case PRECIO_MENOR_A_MAYOR:
                    juegos = juegos.stream().sorted(Comparator.comparing(JuegoEntidad::getPrecioBase)).toList();
                    break;

                case PRECIO_MAYOR_A_MENOR:
                    juegos = juegos.stream().sorted(Comparator.comparing(JuegoEntidad::getPrecioBase).reversed()).toList();
                    break;

                case FECHA_MAS_RECIENTE:
                    juegos = juegos.stream().sorted(Comparator.comparing(JuegoEntidad::getFechaLanzamiento).reversed()).toList();
                    break;

                case FECHA_MAS_ANTIGUA:
                    juegos = juegos.stream().sorted(Comparator.comparing(JuegoEntidad::getFechaLanzamiento)).collect(Collectors.toList());
                    break;
            }
        }
        return juegos.stream()
                .map(Mapper::mapDeJuego)
                .toList();
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
        Optional<JuegoEntidad> juego = juegosrRepo.obtenerPorId(id);

        if (juego.isEmpty()) {
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
     * @param id         ID del juego al que aplicar el descuento.
     * @param porcentaje Porcentaje de descuento a aplicar (0-100).
     * @return `JuegoDTO` con el descuento aplicado y el precio final calculado.
     * @throws ExcepcionValidacion si el juego no existe o el porcentaje está fuera del rango permitido.
     */
    public JuegoDTO aplicarDescuento(Long id, int porcentaje) throws ExcepcionValidacion {
        List<ErrorDTO> errores = new ArrayList<>();

        Optional<JuegoEntidad> juegoOpt = juegosrRepo.obtenerPorId(id);

        if (juegoOpt.isEmpty()) errores.add(new ErrorDTO("juego", ErrorType.NO_ENCONTRADO));

        if (porcentaje < MIN_PORCENTAJE || porcentaje > MAX_PORCENTAJE) errores.add(new ErrorDTO("porcentaje descuento", ErrorType.VALOR_NO_VALIDO));

        if (porcentaje == MIN_PORCENTAJE) errores.add(new ErrorDTO("porcentaje descuento", ErrorType.VALOR_NO_VALIDO));

        if(!errores.isEmpty()) {
            throw new ExcepcionValidacion(errores);
        }

        JuegoEntidad juego = juegoOpt.orElse(null);

        JuegoForm formConDescuento = new JuegoForm(juego.getTitulo(), juego.getDescripcion(), juego.getDesarrollador(),
                juego.getFechaLanzamiento(), juego.getPrecioBase(), porcentaje, juego.getCategoria(), juego.getIdiomas(),
                juego.getClasificacionEdad(), juego.getEstado());

        Optional<JuegoEntidad> juegoConDescuentoOpt = juegosrRepo.actualizar(id, formConDescuento);

        if (juegoConDescuentoOpt.isEmpty()) {
            errores.add(new ErrorDTO("juego", ErrorType.NO_ENCONTRADO));
            throw new ExcepcionValidacion(errores);
        }
        JuegoEntidad juegoConDescuento = juegoConDescuentoOpt.orElse(null);

        return Mapper.mapDeJuego(juegoConDescuento);
    }

    /**
     * Cambiar estado del juego
     * Descripción: Modificar el estado de disponibilidad de un juego.
     * Validaciones: Juego existe, estado válido.
     *
     * @param id          ID del juego cuyo estado se desea cambiar.
     * @param nuevoEstado Nuevo estado de disponibilidad (ej. "DISPONIBLE", "NO_DISPONIBLE", "PROXIMAMENTE").
     * @return `JuegoDTO` con el estado actualizado.
     * @throws ExcepcionValidacion si el juego no existe o el estado proporcionado no es válido.
     */
    public JuegoDTO cambiarEstadoJuego(Long id, String nuevoEstado) throws ExcepcionValidacion {
        List<ErrorDTO> errores = new ArrayList<>();
        System.out.println("Cambiando estado a: " + nuevoEstado);
        Optional<JuegoEntidad> juegoOpt = juegosrRepo.obtenerPorId(id);

        if (juegoOpt.isEmpty()) {
            errores.add(new ErrorDTO("juego", ErrorType.NO_ENCONTRADO));
            throw new ExcepcionValidacion(errores);
        }
        JuegoEntidad juego = juegoOpt.orElse(null);

        JuegoEstado estado = JuegoEstado.valueOf(nuevoEstado);
        System.out.println("Nuevo estado enum: " + estado);
        JuegoForm formConDescuento = new JuegoForm(juego.getTitulo(), juego.getDescripcion(), juego.getDesarrollador(), juego.getFechaLanzamiento(),
                juego.getPrecioBase(), juego.getDescuentoActual(), juego.getCategoria(), juego.getIdiomas(), juego.getClasificacionEdad(), estado);

        Optional<JuegoEntidad> juegoConDescuentoOpt = juegosrRepo.actualizar(id, formConDescuento);

        if (juegoConDescuentoOpt.isEmpty()) {
            errores.add(new ErrorDTO("juego", ErrorType.NO_ENCONTRADO));
            throw new ExcepcionValidacion(errores);
        }
        JuegoEntidad juegoConDescuento = juegoConDescuentoOpt.orElse(null);
        System.out.println("Estado después de actualizar: " + juegoConDescuento.getEstado());
        return Mapper.mapDeJuego(juegoConDescuento);
    }
}
