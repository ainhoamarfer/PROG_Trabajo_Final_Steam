package org.ainhoamarfer.Controlador;

import org.ainhoamarfer.Repositorio.Interfaz.IResenaRepo;
import org.ainhoamarfer.Vista.SteamVista;

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
}
