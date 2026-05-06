package org.ainhoamarfer.controlador;

import org.ainhoamarfer.excepciones.ExcepcionValidacion;
import org.ainhoamarfer.modelo.dtos.ErrorDTO;
import org.ainhoamarfer.modelo.entidad.CompraEntidad;
import org.ainhoamarfer.modelo.entidad.JuegoEntidad;
import org.ainhoamarfer.modelo.entidad.UsuarioEntidad;
import org.ainhoamarfer.modelo.enums.ErrorType;
import org.ainhoamarfer.repositorio.interfaz.ICompraRepo;
import org.ainhoamarfer.repositorio.interfaz.IJuegosRepo;
import org.ainhoamarfer.repositorio.interfaz.IUsuarioRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Util {
    public static final double MIN_RECARGA = 5.00;
    public static final double MAX_RECARGA = 500.00;

    //mappers: public static
    //validaciones generales

    public static boolean validarRecargaCartera(Double numero) {
        //Validaciones: Cantidad > 0, rango entre 5.00 y 500.00
        if(numero == null || numero < MIN_RECARGA || numero > MAX_RECARGA){
            return  false;
        }else return true;
    }
}
