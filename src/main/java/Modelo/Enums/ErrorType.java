package Modelo.Enums;

public enum ErrorType {
    REQUERIDO("El campo es obligatorio"),
    FORMATO_INVALIDO("El formato es inválido"),
    VALOR_DEMASIADO_ALTO("El valor es demasiado alto"),
    VALOR_DEMASIADO_BAJO("El valor es demasiado bajo"),
    NO_ENCONTRADO("No se encontró el elemento"),
    DUPLICADO("El elemento está duplicado"),
    LONGITUD_DEMASIADO_LARGA("El elemento es demasiado largo"),
    LONGITUD_DEMASIADO_CORTA("El elemento es demasiado corto");

    private final String mensaje;

    private ErrorType(String mensaje) {
        this.mensaje = mensaje;
    }
}
