package Controlador;

import Repositorio.Interfaz.IJuegosRepo;
import Vista.SteamVista;

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
    private SteamVista vista;

    public JuegosControlador(IJuegosRepo repo, SteamVista vista) {
        this.repo = repo;
        this.vista = vista;
    }
}
