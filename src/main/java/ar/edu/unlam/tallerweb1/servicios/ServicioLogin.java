package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.exceptions.CampoVacioException;
import ar.edu.unlam.tallerweb1.exceptions.UsuarioExistenteException;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

// Interface que define los metodos del Servicio de Usuarios.
public interface ServicioLogin {

	Usuario consultarUsuario(String email, String password);
	
	Usuario consultarMail(String email);
	
	void guardarCliente(Usuario usuario) throws UsuarioExistenteException, CampoVacioException;
}
