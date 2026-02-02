package Controlador;

import Excepciones.CreacionIncorrectaExcepcion;
import Modelo.Form.UsuarioForm;
import Repositorio.ImplementacionMemoria.UsuarioRepo;
import Repositorio.Interfaz.IUsuarioRepo;

import java.util.ArrayList;
import java.util.List;

public class UsuarioControlador {

    private IUsuarioRepo repo;

    public UsuarioControlador(IUsuarioRepo repo) {
        this.repo = repo;
    }

    public void aniadirCoche(UsuarioRepo usuario) throws CreacionIncorrectaExcepcion {
        // precondición

        List<String> errores = validarUsuarioForm(usuario);

        if (errores.size() != 0)
            throw new CreacionIncorrectaExcepcion(errores);

        // cuerpo

        usua.getCoches().add(coche);

    }

    private void validarUsuarioForm (UsuarioForm form){

        List<String> errores = new ArrayList<>();

        if(!Util.validarStringNoVacia(form.getNombreUsuario())){
            errores.add("Nombre obligatorio");
        }

    }
}
