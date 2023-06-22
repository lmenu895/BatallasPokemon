package ar.edu.unlam.tallerweb1.repositorios;

import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.UsuarioObjeto;

public interface RepositorioUsuarioObjeto {

	List<UsuarioObjeto> buscarObjeto(Long idUsuario);

	void guardar(UsuarioObjeto usuarioObjeto);

}
