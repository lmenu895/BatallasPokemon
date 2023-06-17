package ar.edu.unlam.tallerweb1.servicios;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import ar.edu.unlam.tallerweb1.exceptions.CampoVacioException;
import ar.edu.unlam.tallerweb1.exceptions.ContraseniaCorta;
import ar.edu.unlam.tallerweb1.exceptions.ContraseniaIncompatible;
import ar.edu.unlam.tallerweb1.exceptions.FormatoDeEmailIncorrecto;
import ar.edu.unlam.tallerweb1.exceptions.UsuarioExistenteException;
import ar.edu.unlam.tallerweb1.modelo.DatosLogin;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

// Interface que define los metodos del Servicio de Usuarios.
public interface ServicioLogin {

	Usuario consultarUsuario(String email, String password);
	
	Usuario consultarMail(String email);
	
	void guardarCliente(Usuario usuario) throws UsuarioExistenteException, CampoVacioException, ContraseniaCorta, FormatoDeEmailIncorrecto;

	void cambiarContrasenia(DatosLogin datosLogin, Long idUsuario) throws ContraseniaCorta, ContraseniaIncompatible, CampoVacioException;

	void cambiarUsuario(DatosLogin datosLogin, Long idUsuario) throws UsuarioExistenteException, CampoVacioException;

	void cambiarMail(DatosLogin datosLogin, Long idUsuario) throws FormatoDeEmailIncorrecto, UsuarioExistenteException, CampoVacioException;

	void cambiarFotoPerfil(MultipartFile fotoPerfil, Long idUsuario) throws IOException;
}
