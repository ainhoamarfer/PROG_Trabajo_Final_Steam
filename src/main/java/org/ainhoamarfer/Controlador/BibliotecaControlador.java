package org.ainhoamarfer.Controlador;

import org.ainhoamarfer.Modelo.DTOs.BibliotecaDTO;
import org.ainhoamarfer.Repositorio.Interfaz.IBibliotecaRepo;
import org.ainhoamarfer.Vista.SteamVista;

import java.util.List;

public class BibliotecaControlador {

    /*
    Ver biblioteca personal
    Añadir juego a biblioteca
    Eliminar juego de biblioteca
    Actualizar tiempo de juego
    Consultar última sesión
    Filtrar biblioteca (Ficheros)
    Ver estadísticas de biblioteca
     */

    private IBibliotecaRepo repo;
    private SteamVista vista;

    public BibliotecaControlador(IBibliotecaRepo repo, SteamVista vista) {
        this.repo = repo;
        this.vista = vista;
    }

    /**
     * Ver biblioteca personal
     * Descripción: Listar todos los juegos que posee un usuario en su biblioteca
     * @param idUsuario ID del usuario
     * @param orden orden opcional (alfabético, tiempo de juego, última sesión, fecha de adquisición)
     * @return Lista de juegos en la biblioteca con sus datos de uso
     * Datos mostrados: Título del juego, tiempo jugado, última sesión, estado de instalación
     */
    public List<BibliotecaDTO> verBibliotecaPersonal(long idUsuario, String orden) {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Añadir juego a biblioteca
     * Descripción: Agregar un juego adquirido a la biblioteca del usuario
     * @param idUsuario ID del usuario
     * @param idJuego ID del juego
     * @return Confirmación de adición a biblioteca o mensaje de error
     * Validaciones: Usuario existe, juego existe, no duplicado, compra verificada
     */
    public String anadirJuegoBiblioteca(long idUsuario, long idJuego) {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Eliminar juego de biblioteca
     * Descripción: Quitar un juego de la biblioteca del usuario
     * @param idUsuario ID del usuario
     * @param idJuego ID del juego
     * @return Confirmación de eliminación o cancelación
     * Validaciones: Entrada existe en la biblioteca
     */
    public String eliminarJuegoBiblioteca(long idUsuario, long idJuego) {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Actualizar tiempo de juego
     * Descripción: Registrar y actualizar las horas jugadas de un juego
     * @param idUsuario ID del usuario
     * @param idJuego ID del juego
     * @param horasAAnadir horas a añadir
     * @return Nuevo tiempo total de juego
     * Validaciones: Biblioteca existe, horas positivas
     */
    public double actualizarTiempoJuego(long idUsuario, long idJuego, double horasAAnadir) {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Consultar última sesión
     * Descripción: Ver la última vez que se jugó a un juego específico
     * @param idUsuario ID del usuario
     * @param idJuego ID del juego
     * @return Fecha y hora de última sesión o mensaje "Nunca jugado"
     * Formato: "Última sesión: hace 2 días (18/01/2026 15:30)"
     */
    public String consultarUltimaSesion(long idUsuario, long idJuego) {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Filtrar biblioteca (Ficheros)
     * Descripción: Buscar juegos en la biblioteca personal según criterios
     * @param idUsuario ID del usuario
     * @param estadoInstalacion estado de instalación
     * @param textoBusqueda texto de búsqueda
     * @return Lista filtrada de juegos de la biblioteca
     * Datos mostrados: Título, tiempo jugado, estado
     */
    public List<BibliotecaDTO> filtrarBiblioteca(long idUsuario, boolean estadoInstalacion, String textoBusqueda) {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Ver estadísticas de biblioteca
     * Descripción: Mostrar métricas generales de la biblioteca del usuario
     * @param idUsuario ID del usuario
     * @return Objeto con todas las estadísticas calculadas
     * Estadísticas: Total juegos, horas totales, juegos instalados, juego más jugado, valor total, juegos nunca jugados
     */
    public Object verEstadisticasBiblioteca(long idUsuario) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
