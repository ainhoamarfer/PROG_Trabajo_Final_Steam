package org.ainhoamarfer.Controlador;

public class Util {

    //mappers: public static
    //validaciones generales

    public static boolean validarRecargaCartera(Double numero) {
        //Validaciones: Cantidad > 0, rango entre 5.00 y 500.00
        if(numero == null || numero < 5.00 || numero > 500.00){
            return  false;
        }else return true;
    }




}
