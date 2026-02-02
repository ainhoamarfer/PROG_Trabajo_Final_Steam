package Controlador;

import Excepciones.CreacionIncorrectaExcepcion;
import Modelo.Form.JuegoForm;
import Repositorio.ImplementacionMemoria.JuegoRepo;
import Repositorio.Interfaz.IJuegosRepo;

import java.util.List;

public class JuegosControlador {

    private IJuegosRepo repo;

    public JuegosControlador(IJuegosRepo repo) {
        this.repo = repo;
    }

    public void aniadirJuego(JuegoForm juegoForm) throws CreacionIncorrectaExcepcion {
        // precondición

        List<String> errores = validarJuego(juegoForm);

        if (errores.size() != 0)
            throw new CreacionIncorrectaExcepcion(errores);

        // cuerpo
        repo.create(juegoForm);

    }

    private List<String> validarJuego(JuegoForm juegoForm) {
    }
}
