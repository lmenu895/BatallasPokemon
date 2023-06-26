package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;
import ar.edu.unlam.tallerweb1.modelo.Plan;

public interface ServicioPlan {

	public void darDeBajaPlan(Long id);

	public List<Plan> obtenerPlanes();

	public Plan consultarPlan(Long id);

}