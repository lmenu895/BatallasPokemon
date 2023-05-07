package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.modelo.Ataque;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioAtaque;

@Service("servicioAtaque")
@Transactional
public class ServicioAtaqueImpl implements ServicioAtaque {

	private RepositorioAtaque repositorioAtaque;

	@Autowired
	public ServicioAtaqueImpl(RepositorioAtaque repositorioAtaque){
		this.repositorioAtaque = repositorioAtaque;
	}
	
	@Override
	public List<Ataque> obtenerTodosLosAtaques() {
		return this.repositorioAtaque.obtenerTodosLosAtaques();
	}

	@Override
	public Ataque buscarAtaque(Long id) {
		return this.repositorioAtaque.buscarAtaque(id);
	}

}
