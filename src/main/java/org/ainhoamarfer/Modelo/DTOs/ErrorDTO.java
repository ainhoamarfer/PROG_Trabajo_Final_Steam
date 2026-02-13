package org.ainhoamarfer.Modelo.DTOs;


import org.ainhoamarfer.Modelo.Form.ErrorType;

public record ErrorDTO (String campo, ErrorType mensaje){
}
