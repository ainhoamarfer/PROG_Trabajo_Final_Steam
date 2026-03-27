package org.ainhoamarfer.controlador;

import org.ainhoamarfer.mapper.Mapper;
import org.ainhoamarfer.modelo.enums.CriterioOrdenacionJuegosEnum;
import org.ainhoamarfer.modelo.enums.JuegoEstado;
import org.ainhoamarfer.modelo.form.CriteriosBusquedaForm;
import org.ainhoamarfer.modelo.dtos.ErrorDTO;
import org.ainhoamarfer.modelo.entidad.JuegoEntidad;
import org.ainhoamarfer.modelo.enums.ErrorType;
import org.ainhoamarfer.modelo.form.JuegoForm;
import org.ainhoamarfer.repositorio.implementacionmemoria.JuegoRepo;
import org.ainhoamarfer.repositorio.interfaz.IJuegosRepo;
import org.ainhoamarfer.transaction.ITransactionManager;
import org.ainhoamarfer.vista.SteamVista;

import java.util.*;

import org.ainhoamarfer.modelo.dtos.JuegoDTO;
import org.ainhoamarfer.excepciones.ExcepcionValidacion;

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
    private ITransactionManager tm;

    public JuegosControlador(IJuegosRepo repo, ITransactionManager tm) {
        this.repo = repo;
        this.tm = tm;

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
        Optional<JuegoEntidad> juegoCreado = tm.inTransaction(() -> {
            Optional<JuegoEntidad> juegoOpt = repo.obtenerPorTitulo(form.getTitulo());
            if (juegoOpt.isPresent()) {
                errores.add(new ErrorDTO("juego", ErrorType.DUPLICADO));
                //necesario lanzar esta excepcion para que se haga el rollback, si ocurre no ejecuta la lambda pero sigue el resto del métod
                throw new IllegalStateException();
            }
            return repo.crear(form);
        });

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
    //public List<JuegoDTO> consultarCatalogo(CriterioOrdenacionJuegosEnum criterioOrdenacion) {
        //int op;
        //op = vista.menu();
        //CriterioOrdenacionJuegosEnum opcion = CriterioOrdenacionJuegosEnum.values()[op];
//
        //List<JuegoEntidad> juegos = repo.obtenerTodos();
//
        ////De la A a la Z
        //if(opcion == CriterioOrdenacionJuegosEnum.ALFABETICO_A_Z){
        //    juegos.stream().sorted(Comparator.comparing(JuegoEntidad::getTitulo))
        //            .toList();
        //}
//
        ////De la Z a la A
        //if(opcion == CriterioOrdenacionJuegosEnum.ALFABETICO_Z_A){
        //    juegos.stream().sorted(Comparator.comparing(JuegoEntidad::getTitulo)
        //            .reversed())
        //            .toList();
        //}
//
        ////De menor a mayor precio - TODO ver si pillo el precio base o el precio con descuento
        //if(opcion == CriterioOrdenacionJuegosEnum.PRECIO_MENOR_A_MAYOR){
        //    juegos.stream().sorted(Comparator.comparing(JuegoEntidad::getPrecioBase))
        //            .toList();
        //}
//
        ////De mayor a menor precio
        //if(opcion == CriterioOrdenacionJuegosEnum.PRECIO_MAYOR_A_MENOR){
        //    juegos.stream().sorted(Comparator.comparing(JuegoEntidad::getPrecioBase)
        //            .reversed())
        //            .toList();
        //}
//
        //if(opcion == CriterioOrdenacionJuegosEnum.FECHA_MAS_RECIENTE){
        //    juegos.stream().sorted(Comparator.comparing(JuegoEntidad::getFechaLanzamiento))
        //            .toList();
        //}
//
        //if(opcion == CriterioOrdenacionJuegosEnum.FECHA_MAS_ANTIGUA){
        //    juegos.stream().sorted(Comparator.comparing(JuegoEntidad::getFechaLanzamiento).reversed())
        //            .toList();
        //}
//
        //List<JuegoDTO> juegosEncontrados = new ArrayList<>();
        //for (JuegoEntidad juego : juegos) {
        //    juegosEncontrados.add(Mapper.mapDeJuego(juego));
        //}
        //return juegosEncontrados;
   // }

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
     * @param id ID del juego al que aplicar el descuento.
     * @param porcentaje Porcentaje de descuento a aplicar (0-100).
     * @return `JuegoDTO` con el descuento aplicado y el precio final calculado.
     * @throws ExcepcionValidacion si el juego no existe o el porcentaje está fuera del rango permitido.
     */
    public JuegoDTO aplicarDescuento(Long id, double porcentaje) throws ExcepcionValidacion {
        List<ErrorDTO> errores = new ArrayList<>();

        Optional<JuegoEntidad> juegoOpt = repo.obtenerPorId(id);

        if(juegoOpt.isEmpty()){
            errores.add(new ErrorDTO("juego", ErrorType.NO_ENCONTRADO));
            throw new ExcepcionValidacion(errores);
        }
        JuegoEntidad juego = juegoOpt.orElse(null);

        double precioConDescuento = (juego.getPrecioBase() * porcentaje) / 100;

        JuegoForm formConDescuento = new JuegoForm(juego.getTitulo(), juego.getDescripcion(), juego.getDesarrollador(), juego.getFechaLanzamiento(), juego.getPrecioBase(),
               precioConDescuento, juego.getCategoria(), juego.getIdiomas(), juego.getClasificacionEdad(), juego.getEstado());

        Optional<JuegoEntidad> juegoConDescuentoOpt = repo.actualizar(id, formConDescuento);

        //Seguramente esto no debería repetirse aquí y arriba de esta manera pero ya no me da el cerebro
        if(juegoConDescuentoOpt.isEmpty()){
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
     * @param id ID del juego cuyo estado se desea cambiar.
     * @param nuevoEstado Nuevo estado de disponibilidad (ej. "DISPONIBLE", "NO_DISPONIBLE", "PROXIMAMENTE").
     * @return `JuegoDTO` con el estado actualizado.
     * @throws ExcepcionValidacion si el juego no existe o el estado proporcionado no es válido.
     */
    public JuegoDTO cambiarEstadoJuego(Long id, String nuevoEstado) throws ExcepcionValidacion {
        List<ErrorDTO> errores = new ArrayList<>();

        Optional<JuegoEntidad> juegoOpt = repo.obtenerPorId(id);

        if(juegoOpt.isEmpty()){
            errores.add(new ErrorDTO("juego", ErrorType.NO_ENCONTRADO));
            throw new ExcepcionValidacion(errores);
        }
        JuegoEntidad juego = juegoOpt.orElse(null);

        JuegoEstado estado = JuegoEstado.valueOf(nuevoEstado);

        JuegoForm formConDescuento = new JuegoForm(juego.getTitulo(), juego.getDescripcion(), juego.getDesarrollador(), juego.getFechaLanzamiento(), juego.getPrecioBase(),
                juego.getDescuentoActual(), juego.getCategoria(), juego.getIdiomas(), juego.getClasificacionEdad(), estado);

        Optional<JuegoEntidad> juegoConDescuentoOpt = repo.actualizar(id, formConDescuento);

        if(juegoConDescuentoOpt.isEmpty()){
            errores.add(new ErrorDTO("juego", ErrorType.NO_ENCONTRADO));
            throw new ExcepcionValidacion(errores);
        }
        JuegoEntidad juegoConDescuento = juegoConDescuentoOpt.orElse(null);

        return Mapper.mapDeJuego(juegoConDescuento);
    }
}
