package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;

import ar.edu.unlam.tallerweb1.exceptions.LimiteDeAtaquesException;
import ar.edu.unlam.tallerweb1.exceptions.PuntosInsuficientesException;
import ar.edu.unlam.tallerweb1.modelo.Ataque;
import ar.edu.unlam.tallerweb1.modelo.AtaquePokemon;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.UsuarioAtaquePokemon;

public interface ServicioUsuarioAtaquePokemon {

	List<UsuarioAtaquePokemon> obtenerLista(Long idUsuario, Long idPokemon);

	void guardar(AtaquePokemon ataquePokemon, Usuario usuario);

	List<Ataque> obtenerListaDeActivos(Long idPokemon, Long idUsuario);

	void desbloquear(Long idUAP) throws PuntosInsuficientesException;

	void activarDesactivar(Long idUAP, Long idUsuario, Long idPokemon, String accion) throws LimiteDeAtaquesException;

	void guardar(UsuarioAtaquePokemon uap);
}
