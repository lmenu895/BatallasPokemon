package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.modelo.Usuario;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

// implelemtacion del repositorio de usuarios, la anotacion @Repository indica a Spring que esta clase es un componente que debe
// ser manejado por el framework, debe indicarse en applicationContext que busque en el paquete ar.edu.unlam.tallerweb1.dao
// para encontrar esta clase.
@Repository("repositorioUsuario")
public class RepositorioUsuarioImpl implements RepositorioUsuario {

	// Maneja acciones de persistencia, normalmente estara inyectado el session
	// factory de hibernate
	// el mismo esta difinido en el archivo hibernateContext.xml
	private SessionFactory sessionFactory;

	@Autowired
	public RepositorioUsuarioImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public Usuario buscarUsuario(String email, String password) {

		// Se obtiene la sesion asociada a la transaccion iniciada en el servicio que
		// invoca a este metodo y se crea un criterio
		// de busqueda de Usuario donde el email y password sean iguales a los del
		// objeto recibido como parametro
		// uniqueResult da error si se encuentran mas de un resultado en la busqueda.
		final Session session = sessionFactory.getCurrentSession();
		//password = hashUtil(password, this.buscar(email).getSalt()); //Buscar hash de la contraseña
		return (Usuario) session.createCriteria(Usuario.class).add(Restrictions.eq("email", email))
				.add(Restrictions.eq("password", password)).uniqueResult();
	}

	@Override
	public void guardar(Usuario usuario) {
		/*byte[] salt = generateSalt();
		usuario.setSalt(salt);
		usuario.setPassword(hashUtil(usuario.getPassword(), salt));*/ //Guardando un hash seguro de la contraseña del usuario
		this.sessionFactory.getCurrentSession().save(usuario);
	}

	@Override
	public Usuario buscarUsuario(String email) {
		return (Usuario) this.sessionFactory.getCurrentSession().createCriteria(Usuario.class)
				.add(Restrictions.eq("email", email)).uniqueResult();
	}

	@Override
	public Usuario buscarUsuario(Long id) {
		return (Usuario) this.sessionFactory.getCurrentSession().createCriteria(Usuario.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
	}

	@Override
	public void modificar(Usuario usuario) {
		this.sessionFactory.getCurrentSession().update(usuario);
	}

	@Override
	public Usuario buscarUsuarioPorUsername(String userName) {
		return (Usuario) this.sessionFactory.getCurrentSession().createCriteria(Usuario.class)
				.add(Restrictions.eq("usuario", userName)).uniqueResult();
	}

	// Este código sirve para crear un hash seguro y almacenarlo en la bd en lugar de la contraseña, como no es requerido en la materia no lo implementé
	/*private String hashUtil(String password, byte[] salt) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.update(salt);
			byte[] encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
			StringBuilder hexString = new StringBuilder(2 * encodedhash.length);
			for (byte b : encodedhash) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1) {
					hexString.append('0');
				}
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException ex) {
			throw new RuntimeException(ex);
		}
	}*/

	/*private byte[] generateSalt() {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		return salt;
	}*/
}