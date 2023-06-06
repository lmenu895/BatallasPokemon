package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.exceptions.ExcesoDeObjetosException;
import ar.edu.unlam.tallerweb1.exceptions.PokemonsInsuficientesException;

@Service("servicioBatalla")
@Transactional
public class ServicioBatallaImpl implements ServicioBatalla {

	private ServicioUsuario servicioUsuario;

	@Autowired
	public ServicioBatallaImpl(ServicioUsuario servicioUsuario) {
		this.servicioUsuario = servicioUsuario;
	}

	@Override
	public void inicioBatalla(List<Long> pokemonsLista, String[] objetosLista)
			throws PokemonsInsuficientesException, ExcesoDeObjetosException {
		if (pokemonsLista == null || pokemonsLista.size() != 3) {
			throw new PokemonsInsuficientesException("Debe seleccionar 3 pokémons");
		} else if (objetosLista != null && objetosLista.length > 3) {
			throw new ExcesoDeObjetosException("Solo puede seleccionar un máximo de 3 objetos");
		}
	}

	@Override
	public void finalBatalla(String ganador, Long idUsuario) {
		if (ganador.equals("user")) {
			this.servicioUsuario.sumarPuntos(idUsuario, 10);
		} else {
			this.servicioUsuario.sumarPuntos(idUsuario, 3);
		}
	}
}
