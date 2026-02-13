package org.ainhoamarfer.Controlador;

public class Util {

    //mappers: public static
    //validaciones generales

    public static boolean validarStringNoVacia(String texto) {

        if (texto == null || texto.isEmpty()) {
            return false;
        }
        else return true;
    }

    public static boolean validarStringCaracteres(String texto) {

        for (char c : texto.toCharArray()) {
            if (!(Character.isLetterOrDigit(c) || c == '-' || c == '_')) {
                return false;
            }
        }
        return true;
    }

    public static boolean validarStringLongitud(String texto, int longitudMin, int longitudMax) {

        if (longitudMin > 0 && texto.length() < longitudMin) {
            return false;
        }
        if (longitudMax > 0 && texto.length() > longitudMax) {
            return false;
        }
        else return true;
    }




}
