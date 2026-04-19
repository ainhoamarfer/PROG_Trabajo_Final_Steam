package org.ainhoamarfer.controlador;

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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ResenasControlador {

    /*
    Escribir reseña
    Eliminar reseña
    Ver reseñas de un juego
    Ocultar reseña
    Consultar estadísticas de reseñas (Ficheros)
    Ver reseñas de un usuario
     */

    private IResenaRepo repoResena;
    private IBibliotecaRepo repoBiblioteca;


    public ResenasControlador(IResenaRepo repoResena, IBibliotecaRepo repoBiblioteca) {
        this.repoResena = repoResena;
        this.repoBiblioteca = repoBiblioteca;
    }

    /**
     * Escribir reseña
     * Descripción: Crear una nueva reseña para un juego que el usuario posee
     *
     * @param idUsuario   ID del usuario
     * @param idJuego     ID del juego
     * @param recomendado recomendado (boolean)
     * @param texto       texto de reseña
     * @return Reseña creada exitosamente o lista de errores
     * Validaciones: Usuario propietario del juego, no duplicada, texto válido
     */
    public ResenaDTO escribirResena(long idUsuario, long idJuego, boolean recomendado, String texto) throws ExcepcionValidacion {
        List<ErrorDTO> errores = new ArrayList<>();

        Optional<BibliotecaEntidad> BiblioOpt = repoBiblioteca.obtenerPorIdUsuarioYIdJuego(idUsuario, idJuego);

        if(BiblioOpt.isEmpty()){
            errores.add(new ErrorDTO("Biblioteca", ErrorType.NO_ENCONTRADO));
            throw new ExcepcionValidacion(errores);
        }else {
            BibliotecaEntidad Biblioteca = BiblioOpt.orElse(null);
            Optional<ResenaEntidad> resenaOpt = repoResena.crear(new ResenaForm(idUsuario, idJuego, recomendado, texto, Biblioteca.getTiempoJuego(),LocalDate.now(), LocalDate.now(), ResenaEstado.PUBLICADA));
            ResenaEntidad resena = resenaOpt.orElse(null);
            return Mapper.mapDeResena(resena);
        }
    }

    /**
     * Eliminar reseña
     * Descripción: Cambiar el estado de una reseña a eliminada
     *
     * @param idResena  ID de reseña
     * @param idUsuario ID del usuario (para verificar pertenencia)
     * @return Confirmación de eliminación
     * Validaciones: Reseña existe, pertenece al usuario
     */
    public String eliminarResena(long idResena, long idUsuario) throws ExcepcionValidacion {

        //reviaser con funcion de actualizar estado
        List<ErrorDTO> errores = new ArrayList<>();

        Optional<ResenaEntidad> resenaOpt = repoResena.obtenerPorId(idResena);

        resenaOpt.filter(r -> idUsuario == r.getUsuarioId())
                .ifPresentOrElse(r -> repoResena.eliminar(idResena), () -> {

            if (resenaOpt.isEmpty()) {
                errores.add(new ErrorDTO("reseña", ErrorType.NO_ENCONTRADO));

            } else {
                errores.add(new ErrorDTO("reseña", ErrorType.NO_PERTENECE_AL_USUARIO));
            }
        });

        if (!errores.isEmpty()) {
            throw new ExcepcionValidacion(errores);
        } else {
            return "Reseña eliminada exitosamente";
        }
    }

    /**
     * Ver reseñas de un juego
     * Descripción: Listar todas las reseñas publicadas de un juego específico
     *
     * @param idJuego ID del juego
     * @param filtroPosNeg  filtro opcional (positivas/negativas)
     * @param orden   orden (recientes/mas antiguas)
     * @return Lista de reseñas con estadísticas generales
     * Datos mostrados: Autor, recomendado, texto, horas jugadas, fecha
     */
    public List<ResenaDTO> verResenasJuego(long idJuego, String filtroPosNeg, String orden) {
        List<ErrorDTO> errores = new ArrayList<>();

        // Comprobar si publicada
        List<ResenaEntidad> resenas = repoResena.obtenerTodos().stream()
                .filter(r -> r.getJuegoId() == idJuego && r.getEstado() == ResenaEstado.PUBLICADA)
                .collect(Collectors.toCollection(ArrayList::new)); // al parecer si pongo to list me fallan los tests

        // Filtrar positivas negativas
        if (filtroPosNeg != null && !filtroPosNeg.isEmpty()) {
            if ("positivas".equalsIgnoreCase(filtroPosNeg)) {
                resenas = resenas.stream()
                        .filter(ResenaEntidad::isRecomendado)
                        .collect(Collectors.toCollection(ArrayList::new)); // para el test
            } else if ("negativas".equalsIgnoreCase(filtroPosNeg)) {
                resenas = resenas.stream()
                        .filter(r -> !r.isRecomendado())
                        .collect(Collectors.toCollection(ArrayList::new)); // para el test
            }
        }

        // Ordenar según criterio
        if ("recientes".equalsIgnoreCase(orden)) {
            resenas.sort(Comparator.comparing(ResenaEntidad::getFechaPublicacion).reversed());
        }

        if ("mas antiguas".equalsIgnoreCase(orden)) {
            resenas.sort(Comparator.comparing(ResenaEntidad::getFechaPublicacion));
        }

        return resenas.stream()
                .map(Mapper::mapDeResena)
                .toList();
    }

    /**
     * Ocultar reseña
     * Descripción: Cambiar la visibilidad de una reseña a oculta
     *
     * @param idResena  ID de reseña
     * @param idUsuario ID del usuario
     * @return Confirmación de ocultación
     * Validaciones: Reseña existe, pertenece al usuario, está publicada
     */
    public String ocultarResena(long idResena, long idUsuario) throws ExcepcionValidacion {
        List<ErrorDTO> errores = new ArrayList<>();

        Optional<ResenaEntidad> resenaOpt = repoResena.obtenerPorId(idResena)
                .filter(u -> idUsuario == u.getUsuarioId() && u.getEstado() != ResenaEstado.OCULTA);

        if(resenaOpt.isEmpty()){
            errores.add(new ErrorDTO("Reseña", ErrorType.NO_ENCONTRADO));
        }else {
            repoResena.actualizarEstadoResena(idResena, ResenaEstado.OCULTA);
        }

        if (!errores.isEmpty()) {
            throw new ExcepcionValidacion(errores);
        } else {
            return "Reseña cambiada a no visible";
        }
    }

    /**
     * Consultar estadísticas de reseñas (Ficheros)
     * Descripción: Calcular y mostrar métricas de las reseñas de un juego
     *
     * @param idJuego ID del juego
     * @return Objeto con estadísticas completas
     * Estadísticas: Total reseñas, % positivas, % negativas, promedio horas, tendencia reciente
     */
    public Object consultarEstadisticasResenas(long idJuego) {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Ver reseñas de un usuario
     * Descripción: Listar todas las reseñas escritas por un usuario específico
     *
     * @param idUsuario    ID del usuario
     * @param filtroEstado filtro de estado opcional
     * @return Lista de reseñas del usuario con estadísticas
     * Datos mostrados: Juego, recomendado, texto (extracto), fecha, horas jugadas al momento
     */
    public List<ResenaDTO> verResenasUsuario(long idUsuario, String filtroEstado) throws ExcepcionValidacion {
        List<ErrorDTO> errores = new ArrayList<>();

        List<ResenaEntidad> resenas = repoResena.obtenerPorIdUsuario(idUsuario)
                .stream()
                .toList();

        if (resenas.isEmpty()) {
            errores.add(new ErrorDTO("Reseñas", ErrorType.NO_ENCONTRADO));
        }

        if (!errores.isEmpty()) {
            throw new ExcepcionValidacion(errores);
        }

        List<ResenaEntidad> resenasFiltradas = new ArrayList<>();
        for (ResenaEntidad resena : resenas) {
            if (resena.getEstado().equals(filtroEstado)) {
                resenasFiltradas.add(resena);
            }
        }

        List<ResenaDTO> resenasEncontradasMap = new ArrayList<>();
        for (ResenaEntidad resena : resenasFiltradas) {
            ResenaDTO resenaDto = Mapper.mapDeResena(resena);
            resenasEncontradasMap.add(resenaDto);
        }
        return resenasEncontradasMap;
    }
}

