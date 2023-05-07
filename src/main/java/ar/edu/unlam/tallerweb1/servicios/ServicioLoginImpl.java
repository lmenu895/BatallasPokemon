package ar.edu.unlam.tallerweb1.servicios;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		return this.servicioLoginDao.buscar(email);
	}

	@Override
	public void guardarCliente(Usuario usuarioNuevo) throws UsuarioExistenteException {
		if (verificarUsuarioExistente(usuarioNuevo)) {
			this.servicioLoginDao.guardar(usuarioNuevo);
		} else {
			throw new UsuarioExistenteException("Ya existe un usuario con ese mail");
		}
	}

	// si da true no existe un usuario con ese email
	@Override
	public Boolean verificarUsuarioExistente(Usuario usuario) {
		Usuario resultado = this.servicioLoginDao.buscar(usuario.getEmail());
		if (resultado != null) {
			return false;
		}
		return true;
	}

}
