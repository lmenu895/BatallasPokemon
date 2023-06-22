package ar.edu.unlam.tallerweb1.servicios;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.exceptions.LimiteDeAtaquesException;
import ar.edu.unlam.tallerweb1.exceptions.PuntosInsuficientesException;
import ar.edu.unlam.tallerweb1.modelo.Ataque;
import ar.edu.unlam.tallerweb1.modelo.AtaquePokemon;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.modelo.UsuarioAtaquePokemon;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioUsuarioAtaquePokemon;

@Service("servicioUsuarioAtaquePokemon")
@Transactional
public class ServicioUsuarioAtaquePokemonImpl implements ServicioUsuarioAtaquePokemon {

	private RepositorioUsuarioAtaquePokemon repositorioUsuarioAtaquePokemon;

	@Autowired
	public ServicioUsuarioAtaquePokemonImpl(RepositorioUsuarioAtaquePokemon repositorioUsuarioAtaquePokemon) {
		this.repositorioUsuarioAtaquePokemon = repositorioUsuarioAtaquePokemon;
	}

	@Override
	public List<UsuarioAtaquePokemon> obtenerLista(Long idUsuario, Long idPokemon) {
		List<UsuarioAtaquePokemon> uapList = this.repositorioUsuarioAtaquePokemon.obtenerAtaques(idUsuario, idPokemon);
		Collections.sort(uapList, new Comparator<UsuarioAtaquePokemon>() {
			@Override
			public int compare(UsuarioAtaquePokemon bloqueado1, UsuarioAtaquePokemon bloqueado2) {
				return Boolean.compare(bloqueado1.getBloqueado(), bloqueado2.getBloqueado());
			}
		});
		return uapList;
	}

	@Override
	public void guardar(AtaquePokemon ataquePokemon, Usuario usuario) {
		this.repositorioUsuarioAtaquePokemon.guardar(new UsuarioAtaquePokemon().withUsuario(usuario)
				.withPokemon(ataquePokemon.getPokemon()).withAtaque(ataquePokemon.getAtaque())
				.withBloqueado(ataquePokemon.getBloqueado()).withActivo(ataquePokemon.getBloqueado() ? false : true));
	}

	@Override
	public List<Ataque> obtenerListaDeActivos(Long idPokemon, Long idUsuario) {
		List<Ataque> ataques = new ArrayList<>();
		this.repositorioUsuarioAtaquePokemon.obtenerListaDeAtaquesActivos(idPokemon, idUsuario)
				.forEach(x -> ataques.add(x.getAtaque()));
		return ataques;
	}

	@Override
	public void desbloquear(Long idUAP) throws PuntosInsuficientesException {
		UsuarioAtaquePokemon uap = this.repositorioUsuarioAtaquePokemon.buscar(idUAP);
		Integer puntos = uap.getUsuario().getPuntos();
		if (puntos >= 80) {
			uap.setBloqueado(false);
			uap.getUsuario().setPuntos(puntos - 80);
		} else {
			throw new PuntosInsuficientesException("No dispone de los puntos suficientes para desbloquear el ataque");
		}
	}

	@Override
	public void activarDesactivar(Long idUAP, Long idUsuario, Long idPokemon, String accion)
			throws LimiteDeAtaquesException {
		List<UsuarioAtaquePokemon> uapList = this.repositorioUsuarioAtaquePokemon.obtenerAtaques(idUsuario, idPokemon);
		UsuarioAtaquePokemon uap = uapList.stream().filter(x -> x.getId().equals(idUAP)).findFirst().get();
		uapList = uapList.stream().filter(x -> x.getActivo()).toList();

		if (accion.equals("desactivar")) {
			if (uapList.size() == 1) {
				throw new LimiteDeAtaquesException("El pokemon debe tener un ataque activo como mínimo");
			}
			if (uap != null)
				uap.setActivo(false);
		} else if (accion.equals("activar")) {
			if (uapList.size() == 4) {
				throw new LimiteDeAtaquesException("El pokemon solo puede tener 4 ataques activos a la vez");
			}
			if (uap != null)
				uap.setActivo(true);
		}
	}

	@Override
	public void guardar(UsuarioAtaquePokemon uap) {
		this.repositorioUsuarioAtaquePokemon.guardar(uap);
	}
}
