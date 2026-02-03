package Excepciones;


import Modelo.DTOs.ErrorDto;

import java.util.List;

public class ValidacionExcepcion extends Exception {
    

    List<ErrorDto> errores;

    public ValidacionExcepcion(List<ErrorDto> errores) {
        super("Errores de validación");
        this.errores = errores;
    }
    public List<ErrorDto> getErrores() {
        return errores;
    }
    
}
