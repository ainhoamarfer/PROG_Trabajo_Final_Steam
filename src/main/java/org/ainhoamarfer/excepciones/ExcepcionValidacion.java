package org.ainhoamarfer.excepciones;

import org.ainhoamarfer.modelo.dtos.ErrorDTO;

import java.util.List;

public class ExcepcionValidacion extends RuntimeException {

    List<ErrorDTO> errores;

    public ExcepcionValidacion(List<ErrorDTO> errores) {
        super("Errores de validación");
        this.errores = errores;
    }
    public List<ErrorDTO> getErrores() {
        return errores;
    }
    
}
