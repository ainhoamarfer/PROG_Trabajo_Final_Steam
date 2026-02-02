package Modelo.Form;

import Modelo.Enums.JuegoClasificacionEdad;

import java.time.LocalDate;

public class JuegoForm {

    private String titulo;
    private String desarrollador;
    private LocalDate fechaLanzamiento;
    private double precioBase;
    private JuegoClasificacionEdad clasificacionEdad;

    public JuegoForm(String titulo, String desarrollador, LocalDate fechaLanzamiento, double precioBase, JuegoClasificacionEdad clasificacionEdad) {
        this.titulo = titulo;
        this.desarrollador = desarrollador;
        this.fechaLanzamiento = fechaLanzamiento;
        this.precioBase = precioBase;
        this.clasificacionEdad = clasificacionEdad;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDesarrollador() {
        return desarrollador;
    }

    public void setDesarrollador(String desarrollador) {
        this.desarrollador = desarrollador;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(LocalDate fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public JuegoClasificacionEdad getClasificacionEdad() {
        return clasificacionEdad;
    }

    public void setClasificacionEdad(JuegoClasificacionEdad clasificacionEdad) {
        this.clasificacionEdad = clasificacionEdad;
    }
}
