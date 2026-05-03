package org.ainhoamarfer.transaction;

import org.ainhoamarfer.excepciones.ExcepcionValidacion;

public interface ExceptionSupplier<T> {

    T get() throws ExcepcionValidacion;
}
