package ar.edu.unlam.tallerweb1.servicios;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.exceptions.CampoVacioException;
import ar.edu.unlam.tallerweb1.exceptions.ContraseniaCorta;
import ar.edu.unlam.tallerweb1.exceptions.FormatoDeEmailIncorrecto;
import ar.edu.unlam.tallerweb1.exceptions.UsuarioExistenteException;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioUsuario;

// Implelemtacion del Servicio de usuarios, la anotacion @Service indica a Spring que esta clase es un componente que debe
// ser manejado por el framework, debe indicarse en applicationContext que busque en el paquete ar.edu.unlam.tallerweb1.servicios
// para encontrar esta clase.
// La anotacion @Transactional indica que se debe iniciar una transaccion de base de datos ante la invocacion de cada metodo del servicio,
// dicha transaccion esta asociada al transaction manager definido en el archivo spring-servlet.xml y el mismo asociado al session factory definido
// en hibernateCOntext.xml. De esta manera todos los metodos de cualquier dao invocados dentro de un servicio se ejecutan en la misma transaccion
@Service("servicioLogin")
@Transactional
public class ServicioLoginImpl implements ServicioLogin {

	private RepositorioUsuario servicioLoginDao;

	@Autowired
	public ServicioLoginImpl(RepositorioUsuario servicioLoginDao) {
		this.servicioLoginDao = servicioLoginDao;
	}

	@Override
	public Usuario consultarUsuario(String email, String password) {
		return this.servicioLoginDao.buscarUsuario(email, password);
	}

	@Override
	public Usuario consultarMail(String email) {
		// TODO Auto-generated method stub
		return this.servicioLoginDao.buscarUsuario(email);
	}

	@Override
	public void guardarCliente(Usuario usuarioNuevo) throws UsuarioExistenteException, CampoVacioException, ContraseniaCorta, FormatoDeEmailIncorrecto {
		
		checkearDatos(usuarioNuevo);
		
		if (verificarUsuarioExistente(usuarioNuevo) && verificarUsuarioExistentePorNick(usuarioNuevo)) {
			this.servicioLoginDao.guardar(usuarioNuevo);
		} 
		else {
			throw new UsuarioExistenteException("Ya existe un usuario con ese email o nombre de usuario");
		}
	}

	private void checkearDatos(Usuario usuarioNuevo) throws FormatoDeEmailIncorrecto, ContraseniaCorta, CampoVacioException {
		
		if (verificarCaposRequeridos(usuarioNuevo)) {
			throw new CampoVacioException("Debe llenar todos los campos para registrarse");
		}
		if(usuarioNuevo.getPassword().length() < 8) {
			throw new ContraseniaCorta("La contraseña debe ser de por lo menos 8 caracteres");
		}
		if(!validarEmail(usuarioNuevo.getEmail())) {
			throw new FormatoDeEmailIncorrecto("El formato de email es incorrecto");
		}
		
	}

	// si da true no existe un usuario con ese email
	private Boolean verificarUsuarioExistente(Usuario usuario) {
		Usuario resultado = this.servicioLoginDao.buscarUsuario(usuario.getEmail());
		if (resultado != null) {
			return false;
		}
		return true;
	}

	// si da true no existe un usuario con ese nick
	private Boolean verificarUsuarioExistentePorNick(Usuario usuario) {
		Usuario resultado = this.servicioLoginDao.buscarUsuarioPorUsername(usuario.getUsuario());
		if (resultado != null && resultado.getUsuario() != usuario.getUsuario()) {
			return false;
		}
		return true;
	}
	
	private Boolean verificarCaposRequeridos(Usuario usuario) {
		if (usuario.getEmail().isBlank() || usuario.getUsuario().isBlank()
				|| usuario.getPassword().isBlank()) {
			return true;
		}
		return false;
	}
	
	private static Boolean validarEmail(String email) {
		
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + 
		"[a-zA-Z0-9_+&*-]+)*@" + 
		"(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		
		Pattern pattern = Pattern.compile(emailRegex);
		if(pattern.matcher(email).matches()) {
			return true;
		}
		return false;
		
	}
}
