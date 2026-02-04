package Controlador;

import Excepciones.ValidacionExcepcion;
import Modelo.DTOs.ErrorDto;
import Modelo.Form.UsuarioForm;
import Repositorio.ImplementacionMemoria.UsuarioRepo;
import Repositorio.Interfaz.IUsuarioRepo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static Controlador.Util.validarStringLongitud;
import static Controlador.Util.validarStringNoVacia;

public class UsuarioControlador {

    private IUsuarioRepo repo;

    public UsuarioControlador(IUsuarioRepo repo) {
        this.repo = repo;
    }

    /**
     * Registrar nuevo usuario
     * Descripción: Crear una nueva cuenta de usuario en la plataforma
     * Entrada: Datos del formulario de registro (nombre de usuario, email, contraseña, nombre real,
     * país, fecha de nacimiento)
     * Salida: Usuario creado exitosamente o lista de errores de validación
     * Validaciones: Aplicar todas las restricciones definidas en la sección de validación de Usuario*/

    private UsuarioRepo registrarNuevoUsuario(UsuarioForm form) throws ValidacionExcepcion {

        //private String nombreUsuario; //único
        //private String email;
        //private String contrasena;
        //private String nombreReal;
        //private String pais;
        //private LocalDate fechaNaci;
        List<ErrorDto> errores = form.validar();

        }

    }

    /**
     * Consultar perfil
     * Descripción: Mostrar la información de un usuario específico
     * Entrada: ID o nombre del usuario a consultar
     * Salida: Información del perfil del usuario o mensaje de acceso denegado
     * Información mostrada: Nombre de usuario, avatar, país, fecha de registro, biblioteca y estadísticas de juego*/
    /**
     * Añadir saldo a cartera
     * Descripción: Recargar dinero en la cartera virtual de Steam del usuario
     * Entrada: ID del usuario, cantidad a añadir
     * Salida: Nuevo saldo de la cartera o mensaje de error
     * Validaciones: Cantidad > 0, cuenta activa, rango entre 5.00 y 500.00*/

    /**
     * Consultar saldo
     * Descripción: Mostrar el saldo disponible en la cartera Steam de un usuario
     * Entrada: ID del usuario
     * Salida: Saldo actual de la cartera (ejemplo: "45.67 €")
     * Validaciones: Usuario debe existir en el sistema
     */

    private void validarUsuarioForm (UsuarioForm form) throws ValidacionExcepcion {

        List<ErrorDto> errores = new ArrayList<>();

        if(!validarStringNoVacia(form.getNombreUsuario())){
            throw new ValidacionExcepcion(errores);
        }

    }
}
