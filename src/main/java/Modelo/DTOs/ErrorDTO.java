package Modelo.DTOs;


import Modelo.Form.ErrorType;

public record ErrorDTO (String campo, ErrorType mensaje){
}
