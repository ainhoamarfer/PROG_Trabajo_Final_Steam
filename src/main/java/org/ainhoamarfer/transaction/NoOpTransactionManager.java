package org.ainhoamarfer.transaction;

import org.ainhoamarfer.excepciones.ExcepcionValidacion;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * Implementación no-op de {@link ITransactionManager}.
 * Se usa con repositorios en memoria donde no existe el concepto de transacción.
 */
public class NoOpTransactionManager implements ITransactionManager {

    @Override
    public <T> T inTransaction(Supplier<T> work) throws ExcepcionValidacion {
        try {
            return work.get();
        }catch(ExcepcionValidacion e){
            throw e;
        }
        catch (Exception e) {
            try {
                return (T) Optional.empty();
            } catch (ClassCastException ex) {
                return null;
            }
        }
    }
}
