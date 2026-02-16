package org.ainhoamarfer.Excepciones;

import org.ainhoamarfer.Modelo.DTOs.ErrorDTO;

import java.util.List;

public class ExcepcionValidacion extends Exception {
    

    List<ErrorDTO> errores;

    public ExcepcionValidacion(List<ErrorDTO> errores) {
        super("Errores de validación");
        this.errores = errores;
    }
    public List<ErrorDTO> getErrores() {
        return errores;
    }
    
}
