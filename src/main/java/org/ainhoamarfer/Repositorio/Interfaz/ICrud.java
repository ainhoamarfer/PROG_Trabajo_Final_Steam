package org.ainhoamarfer.Repositorio.Interfaz;

import java.util.List;
import java.util.Optional;

public interface ICrud<E, F, ID> {

    Optional<E> crear(F form);

    Optional<E> obtenerPorId(ID id);

    List<E> obtenerTodos();

    Optional<E> actualizar(ID id, F form);

    boolean eliminar(ID id);
}
