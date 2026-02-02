package Controlador;

import Repositorio.Interfaz.ICompraRepo;

public class CompraControlador {

    private ICompraRepo repo;

    public CompraControlador(ICompraRepo repo) {
        this.repo = repo;
    }
}
