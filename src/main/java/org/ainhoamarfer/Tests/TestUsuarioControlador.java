package org.ainhoamarfer.Tests;

import org.ainhoamarfer.Controlador.UsuarioControlador;
import org.ainhoamarfer.Excepciones.ExcepcionValidacion;
import org.ainhoamarfer.Modelo.DTOs.ErrorDTO;
import org.ainhoamarfer.Modelo.Enums.ErrorType;
import org.ainhoamarfer.Modelo.Form.UsuarioForm;
import org.ainhoamarfer.Modelo.Entidad.UsuarioEntidad;
import org.ainhoamarfer.Repositorio.Interfaz.IUsuarioRepo;
import org.ainhoamarfer.Vista.SteamVista;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestUsuarioControlador {

	@Mock
	private IUsuarioRepo usuarioRepo;

	@Mock
	private SteamVista vista;

	private UsuarioControlador controlador;

	@BeforeEach
	void setUp() {
		controlador = new UsuarioControlador(usuarioRepo, vista);
	}

	@Test
	void registrarNuevoUsuario_usuarioExiste_lanzaExcepcionValidacion() {
		UsuarioForm form = mock(UsuarioForm.class);
		when(form.validar(form)).thenReturn(new ArrayList<>());
		when(form.getNombreUsuario()).thenReturn("usuarioExistente");
		when(usuarioRepo.obtenerPorNombreUsuario("usuarioExistente")).thenReturn(Optional.of(mock(UsuarioEntidad.class)));

		assertThrows(ExcepcionValidacion.class, () -> controlador.registrarNuevoUsuario(form));
	}

	@Test
	void registrarNuevoUsuario_formularioInvalido_lanzaExcepcionValidacion() {
		UsuarioForm form = mock(UsuarioForm.class);
		List<ErrorDTO> errores = new ArrayList<>();
		errores.add(new ErrorDTO("campo", ErrorType.VALOR_NO_VALIDO));
		when(form.validar(form)).thenReturn(errores);

		assertThrows(ExcepcionValidacion.class, () -> controlador.registrarNuevoUsuario(form));
	}

	@Test
	void consultarPerfil_usuarioNoEncontrado_lanzaExcepcionValidacion() {
		when(usuarioRepo.obtenerPorId(anyLong())).thenReturn(Optional.empty());

		assertThrows(ExcepcionValidacion.class, () -> controlador.consultarPerfil(1L));
	}

	@Test
	void consultarSaldoCartera_usuarioExiste_devuelveSaldo() throws ExcepcionValidacion {
		UsuarioEntidad usuario = mock(UsuarioEntidad.class);
		when(usuarioRepo.obtenerPorId(2L)).thenReturn(Optional.of(usuario));
		when(usuario.getSaldoCartera()).thenReturn(75.5);

		Double saldo = controlador.consultarSaldoCartera(2L);

		assertEquals(75.5, saldo);
	}
}

