package org.ainhoamarfer.transaction;

import jakarta.transaction.TransactionManager;
import org.ainhoamarfer.controlador.JuegosControlador;
import org.ainhoamarfer.modelo.enums.JuegoClasificacionEdad;
import org.ainhoamarfer.modelo.enums.JuegoEstado;
import org.ainhoamarfer.modelo.form.JuegoForm;
import org.ainhoamarfer.repositorio.implementacion_hibernate.JuegoRepoHibernate;

import java.time.LocalDate;

public class MainHibernate {

    public static void main() {

        ITransactionManager tm = new HibernateTransactionManager();
        JuegosControlador jControler = new JuegosControlador(new JuegoRepoHibernate((ISesionManager) tm), tm);

        JuegoForm validForm = new JuegoForm(
                "Half-Life 3",
                "El juego más esperado de la historia.",
                "Valve",
                LocalDate.now().minusDays(1),
                29.99,
                0,
                "Acción",
                "Inglés",
                JuegoClasificacionEdad.PEGI_12,
                JuegoEstado.DISPONIBLE);

        var juego = jControler.anadirJuego(validForm);
        var juego2 = jControler.anadirJuego(validForm);
        System.out.println(juego);
        System.out.println(juego2);
    }
}
