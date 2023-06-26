package ar.edu.unlam.tallerweb1.servicios;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unlam.tallerweb1.modelo.Billetera;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioBilletera;

@Service
@Transactional
public class ServicioBilleteraImpl implements ServicioBilletera {

	private RepositorioBilletera repositorioBilletera;
	

	@Autowired
	public ServicioBilleteraImpl(RepositorioBilletera repositorioBilletera) {

		this.repositorioBilletera = repositorioBilletera;
	}

	@Override
	public Billetera buscarBilleteraPorId(Long id) {

		return repositorioBilletera.buscarBilleteraPorId(id);
	}

	@Override
	public Double consultarSaldo(Billetera saldo) {

		return repositorioBilletera.consultarSaldo(saldo);
	}

	@Override
	public void ingresarSaldo(Billetera billetera, Double monto) {

		repositorioBilletera.ingresarSaldo(billetera, monto);

	}

	@Override
	public void registrarBilletera(Billetera billetera) {
		if (repositorioBilletera.consultarBilleteraDeUsuario(billetera.getUsuario().getId()) == null)
			repositorioBilletera.registrarBilletera(billetera);
	}

	@Override
	public Billetera consultarBilleteraDeUsuario(Long idUsuario) {
		return repositorioBilletera.consultarBilleteraDeUsuario(idUsuario);
	}
}
