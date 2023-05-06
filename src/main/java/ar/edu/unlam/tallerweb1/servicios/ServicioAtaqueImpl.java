package ar.edu.unlam.tallerweb1.servicios;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unlam.tallerweb1.repositorios.RepositorioAtaque;

@Service("servicioAtaque")
@Transactional
public class ServicioAtaqueImpl implements ServicioAtaque {
	
	private RepositorioAtaque repositorioAtaque;

    @Autowired
    public ServicioAtaqueImpl(RepositorioAtaque repositorioAtaque){
        this.repositorioAtaque = repositorioAtaque;
    }

	
	
}
