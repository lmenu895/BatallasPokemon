package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.Objeto;
import ar.edu.unlam.tallerweb1.modelo.Pokemon;
import ar.edu.unlam.tallerweb1.modelo.RarezaPokemon;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.UsuarioObjeto;

public interface ServicioUsuario {

	void guardarEquipo(String[] pokemons,Long id);

	Usuario buscarUsuario(Long idUsuario);
	
	public List<Pokemon> obtenerListaDePokemons(Long idUsuario);

	Boolean restarPuntos(Integer monedas, Usuario usuario);

	void sumarPuntos(Long idUsuario, Integer puntos);

	Integer sumarpokeMonedas(RarezaPokemon rarezaPokemon, Usuario usuario);
	
	void sumarTiradasComunes(Usuario usuario);
	
	void sumarTiradasTotales(Usuario usuario);

	void reiniciarTiradasComunes(Usuario usuario);
	
	void reiniciarTiradasTotales(Usuario usuario);

	void sacarPrincipiante(Usuario usuario);
}