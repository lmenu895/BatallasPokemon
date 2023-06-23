package ar.edu.unlam.tallerweb1.repositorios;

import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.Objeto;
import ar.edu.unlam.tallerweb1.modelo.UsuarioObjeto;

public interface RepositorioUsuarioObjeto {

	List<UsuarioObjeto> buscarObjetos(Long idUsuario);

	void guardar(UsuarioObjeto usuarioObjeto);

	UsuarioObjeto buscar(Long idObjeto, Long idUsuario);

}
