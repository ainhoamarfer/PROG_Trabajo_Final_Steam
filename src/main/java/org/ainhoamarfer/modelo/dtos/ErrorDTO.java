package org.ainhoamarfer.modelo.dtos;

import org.ainhoamarfer.modelo.enums.ErrorType;

public class ErrorDTO {

	private final String campo;
	private final ErrorType mensaje;

	public ErrorDTO(String campo, ErrorType mensaje) {
		this.campo = campo;
		this.mensaje = mensaje;
	}

	public String campo() {
		return campo;
	}

	public ErrorType mensaje() {
		return mensaje;
	}

}
