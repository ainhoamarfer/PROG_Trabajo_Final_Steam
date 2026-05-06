package org.ainhoamarfer.transaction;


import org.ainhoamarfer.dbconfig.HibernateUtil;
import org.ainhoamarfer.excepciones.ExcepcionValidacion;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * Implementación Hibernate de {@link ITransactionManager}.
 * Gestiona el ciclo de vida de la sesión y la transacción.
 * Expone {@link #getSession()} para que {@code HibernateAlumnoRepository}
 * pueda acceder a la sesión activa durante el bloque de trabajo.
 */
public class HibernateTransactionManager implements ITransactionManager, ISesionManager {
    private Session session;

    @SuppressWarnings("unchecked")
    @Override
    public <T> T inTransaction(ExceptionSupplier<T> work) throws ExcepcionValidacion {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            session = s;
            try {
                tx = s.beginTransaction();
                T result = work.get();
                tx.commit();
                return result;
            } catch (Exception e) {
                if (tx != null)
                    tx.rollback();
                throw e;
            }
        } finally {
            session = null;
        }
    }

    @Override
    public Session getSession() {
        return session;
    }
}
