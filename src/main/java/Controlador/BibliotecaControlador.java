package Controlador;

import Repositorio.Interfaz.IBibliotecaRepo;

public class BibliotecaControlador {

    IBibliotecaRepo repo;

    public BibliotecaControlador(IBibliotecaRepo repo) {
        this.repo = repo;
    }
}
