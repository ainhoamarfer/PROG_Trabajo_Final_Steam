package Controlador;

import Repositorio.Interfaz.IResenaRepo;

public class ResenasControlador {

    private IResenaRepo repo;

    public ResenasControlador(IResenaRepo repo) {
        this.repo = repo;
    }
}
