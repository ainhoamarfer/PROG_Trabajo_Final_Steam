package Controlador;

import Repositorio.Interfaz.IJuegosRepo;

public class JuegosControlador {

    private IJuegosRepo repo;

    public JuegosControlador(IJuegosRepo repo) {
        this.repo = repo;
    }
}
