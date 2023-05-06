package cosas;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("servicioAtaque")
@Transactional
public class ServicioAtaqueImpl implements ServicioAtaque {
	
	private RepositorioAtaque repositorioAtaque;

    @Autowired
    public ServicioAtaqueImpl(RepositorioAtaque repositorioAtaque){
        this.repositorioAtaque = repositorioAtaque;
    }

	
	
}
