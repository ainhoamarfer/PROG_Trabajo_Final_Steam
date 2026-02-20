package org.ainhoamarfer.Modelo.Form;

import org.ainhoamarfer.Modelo.DTOs.ErrorDTO;
import org.ainhoamarfer.Modelo.Enums.ErrorType;
import org.ainhoamarfer.Modelo.Enums.UsuarioEstadoCuenta;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UsuarioForm {

    private static final Set<String> PAISES_VALIDOS = Set.of(
        "españa", "francia", "alemania", "italia", "reino unido", "estados unidos", "canadá", "méxico", "brasil", "argentina",
        "japón", "china", "india", "australia", "sudáfrica", "egipto", "rusia", "turquía", "corea del sur", "países bajos"
    );

    private String nombreUsuario; //único
    private String email;
    private String contrasena;
    private String nombreReal;
    private String pais;
    private LocalDate fechaNaci;
    private LocalDate fechaRegistro;
    private String avatar;
    private Double saldoCartera;
    private UsuarioEstadoCuenta estadoCuenta;

    public UsuarioForm(String nombreUsuario, String email, String contrasena, String nombreReal, String pais, LocalDate fechaNaci, LocalDate fechaRegistro,
                       String avatar, Double saldoCartera, UsuarioEstadoCuenta estadoCuenta) {
        this.nombreUsuario = nombreUsuario;
        this.email = email;
        this.contrasena = contrasena;
        this.nombreReal = nombreReal;
        this.pais = pais;
        this.fechaNaci = fechaNaci;
        this.fechaRegistro = fechaRegistro;
        this.avatar = avatar;
        this.saldoCartera = saldoCartera;
        this.estadoCuenta = estadoCuenta;
    }

    public UsuarioEstadoCuenta getEstadoCuenta() {
        return estadoCuenta;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getEmail() {
        return email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getNombreReal() {
        return nombreReal;
    }

    public String getPais() {
        return pais;
    }

    public LocalDate getFechaNaci() {
        return fechaNaci;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public String getAvatar() {
        return avatar;
    }

    public double getSaldoCartera() {
        return saldoCartera;
    }



    public List<ErrorDTO> validar (UsuarioForm form){

        List<ErrorDTO> errores = new ArrayList<>();

        //Nombre usuario
        if (nombreUsuario == null || nombreUsuario.isBlank()) {
            errores.add(new ErrorDTO("nombreUsuario", ErrorType.REQUERIDO));
        }
        if (nombreUsuario.length() < 3 || nombreUsuario.length() > 20) {
            errores.add(new ErrorDTO("nombreUsuario", ErrorType.LONGITUD_INVALIDA));
        }
        if (!nombreUsuario.matches("^[a-zA-Z_][a-zA-Z0-9_-]*$")) {
            errores.add(new ErrorDTO("nombreUsuario", ErrorType.FORMATO_INVALIDO));
        }

        //Email - TODO validar que el email es único en el sistema
        if (email == null || email.isBlank()) {
            errores.add(new ErrorDTO("email", ErrorType.REQUERIDO));
        }
        if (!email.contains("@")) {
            errores.add(new ErrorDTO("email", ErrorType.FORMATO_INVALIDO));
        }

        //Contraseña
        if (contrasena == null || contrasena.isBlank()) {
            errores.add(new ErrorDTO("contrasena", ErrorType.REQUERIDO));
        }
        if (contrasena.length() < 8) {
            errores.add(new ErrorDTO("contrasena", ErrorType.LONGITUD_INVALIDA));
        }
        if (!contrasena.matches("(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+")) {
            errores.add(new ErrorDTO("contrasena", ErrorType.FORMATO_INVALIDO));
        }

        //Nombre real
        if(nombreReal== null || nombreUsuario.isBlank()) {
            errores.add(new ErrorDTO("nombreUsuario", ErrorType.REQUERIDO));
        }
        if (nombreReal.length() < 2 || nombreReal.length() > 50) {
            errores.add(new ErrorDTO("nombreUsuario", ErrorType.LONGITUD_INVALIDA));
        }

        //Pais - Debe ser uno de los países válidos: España, Francia, Alemania, Italia, Reino Unido, Estados Unidos, Canadá, México, Brasil,
        // Argentina, Japón, China, India, Australia, Sudáfrica, Egipto, Rusia, Turquía, Corea del Sur, Países Bajos
        if(pais == null || pais.isBlank()) {
            errores.add(new ErrorDTO("pais", ErrorType.REQUERIDO));
        } else {
            if (!PAISES_VALIDOS.contains(pais.toLowerCase())) {
                errores.add(new ErrorDTO("pais", ErrorType.VALOR_NO_VALIDO));
            }
        }

        //Fecha nacimient
        if (fechaNaci == null) {
            errores.add(new ErrorDTO("fechaNaci", ErrorType.REQUERIDO));
        }
        LocalDate hoy = LocalDate.now();
        if (fechaNaci.isAfter(hoy)) {
            errores.add(new ErrorDTO("fechaNaci", ErrorType.VALOR_NO_VALIDO));
        }
        int edad = hoy.getYear() - fechaNaci.getYear();
        if (edad < 13) {
            errores.add(new ErrorDTO("fechaNaci", ErrorType.VALOR_NO_VALIDO));
        }

        //Fecha registro
        if (fechaRegistro != null) {
            errores.add(new ErrorDTO("fechaRegistro", ErrorType.VALOR_NO_VALIDO));
        }

        //Avatar
        if(avatar != null || !avatar.isBlank()) {
            if(avatar.length() > 100){
                errores.add(new ErrorDTO("avatar", ErrorType.LONGITUD_INVALIDA));
            }
        }

        //Saldo
        if (saldoCartera < 0) {
            errores.add(new ErrorDTO("saldoCartera", ErrorType.VALOR_NO_VALIDO));
        }
        BigDecimal numeroDecimal = BigDecimal.valueOf(saldoCartera);
        if (numeroDecimal.scale() > 2) {
            errores.add(new ErrorDTO("saldoCartera", ErrorType.FORMATO_INVALIDO));
        }

        return errores;
    }

}
