package Controlador;

import Excepciones.ValidacionExcepcion;
import Modelo.DTOs.ErrorDto;
import Modelo.Form.JuegoForm;
import Repositorio.Interfaz.IJuegosRepo;

import java.util.List;

public class JuegosControlador {

    private IJuegosRepo repo;

    public JuegosControlador(IJuegosRepo repo) {
        this.repo = repo;
    }

    public void aniadirJuego(JuegoForm juegoForm) throws ValidacionExcepcion {
        // precondición

        List<ErrorDto> errores = validarJuego(juegoForm);

        if (errores.size() != 0)
            throw new ValidacionExcepcion(errores);

        // cuerpo
        repo.create(juegoForm);

    }

    private List<ErrorDto> validarJuego(JuegoForm juegoForm) {
    }
}
