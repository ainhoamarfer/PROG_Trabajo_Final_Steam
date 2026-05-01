package org.ainhoamarfer.controlador;

import org.ainhoamarfer.excepciones.ExcepcionValidacion;
import org.ainhoamarfer.mapper.Mapper;
import org.ainhoamarfer.modelo.dtos.BibliotecaDTO;
import org.ainhoamarfer.modelo.dtos.ErrorDTO;
import org.ainhoamarfer.modelo.entidad.BibliotecaEntidad;
import org.ainhoamarfer.modelo.entidad.JuegoEntidad;
import org.ainhoamarfer.modelo.entidad.UsuarioEntidad;
import org.ainhoamarfer.modelo.enums.ErrorType;
import org.ainhoamarfer.modelo.form.BibliotecaForm;
import org.ainhoamarfer.repositorio.interfaz.IBibliotecaRepo;
import org.ainhoamarfer.repositorio.interfaz.IJuegosRepo;
import org.ainhoamarfer.repositorio.interfaz.IUsuarioRepo;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BibliotecaControlador {

    /*
    Ver biblioteca personal
    Añadir juego a biblioteca
    Eliminar juego de biblioteca
    Actualizar tiempo de juego
    Consultar última sesión
    Filtrar biblioteca (Ficheros)
    Ver estadísticas de biblioteca
     */


    private IBibliotecaRepo biblioRepo;
    private IJuegosRepo juegoRepo;
    private final IUsuarioRepo usuarioRepo;

    public BibliotecaControlador(IBibliotecaRepo biblioRepo, IUsuarioRepo usuarioRepo, IJuegosRepo juegoRepo) {
        this.biblioRepo = biblioRepo;
        this.usuarioRepo = usuarioRepo;
        this.juegoRepo = juegoRepo;
    }

    /**
     * Ver biblioteca personal
     * Descripción: Listar todos los juegos que posee un usuario en su biblioteca
     * @param idUsuario ID del usuario
     * @param orden orden opcional (alfabético, tiempo de juego, última sesión, fecha de adquisición)
     * @return Lista de juegos en la biblioteca con sus datos de uso
     * Datos mostrados: Título del juego, tiempo jugado, última sesión, estado de instalación
     */
    public List<BibliotecaDTO> verBibliotecaPersonal(long idUsuario, String orden) throws ExcepcionValidacion {
        List<ErrorDTO> errores = new ArrayList<>();

      List<BibliotecaEntidad> bibliotecas = biblioRepo.obtenerPorIdUsuario(idUsuario)
              .stream()
              .filter(b -> b.getUsuarioId() == idUsuario)
              .collect(Collectors.toCollection(ArrayList::new));

      if (bibliotecas.isEmpty()) {
          return new ArrayList<>();
      }


      if (!orden.isEmpty()){
          List<BibliotecaEntidad> bibliotecasOrdenadas = bibliotecas.stream()
                  .sorted( (b1, b2) -> "fecha de adquisición".equalsIgnoreCase(orden) ? b2.getFechaAdquisicion().compareTo(b1.getFechaAdquisicion()) : 0) // Ordenar por fecha si se selecciona "recientes"
                  .toList();

          if (bibliotecasOrdenadas.isEmpty()) {
              errores.add(new ErrorDTO("bibliotecasOrdenadas", ErrorType.NO_ENCONTRADO));
              throw new ExcepcionValidacion(errores);
          }

          List<BibliotecaDTO> bibliotecasOrdMap = new ArrayList<>();
          BibliotecaEntidad unaBiblioteca = bibliotecasOrdenadas.get(0);
          UsuarioEntidad usuario = usuarioRepo.obtenerPorId(unaBiblioteca.getUsuarioId()).orElse(null);
          JuegoEntidad juego = juegoRepo.obtenerPorId(unaBiblioteca.getJuegoId()).orElse(null);

          for (BibliotecaEntidad biblioteca : bibliotecasOrdenadas) {
              bibliotecasOrdMap.add(Mapper.mapDeBiblioteca(biblioteca, Mapper.mapDeUsuario(usuario), Mapper.mapDeJuego(juego)));

          }
          return bibliotecasOrdMap;

      } else {
          BibliotecaEntidad unaBiblioteca = bibliotecas.get(0);
          UsuarioEntidad usuario = usuarioRepo.obtenerPorId(unaBiblioteca.getUsuarioId()).orElse(null);
          JuegoEntidad juego = juegoRepo.obtenerPorId(unaBiblioteca.getJuegoId()).orElse(null);

          List<BibliotecaDTO> bibliotecasMap = new ArrayList<>();
          for (BibliotecaEntidad biblioteca : bibliotecas) {
              bibliotecasMap.add(Mapper.mapDeBiblioteca(biblioteca, Mapper.mapDeUsuario(usuario), Mapper.mapDeJuego(juego)));
          }
          return bibliotecasMap;
      }
    }

    /**
     * Añadir juego a biblioteca
     * Descripción: Agregar un juego adquirido a la biblioteca del usuario
     * @param idUsuario ID del usuario
     * @param idJuego ID del juego
     * @return Biblioteca (antes era boolean pero creo que es mejor devolver la biblioteca con el nuevo juego añadido)
     * Validaciones: Usuario existe, juego existe, no duplicado, compra verificada
     */
    public BibliotecaDTO anadirJuegoBiblioteca(long idUsuario, long idJuego) throws ExcepcionValidacion {
        List<ErrorDTO> errores = new ArrayList<>();

        if (usuarioRepo.obtenerPorId(idUsuario).isEmpty()) {
            errores.add(new ErrorDTO("usuario", ErrorType.NO_ENCONTRADO));
            throw new ExcepcionValidacion(errores);
        }

        List<BibliotecaEntidad> bibliotecasUsuarioLista = biblioRepo.obtenerPorIdUsuario(idUsuario);


        //ver si existe el juego
        juegoRepo.obtenerPorId(idJuego).orElseThrow(() -> {
            errores.add(new ErrorDTO("juego", ErrorType.NO_ENCONTRADO));
            return new ExcepcionValidacion(errores);
        });

        //que no este ya en esta biblioteca
        for (BibliotecaEntidad biblioteca : bibliotecasUsuarioLista) {
            if (biblioteca.getJuegoId() == idJuego) {
                errores.add(new ErrorDTO("Este juego ya existe en esta biblioteca", ErrorType.DUPLICADO));
                throw new ExcepcionValidacion(errores);
            }
        }

        BibliotecaForm nuevaBiblioteca = new BibliotecaForm(idUsuario, idJuego, LocalDate.now(), 0.0, null, false);

        Optional<BibliotecaEntidad> biblioCreada = biblioRepo.crear(nuevaBiblioteca);
        BibliotecaEntidad biblioteca = biblioCreada.orElse(null);

        UsuarioEntidad usuario = usuarioRepo.obtenerPorId(biblioteca.getUsuarioId()).orElse(null);
        JuegoEntidad juego = juegoRepo.obtenerPorId(biblioteca.getJuegoId()).orElse(null);

        return Mapper.mapDeBiblioteca(biblioteca, Mapper.mapDeUsuario(usuario), Mapper.mapDeJuego(juego));
    }

    /**
     * Eliminar juego de biblioteca
     * Descripción: Quitar un juego de la biblioteca del usuario
     * @param idUsuario ID del usuario
     * @param idJuego ID del juego
     * Validaciones: Entrada existe en la biblioteca
     */
    public void eliminarJuegoBiblioteca(long idUsuario, long idJuego) throws ExcepcionValidacion {

        Optional <BibliotecaEntidad> bibliotecaOpt = biblioRepo.obtenerPorIdUsuarioYIdJuego(idUsuario, idJuego);

        if (bibliotecaOpt.isEmpty()) {
            List<ErrorDTO> errores = new ArrayList<>();
            errores.add(new ErrorDTO("biblioteca", ErrorType.NO_ENCONTRADO));
            throw new ExcepcionValidacion(errores);
        } else {
            BibliotecaEntidad biblioteca = bibliotecaOpt.get();
            biblioRepo.eliminar(biblioteca.getId());
        }
    }

    /**
     * Actualizar tiempo de juego
     * Descripción: Registrar y actualizar las horas jugadas de un juego
     * @param idUsuario ID del usuario
     * @param idJuego ID del juego
     * @param horasAAnadir horas a añadir
     * @return BibliotecaDTO
     * Validaciones: Biblioteca existe, horas positivas
     */
    public BibliotecaDTO actualizarTiempoJuego(long idUsuario, long idJuego, double horasAAnadir) throws ExcepcionValidacion {
        List<ErrorDTO> errores = new ArrayList<>();

        Optional <BibliotecaEntidad> bibliotecaOpt = biblioRepo.obtenerPorIdUsuarioYIdJuego(idUsuario, idJuego);

        if (bibliotecaOpt.isEmpty()) {
            errores.add(new ErrorDTO("Biblioteca", ErrorType.NO_ENCONTRADO));
            throw new ExcepcionValidacion(errores);
        }

        if (horasAAnadir <= 0) {
            errores.add(new ErrorDTO("Horas a añadir", ErrorType.VALOR_NO_VALIDO));
            throw new ExcepcionValidacion(errores);
        } else { BibliotecaEntidad biblioteca = bibliotecaOpt.orElse(null);

        double nuevoTiempoJuego = biblioteca.getTiempoJuego() + horasAAnadir;

        BibliotecaForm form = new BibliotecaForm(biblioteca.getUsuarioId(), biblioteca.getJuegoId(), biblioteca.getFechaAdquisicion(), nuevoTiempoJuego, biblioteca.getFechaUltimaJugado(), biblioteca.isInstalado());
        biblioRepo.actualizar(biblioteca.getId(), form);


        UsuarioEntidad usuario = usuarioRepo.obtenerPorId(biblioteca.getUsuarioId()).orElse(null);
        JuegoEntidad juego = juegoRepo.obtenerPorId(biblioteca.getJuegoId()).orElse(null);

        BibliotecaEntidad biblio = biblioRepo.obtenerPorId(biblioteca.getId()).orElse(null);

        return Mapper.mapDeBiblioteca(biblio, Mapper.mapDeUsuario(usuario), Mapper.mapDeJuego(juego));}
    }

    /**
     * Consultar última sesión
     * Descripción: Ver la última vez que se jugó a un juego específico
     * @param idUsuario ID del usuario
     * @param idJuego ID del juego
     * @return BibliotecaDTO
     * Validaciones: Biblioteca existe, formato de fecha legible
     */
    public BibliotecaDTO consultarUltimaSesion(long idUsuario, long idJuego) throws ExcepcionValidacion {
        List<ErrorDTO> errores = new ArrayList<>();

        Optional <BibliotecaEntidad> bibliotecaOpt = biblioRepo.obtenerPorIdUsuarioYIdJuego(idUsuario, idJuego);

        if (bibliotecaOpt.isEmpty()) {
            errores.add(new ErrorDTO("Biblioteca", ErrorType.NO_ENCONTRADO));
            throw new ExcepcionValidacion(errores);
        } else {
            BibliotecaEntidad biblioteca = bibliotecaOpt.orElse(null);

            UsuarioEntidad usuario = usuarioRepo.obtenerPorId(biblioteca.getUsuarioId()).orElse(null);
            JuegoEntidad juego = juegoRepo.obtenerPorId(biblioteca.getJuegoId()).orElse(null);
            return Mapper.mapDeBiblioteca(biblioteca, Mapper.mapDeUsuario(usuario), Mapper.mapDeJuego(juego));
        }
    }

    /**
     * Filtrar biblioteca (Ficheros)
     * Descripción: Buscar juegos en la biblioteca personal según criterios
     * @param idUsuario ID del usuario
     * @param estadoInstalacion estado de instalación
     * @param textoBusqueda texto de búsqueda
     * @return Lista filtrada de juegos de la biblioteca
     * Datos mostrados: Título, tiempo jugado, estado
     */
    public List<BibliotecaDTO> filtrarBiblioteca(long idUsuario, boolean estadoInstalacion, String textoBusqueda) {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Ver estadísticas de biblioteca
     * Descripción: Mostrar métricas generales de la biblioteca del usuario
     * @param idUsuario ID del usuario
     * @return Objeto con todas las estadísticas calculadas
     * Estadísticas: Total juegos, horas totales, juegos instalados, juego más jugado, valor total, juegos nunca jugados
     */
    public Object verEstadisticasBiblioteca(long idUsuario) {


        throw new UnsupportedOperationException("Not implemented");
    }
}
