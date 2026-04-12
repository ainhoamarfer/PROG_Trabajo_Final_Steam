package org.ainhoamarfer.modelo.enums;

public enum ErrorType {
    REQUERIDO("El campo es obligatorio"),
    FORMATO_INVALIDO("El formato es inválido"),
    VALOR_NO_VALIDO("El valor no es valido"),
    NO_ENCONTRADO("No se encontró el elemento"),
    DUPLICADO("El elemento está duplicado"),
    ESTADO_CUENTA("Esta cuentas esta suspendida o baneada"),
    USUARIO_INVALIDO("Este usuario no es valido"),
    LONGITUD_INVALIDA("La longitud es incorrecta"),
    COMPRA_YA_EXISTENTE("El usuario ya ha comprado este juego"),
    SALDO_INSUFICIENTE("El saldo en la cartera es insuficiente para realizar la compra"),
    NO_PERTENECE_AL_USUARIO("El elemento no pertenece al usuario"),
    PLAZO_REEMBOLSO_VENCIDO("El plazo de reenvolso expiro");


    private final String mensaje;

    private ErrorType(String mensaje) {
        this.mensaje = mensaje;
    }
}
