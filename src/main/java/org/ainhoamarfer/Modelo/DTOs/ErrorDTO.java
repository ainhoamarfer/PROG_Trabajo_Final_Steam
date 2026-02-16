package org.ainhoamarfer.Modelo.DTOs;


import org.ainhoamarfer.Modelo.Enums.ErrorType;

public record ErrorDTO (String campo, ErrorType mensaje){
}
