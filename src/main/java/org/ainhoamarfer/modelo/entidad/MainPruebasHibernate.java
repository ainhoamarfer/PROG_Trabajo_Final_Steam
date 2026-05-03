package org.ainhoamarfer.modelo.entidad;

import org.ainhoamarfer.dbconfig.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class MainPruebasHibernate {
    //Comando para ejecutar el jar de h2 en la terminal, para iniciar la consola web de h2 y poder ver la base de datos creada por hibernate
    // java -jar C:\Users\34691\.m2\repository\com\h2database\h2\2.3.232\h2-2.3.232.jar


    static void main() {

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.close();


    }
}
