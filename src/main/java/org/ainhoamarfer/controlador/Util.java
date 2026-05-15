package org.ainhoamarfer.controlador;



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
