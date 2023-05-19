package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.modelo.Usuario;

// Interface que define los metodos del Repositorio de Usuarios.
public interface RepositorioUsuario {
	
	Usuario buscarUsuario(String email, String password);
	void guardar(Usuario usuario);
    Usuario buscarUsuario(String email);
	void modificar(Usuario usuario);
	Usuario buscarUsuario(Long id);
	Usuario buscarUsuarioPorUsername(String userName);
}