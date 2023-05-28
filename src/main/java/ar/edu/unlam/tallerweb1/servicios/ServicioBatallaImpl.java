package ar.edu.unlam.tallerweb1.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("servicioBatalla")
@Transactional
public class ServicioBatallaImpl implements ServicioBatalla {

	private ServicioUsuario servicioUsuario;

	@Autowired
	public ServicioBatallaImpl(ServicioUsuario servicioUsuario) {
		this.servicioUsuario = servicioUsuario;
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
