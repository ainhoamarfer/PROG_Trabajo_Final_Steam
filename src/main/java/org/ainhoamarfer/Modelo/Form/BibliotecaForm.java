package org.ainhoamarfer.Modelo.Form;

import org.ainhoamarfer.Modelo.DTOs.ErrorDTO;
import org.ainhoamarfer.Modelo.Enums.ErrorType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class BibliotecaForm {

    private int usuarioId;
    private int juegoId;
    private LocalDate fechaAdquisicion;
    private Double tiempoJuego;
    private LocalDate fechaUltimaJugado;
    public Boolean instalado;

    public BibliotecaForm(int usuarioId, int juegoId, LocalDate fechaAdquisicion, Double tiempoJuego, LocalDate fechaUltimaJugado, Boolean instalado) {
        this.usuarioId = usuarioId;
        this.juegoId = juegoId;
        this.fechaAdquisicion = fechaAdquisicion;
        this.tiempoJuego = tiempoJuego;
        this.fechaUltimaJugado = fechaUltimaJugado;
        this.instalado = instalado;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public int getJuegoId() {
        return juegoId;
    }

    public LocalDate getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public Double getTiempoJuego() {
        return tiempoJuego;
    }

    public LocalDate getFechaUltimaJugado() {
        return fechaUltimaJugado;
    }

    public Boolean isInstalado() {
        return instalado;
    }

    public List<ErrorDTO> validar (BibliotecaForm form){
        List<ErrorDTO> errores = new ArrayList<>();

        // Usuario: obligatorio y debe existir TODO: comprobar que el usuario existe en el sistema
        if (usuarioId < 0) {
            errores.add(new ErrorDTO("usuarioId", ErrorType.REQUERIDO));
        }

        // Juego: obligatorio y debe existir TODO: comprobar que el juego existe en el sistema
        if (juegoId < 0) {
            errores.add(new ErrorDTO("juegoId", ErrorType.REQUERIDO));
        }

        // Un usuario no puede tener el mismo juego dos veces en su biblioteca TODO: comprobar que fechaAdquisicion no es anterior a la fecha de registro del usuario

        // Fecha de adquisición: obligatoria; no puede ser futura; no puede ser anterior a la fecha de registro del usuario
        if (fechaAdquisicion == null) {
            errores.add(new ErrorDTO("fechaAdquisicion", ErrorType.REQUERIDO));
        } else {
            LocalDate hoy = LocalDate.now();
            if (fechaAdquisicion.isAfter(hoy)) {
                errores.add(new ErrorDTO("fechaAdquisicion", ErrorType.VALOR_NO_VALIDO));
            }
        }

        // Tiempo de juego total: por defecto 0.0; debe ser >=0; máximo 1 decimal
        if (tiempoJuego == null) {
            tiempoJuego = 0.0;
        } else {
            if (tiempoJuego < 0) {
                errores.add(new ErrorDTO("tiempoJuego", ErrorType.VALOR_NO_VALIDO));
            }
            BigDecimal bd = BigDecimal.valueOf(tiempoJuego);
            if (bd.scale() > 1) {
                errores.add(new ErrorDTO("tiempoJuego", ErrorType.FORMATO_INVALIDO));
            }
        }

        // Última fecha de juego: opcional; no puede ser futura; no puede ser anterior a fecha de adquisición
        if (fechaUltimaJugado != null) {
            LocalDate hoy = LocalDate.now();
            if (fechaUltimaJugado.isAfter(hoy)) {
                errores.add(new ErrorDTO("fechaUltimaJugado", ErrorType.VALOR_NO_VALIDO));
            }
            if (fechaAdquisicion != null && fechaUltimaJugado.isBefore(fechaAdquisicion)) {
                errores.add(new ErrorDTO("fechaUltimaJugado", ErrorType.VALOR_NO_VALIDO));
            }
        }

        // Estado de instalación: por defecto NO_INSTALADO (false); debe ser INSTALADO o NO_INSTALADO
        if (instalado == null) {
            this.instalado = false;
        } else {
            this.instalado = instalado;
        }

        return errores;
    }
}
