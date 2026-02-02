package Modelo.Form;

import java.time.LocalDate;

public class BibliotecaForm {

    private int usuarioId;
    private int juegoId;
    private LocalDate fechaAdquisicion;

    public BibliotecaForm(int usuarioId, int juegoId, LocalDate fechaAdquisicion) {
        this.usuarioId = usuarioId;
        this.juegoId = juegoId;
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getJuegoId() {
        return juegoId;
    }

    public void setJuegoId(int juegoId) {
        this.juegoId = juegoId;
    }

    public LocalDate getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(LocalDate fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }
}
