package org.ainhoamarfer.Controlador;

import org.ainhoamarfer.Repositorio.Interfaz.IBibliotecaRepo;
import org.ainhoamarfer.Vista.SteamVista;

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

    /*
    Ver biblioteca personal
     */
}
