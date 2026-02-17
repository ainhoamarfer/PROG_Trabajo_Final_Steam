package org.ainhoamarfer.Controlador;

import org.ainhoamarfer.Modelo.DTOs.ResenaDTO;
import org.ainhoamarfer.Repositorio.Interfaz.IResenaRepo;
import org.ainhoamarfer.Vista.SteamVista;

import java.util.List;

public class ResenasControlador {

    /*
    Escribir reseña
    Eliminar reseña
    Ver reseñas de un juego
    Ocultar reseña
    Consultar estadísticas de reseñas (Ficheros)
    Ver reseñas de un usuario
     */

    private IResenaRepo repo;
    private SteamVista vista;

    public ResenasControlador(IResenaRepo repo, SteamVista vista) {
        this.repo = repo;
        this.vista = vista;
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
    public String escribirResena(long idUsuario, long idJuego, boolean recomendado, String texto) {
        throw new UnsupportedOperationException("Not implemented");
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
    public String eliminarResena(long idResena, long idUsuario) {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Ver reseñas de un juego
     * Descripción: Listar todas las reseñas publicadas de un juego específico
     *
     * @param idJuego ID del juego
     * @param filtro  filtro opcional (positivas/negativas)
     * @param orden   orden (recientes/útiles)
     * @return Lista de reseñas con estadísticas generales
     * Datos mostrados: Autor, recomendado, texto, horas jugadas, fecha
     */
    public List<ResenaDTO> verResenasJuego(long idJuego, String filtro, String orden) {
        throw new UnsupportedOperationException("Not implemented");
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
    public String ocultarResena(long idResena, long idUsuario) {
        throw new UnsupportedOperationException("Not implemented");
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
    public List<ResenaDTO> verResenasUsuario(long idUsuario, String filtroEstado) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
