package org.ainhoamarfer.modelo.entidad;

public class EstadisticaBibliotecaEntidad {

    private long idUsuario;
    private int totalJuegos;
    private double horasTotales;
    private int juegosInstalados;
    private long idJuegoMasJugado;
    private double valorTotal;
    private int juegosNuncaJugados;

    public EstadisticaBibliotecaEntidad(long idUsuario, int totalJuegos, double horasTotales, int juegosInstalados, long idJuegoMasJugado, double valorTotal, int juegosNuncaJugados) {
        this.idUsuario = idUsuario;
        this.totalJuegos = totalJuegos;
        this.horasTotales = horasTotales;
        this.juegosInstalados = juegosInstalados;
        this.idJuegoMasJugado = idJuegoMasJugado;
        this.valorTotal = valorTotal;
        this.juegosNuncaJugados = juegosNuncaJugados;
    }

    public int getTotalJuegos() {
        return totalJuegos;
    }

    public double getHorasTotales() {
        return horasTotales;
    }

    public int getJuegosInstalados() {
        return juegosInstalados;
    }

    public long getIdJuegoMasJugado() {
        return idJuegoMasJugado;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public int getJuegosNuncaJugados() {
        return juegosNuncaJugados;
    }
}
