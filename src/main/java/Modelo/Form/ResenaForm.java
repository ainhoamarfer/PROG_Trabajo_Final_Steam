package Modelo.Form;

public class ResenaForm {

    private int usuarioId;
    private int juegoId;
    private boolean recomendado;
    private String texto;

    public ResenaForm(int usuarioId, int juegoId, boolean recomendado, String texto) {
        this.usuarioId = usuarioId;
        this.juegoId = juegoId;
        this.recomendado = recomendado;
        this.texto = texto;
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
