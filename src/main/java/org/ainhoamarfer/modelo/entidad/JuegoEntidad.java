package org.ainhoamarfer.modelo.entidad;

import jakarta.persistence.*;
import org.ainhoamarfer.modelo.enums.JuegoClasificacionEdad;
import org.ainhoamarfer.modelo.enums.JuegoEstado;


import java.time.LocalDate;

@Table(name = "juegos")
@Entity
public class JuegoEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column (name = "titulo")
    private String titulo;
    @Column (name = "descripcion")
    private String descripcion;
    @Column (name = "desarrollador")
    private String desarrollador;
    @Column (name = "fechaLanzamiento")
    private LocalDate fechaLanzamiento;
    @Column (name = "precio_base")
    private double precioBase;
    @Column (name = "descuento_Actual")
    private double descuentoActual;
    @Column (name = "categoria")
    private String categoria;
    @Column (name = "idiomas")
    private String idiomas;
    @Column (name = "clasificacion_Edad")
    private JuegoClasificacionEdad clasificacionEdad;
    @Column (name = "estado")
    private JuegoEstado estado;

    public JuegoEntidad(long id, String titulo, String descripcion, String desarrollador, LocalDate fechaLanzamiento, double precioBase, double descuentoActual, String categoria, String idiomas, JuegoClasificacionEdad clasificacionEdad) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.desarrollador = desarrollador;
        this.fechaLanzamiento = fechaLanzamiento;
        this.precioBase = precioBase;
        this.descuentoActual = descuentoActual;
        this.categoria = categoria;
        this.idiomas = idiomas;
        this.clasificacionEdad = clasificacionEdad;
        this.estado = JuegoEstado.DISPONIBLE;
    }

    public long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getDesarrollador() {
        return desarrollador;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public double getDescuentoActual() {
        return descuentoActual;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public JuegoClasificacionEdad getClasificacionEdad() {
        return clasificacionEdad;
    }

    public JuegoEstado getEstado() {
        return estado;
    }
}
