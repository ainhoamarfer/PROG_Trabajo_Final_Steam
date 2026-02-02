package Excepciones;

import java.util.ArrayList;
import java.util.List;

public class CreacionIncorrectaExcepcion extends Exception {

    private List<String> errores = new ArrayList<>();

    public CreacionIncorrectaExcepcion(List<String> errores) {
        this.errores = errores;
    }

    public List<String> getErrores(){
        return errores;
    }
}
