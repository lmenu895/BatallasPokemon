package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unlam.tallerweb1.modelo.Plan;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioPlan;

@Service("servicioPlan")
@Transactional
public class ServicioPlanImpl implements ServicioPlan {

	private RepositorioPlan repositorioPlan;

	@Autowired
	public ServicioPlanImpl(RepositorioPlan repositorioPlan) {
		this.repositorioPlan = repositorioPlan;
	}

	@Override
	public void darDeBajaPlan(Long id) {
		repositorioPlan.darDeBajaPlan(id);
	}

	public List<Plan> obtenerPlanes() {
		return repositorioPlan.listaDePlanes();
	}

	@Override
	public Plan consultarPlan(Long id) {
		return repositorioPlan.consultarPlan(id);

	}

}
