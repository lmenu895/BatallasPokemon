package ar.edu.unlam.tallerweb1.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.repositorios.RepositorioUsuario;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioUsuarioAtaquePokemon;

@Service("servicioAgregarAtaquesImpl")
@Transactional
public class ServicioAgregarAtaquesImpl implements ServicioAgregarAtaques {

	@Autowired
	private RepositorioUsuarioAtaquePokemon repositorioUsuarioAtaquePokemon;
	@Autowired
	private RepositorioUsuario repositorioUsuario;
	@Autowired
	private ServicioAtaquePokemon servicioAtaquePokemon;

	@Override
	public void agregar(Long idUsuario) {
		/*List<AtaquePokemon> apList = this.servicioAtaquePokemon.obtenerTodos();
		for (AtaquePokemon ap : apList) {
			this.repositorioUsuarioAtaquePokemon.guardar(new UsuarioAtaquePokemon().withAtaque(ap.getAtaque())
					.withPokemon(ap.getPokemon()).withUsuario(this.repositorioUsuario.buscar(idUsuario))
					.withBloqueado(ap.getBloqueado()).withActivo(ap.getBloqueado() ? false : true));
		}*/
	}

}
