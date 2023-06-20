package ar.edu.unlam.tallerweb1.servicios;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ar.edu.unlam.tallerweb1.exceptions.CampoVacioException;
import ar.edu.unlam.tallerweb1.exceptions.ContraseniaCorta;
import ar.edu.unlam.tallerweb1.exceptions.ContraseniaIncompatible;
import ar.edu.unlam.tallerweb1.exceptions.FormatoDeEmailIncorrecto;
import ar.edu.unlam.tallerweb1.exceptions.UsuarioExistenteException;
import ar.edu.unlam.tallerweb1.modelo.DatosLogin;
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
	private ServletContext servletContext;

	@Autowired
	public ServicioLoginImpl(RepositorioUsuario servicioLoginDao, ServletContext servletContext) {
		this.servicioLoginDao = servicioLoginDao;
		this.servletContext = servletContext;
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
	public void guardarCliente(Usuario usuarioNuevo)
			throws UsuarioExistenteException, CampoVacioException, ContraseniaCorta, FormatoDeEmailIncorrecto {

		checkearDatos(usuarioNuevo);

		if (verificarUsuarioExistente(usuarioNuevo.getEmail())
				&& verificarUsuarioExistentePorNick(usuarioNuevo.getEmail())) {
			this.servicioLoginDao.guardar(usuarioNuevo);
		} else {
			throw new UsuarioExistenteException("Ya existe un usuario con ese email o nombre de usuario");
		}
	}

	@Override
	public void cambiarContrasenia(DatosLogin datosLogin, Long idUsuario)
			throws ContraseniaCorta, ContraseniaIncompatible, CampoVacioException {
		Usuario usuario = this.servicioLoginDao.buscarUsuario(idUsuario);
		if (datosLogin.getPassword().isBlank() || datosLogin.getOldPassword().isBlank()) {
			throw new CampoVacioException("Debe completar los 2 campos para cambiar la contraseña");
		}
		if (!usuario.getPassword().equals(datosLogin.getOldPassword())) {
			throw new ContraseniaIncompatible("Contraseña antigua incorrecta");
		}
		if (datosLogin.getPassword().length() < 8) {
			throw new ContraseniaCorta("La contraseña debe ser de por lo menos 8 caracteres");
		}
		if (usuario.getPassword().equals(datosLogin.getPassword())) {
			throw new ContraseniaIncompatible("La nueva contraseña es igual a la anterior");
		}
		usuario.setPassword(datosLogin.getPassword());
	}

	@Override
	public void cambiarUsuario(DatosLogin datosLogin, Long idUsuario)
			throws UsuarioExistenteException, CampoVacioException {
		if (datosLogin.getUsuario().isBlank()) {
			throw new CampoVacioException("Debe ingresar un nombre de usuario");
		}
		if (!this.verificarUsuarioExistentePorNick(datosLogin.getUsuario())) {
			throw new UsuarioExistenteException("Ya existe un usuario con ese nickname");
		}
		Usuario usuario = this.servicioLoginDao.buscarUsuario(idUsuario);
		usuario.setUsuario(datosLogin.getUsuario());
	}

	@Override
	public void cambiarMail(DatosLogin datosLogin, Long idUsuario)
			throws FormatoDeEmailIncorrecto, UsuarioExistenteException, CampoVacioException {
		if (datosLogin.getEmail().isBlank()) {
			throw new CampoVacioException("Debe ingresar un email");
		}
		if (!this.verificarUsuarioExistente(datosLogin.getEmail())) {
			throw new UsuarioExistenteException("Ya existe un usuario con ese mail");
		}
		if (!this.validarEmail(datosLogin.getEmail())) {
			throw new FormatoDeEmailIncorrecto("El formato del mail es incorrecto");
		}
		Usuario usuario = this.servicioLoginDao.buscarUsuario(idUsuario);
		usuario.setEmail(datosLogin.getEmail());
	}

	@Override
	public void cambiarFotoPerfil(MultipartFile fotoPerfil, Long idUsuario) throws IOException {
		Usuario usuario = this.servicioLoginDao.buscarUsuario(idUsuario);
		if (usuario.getFotoPerfil() != null && !usuario.getFotoPerfil().isBlank()) {
			try {
				Files.delete(
						Paths.get(servletContext.getRealPath("") + "images/fotosPerfil/" + usuario.getFotoPerfil()));
			} catch (NoSuchFileException ex) {
				System.err.println(ex);
			}
		}
		guardarImagen(fotoPerfil);
		usuario.setFotoPerfil(fotoPerfil.getOriginalFilename());
	}

	private void guardarImagen(MultipartFile imagen) throws IOException {
		try {
			String fileName = imagen.getOriginalFilename();
			String uploadDir = servletContext.getRealPath("") + "images/fotosPerfil";
			Path uploadPath = Paths.get(uploadDir);
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			InputStream inputStream = imagen.getInputStream();
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ex) {
			throw new IOException("No se pudo guardar el archivo");
		}
	}

	private void checkearDatos(Usuario usuarioNuevo)
			throws FormatoDeEmailIncorrecto, ContraseniaCorta, CampoVacioException {

		if (!this.verificarCaposRequeridos(usuarioNuevo)) {
			throw new CampoVacioException("Debe llenar todos los campos para registrarse");
		}
		if (usuarioNuevo.getPassword().length() < 8) {
			throw new ContraseniaCorta("La contrasea debe ser de por lo menos 8 caracteres");
		}
		if (!this.validarEmail(usuarioNuevo.getEmail())) {
			throw new FormatoDeEmailIncorrecto("El formato de email es incorrecto");
		}

	}

	private Boolean verificarUsuarioExistente(String mail) {
		Usuario resultado = this.servicioLoginDao.buscarUsuario(mail);
		return resultado != null ? false : true;
	}

	private Boolean verificarUsuarioExistentePorNick(String usuario) {
		Usuario resultado = this.servicioLoginDao.buscarUsuarioPorUsername(usuario);
		return resultado != null ? false : true;
	}

	private Boolean verificarCaposRequeridos(Usuario usuario) {
		return usuario.getEmail().isBlank() || usuario.getUsuario().isBlank() || usuario.getPassword().isBlank() ? false
				: true;
	}

	private Boolean validarEmail(String email) {

		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

		Pattern pattern = Pattern.compile(emailRegex);
		return pattern.matcher(email).matches() ? true : false;
	}
}
