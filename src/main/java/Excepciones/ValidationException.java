package Excepciones;

import Modelo.DTOs.ErrorDTO;

import java.util.List;

public class ValidationException extends Exception {
    

    List<ErrorDTO> errores;

    public ValidationException(List<ErrorDTO> errores) {
        super("Errores de validación");
        this.errores = errores;
    }
    public List<ErrorDTO> getErrores() {
        return errores;
    }
    
}
