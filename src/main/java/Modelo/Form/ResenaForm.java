package Modelo.Form;

import Modelo.Enums.ResenaEstado;

public class ResenaForm {

    private int usuarioId;
    private int juegoId;
    private boolean recomendado;
    private String texto;
    private ResenaEstado estado;

    public ResenaForm(int usuarioId, int juegoId, boolean recomendado, String texto, ResenaEstado estado) {
        this.usuarioId = usuarioId;
        this.juegoId = juegoId;
        this.recomendado = recomendado;
        this.texto = texto;
        this.estado = estado;
    }



    public ResenaEstado getEstado() {
        return estado;
    }

    public void setEstado(ResenaEstado estado) {
        this.estado = estado;
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

    public boolean isRecomendado() {
        return recomendado;
    }

    public void setRecomendado(boolean recomendado) {
        this.recomendado = recomendado;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
