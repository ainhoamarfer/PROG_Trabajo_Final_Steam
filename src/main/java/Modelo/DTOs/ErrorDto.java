package Modelo.DTOs;

import Modelo.Enums.ErrorType;

public record ErrorDto(String campo, ErrorType mensaje) {

}
