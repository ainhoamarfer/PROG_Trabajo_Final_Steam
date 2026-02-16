package org.ainhoamarfer.Modelo.Form;

public enum ErrorType {
    REQUERIDO("El campo es obligatorio"),
    FORMATO_INVALIDO("El formato es inválido"),
    VALOR_NO_VALIDO("El valor no es valido"),
    NO_ENCONTRADO("No se encontró el elemento"),
    DUPLICADO("El elemento está duplicado"),
    ESTADO_CUENTA("Esta cuentas esta suspendida o baneada"),
    USUARIO_INVALIDO("Este usuario no es valido");

    private final String mensaje;

    private ErrorType(String mensaje) {
        this.mensaje = mensaje;
    }
}
