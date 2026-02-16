package org.ainhoamarfer.Modelo.Enums;

public enum UsuarioEstadoCuenta {
    ACTIVA, SUSPENDIDA, BANEADA;

    public boolean estadoValidoParaOperar() {
        return this == ACTIVA;
    }

    public boolean estadoBloqueado() {
        return this == SUSPENDIDA || this == BANEADA;
    }
}





