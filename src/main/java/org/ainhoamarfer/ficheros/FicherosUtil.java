package org.ainhoamarfer.ficheros;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class FicherosUtil {

    private static final String SEPARATOR = ", ";

    public static void main(String[] args) {

        Path path = leerPathUsuario();

        //escribirFichero(numeros, path);
    }

    private static Path leerPathUsuario() {

        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce la ruta del fichero de salida:");
        String ruta = sc.nextLine();

        Path path = Path.of(ruta);

        return path;
    }

    private static void escribirFichero(List<Integer> numeros, Path path) {

        // Precondiciones
        // correctamente
        if (numeros == null) {
            throw new IllegalArgumentException("numeros: null");
        }
        if (path == null) {
            throw new IllegalArgumentException("path: null");
        }
        if (Files.exists(path)) {
            throw new IllegalArgumentException("El path ya existe: " + path);
        }

        // Cuerpo del métod
        String s = numeros.isEmpty() ? "No se introdujeron números" // Si la lista de numeros está vacía, la cadena resultante es un mensaje indicando que no se introdujeron números
                : numeros.stream()
                .map(n -> n + "") // Convierte el numero a su String asociada
                .reduce("", (acc, n) -> acc + SEPARATOR + n);
        // Concatena cada numero a la cadena acumulada, separando por el SEPARATOR

        s = s.substring(0, s.length() - SEPARATOR.length()); // Elimina del final de la cadena

        try {
            Files.writeString(path, s);
        } catch (IOException e) {
            System.err.printf("Error escribiendo numeros: %s en path: %s%n", s, path);
            e.printStackTrace();
        }
    }
}
