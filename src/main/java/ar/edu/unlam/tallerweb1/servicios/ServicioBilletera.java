package ar.edu.unlam.tallerweb1.servicios;

import ar.edu.unlam.tallerweb1.modelo.Billetera;
import ar.edu.unlam.tallerweb1.modelo.Plan;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

public interface ServicioBilletera {

	void registrarBilletera(Billetera billetera);

	Billetera buscarBilleteraPorId(Long id);
	
	Billetera consultarBilleteraDeUsuario(Usuario usuario);
	
	Double consultarSaldo(Billetera saldo);
	
	void ingresarSaldo(Billetera billetera, Double monto);

	void pagarPlan(Plan plan, Billetera billetera);
	
	
}
