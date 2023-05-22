package ar.edu.unlam.tallerweb1.modelo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

// Clase que modela el concepto de Usuario, la anotacion @Entity le avisa a hibernate que esta clase es persistible
// el paquete ar.edu.unlam.tallerweb1.modelo esta indicado en el archivo hibernateCOntext.xml para que hibernate
// busque entities en el
@Entity
public class Usuario {

	// La anotacion id indica que este atributo es el utilizado como clave primaria de la entity, se indica que el valor es autogenerado.
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	// para el resto de los atributo no se usan anotaciones entonces se usa el default de hibernate: la columna se llama igual que
	// el atributo, la misma admite nulos, y el tipo de dato se deduce del tipo de dato de java.
	private String usuario;
	private String email;
	private String password;
	private Boolean esAdmin = false;
<<<<<<< HEAD
	private Boolean principiante = true;
	private Integer puntos=1000;
	private byte[] salt; //Sirve para guardar hashes seguros en lugar de contraseñas en la base de datos, no está implementado
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
=======
	private Integer puntos=1000;

	private byte[] salt; //Sirve para guardar hashes seguros en lugar de contraseï¿½as en la base de datos, no estï¿½ implementado
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "usuario", cascade = CascadeType.ALL)
>>>>>>> 0df91bd (gacha terminado sin front2)
	private List<UsuarioPokemon> pokemons;
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	private List<UsuarioObjeto> objetos;
	
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public Boolean getEsAdmin() {
		return esAdmin;
	}
	public void setEsAdmin(Boolean esAdmin) {
		this.esAdmin = esAdmin;
	}
	public byte[] getSalt() {
		return salt;
	}
	public void setSalt(byte[] salt) {
		this.salt = salt;
	}
	public List<UsuarioPokemon> getPokemons() {
		return pokemons;
	}
	public List<UsuarioObjeto> getObjetos() {
		return objetos;
	}
	public void setObjetos(List<UsuarioObjeto> objetos) {
		this.objetos = objetos;
	}
	public void setPokemons(List<UsuarioPokemon> pokemons) {
		this.pokemons = pokemons;
	}
	public Integer getPuntos() {
		return puntos;
	}
	public void setPuntos(Integer puntos) {
		this.puntos = puntos;
	}
	public Boolean getPrincipiante() {
		return principiante;
	}
	public void setPrincipiante(Boolean principiante) {
		this.principiante = principiante;
	}
	
}
