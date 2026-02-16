package org.ainhoamarfer.Modelo.DTOs;

import java.time.Instant;

public record CriteriosBusqueda(String titulo, String descripcion, String desarrollador, Instant fechaLanzamiento, double precioBase, String categoria) {
}
