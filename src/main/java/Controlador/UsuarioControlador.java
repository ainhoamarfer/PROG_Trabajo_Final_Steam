package Controlador;

import Modelo.Form.UsuarioForm;
import Repositorio.Interfaz.IUsuarioRepo;

public class UsuarioControlador {

    private IUsuarioRepo repo;

    public UsuarioControlador(IUsuarioRepo repo) {
        this.repo = repo;
    }

    private void validarUsuarioForm (UsuarioForm form){

        if (form.getNombreUsuario().isEmpty()){

        }



    }
}
