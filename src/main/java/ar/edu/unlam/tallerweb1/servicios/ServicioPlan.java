package ar.edu.unlam.tallerweb1.servicios;


import java.util.List;
import ar.edu.unlam.tallerweb1.modelo.Plan;
import ar.edu.unlam.tallerweb1.modelo.Usuario;

public interface ServicioPlan {


	public void darDeBajaPlan(Long id);

	public List<Plan> obtenerPlanes();

	public Plan consultarPlan(Long id);
	
	


}