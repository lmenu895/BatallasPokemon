package ar.edu.unlam.tallerweb1.servicios;

import java.time.LocalDate;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;

import ar.edu.unlam.tallerweb1.exceptions.PagoExistenteException;
import ar.edu.unlam.tallerweb1.exceptions.PagoNoAprobadoException;
import ar.edu.unlam.tallerweb1.modelo.Pago;
import ar.edu.unlam.tallerweb1.modelo.UsuarioPlan;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioPago;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioUsuario;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioUsuarioPlan;

@Service
@Transactional
public class ServicioPagoImpl implements ServicioPago {

	private RepositorioPago repositorioPago;
	private RepositorioUsuario repositorioUsuario;
	private RepositorioUsuarioPlan repositorioUsuarioPlan;

	@Autowired
	public ServicioPagoImpl(RepositorioPago repositorioPago, RepositorioUsuario repositorioUsuario,
			RepositorioUsuarioPlan repositorioUsuarioPlan) {
		this.repositorioPago = repositorioPago;
		this.repositorioUsuario = repositorioUsuario;
		this.repositorioUsuarioPlan = repositorioUsuarioPlan;
	}

	@Override
	public Payment verificarPago(Long payment_id)
			throws MPException, MPApiException, PagoExistenteException, PagoNoAprobadoException {
		PaymentClient client = new PaymentClient();
		Payment payment = client.get(payment_id);
		if (this.repositorioPago.buscar(payment.getId(), payment.getDateApproved().toLocalDateTime()) != null) {
			throw new PagoExistenteException("Ya fue asignado un plan con este pago");
		}
		if (!payment.getStatus().equals("approved")) {
			throw new PagoNoAprobadoException("El pago no fue aprobado");
		}
		return payment;
	}

	@Override
	public void guardar(Payment payment, Long idUsuario) {
		Pago pago = new Pago().withFechaAprobado(payment.getDateApproved().toLocalDateTime())
				.withIdPago(payment.getId()).withUsuario(this.repositorioUsuario.buscar(idUsuario));
		this.repositorioPago.guardar(pago);
	}

	@Override
	public Double calcularPrecioMejoraPremium(Double precioOriginal, Long idUsuario) {
		LocalDate fecha = this.repositorioUsuarioPlan.buscarPorUsuario(idUsuario).getVencimiento().minusWeeks(1);
		Double semanas = 4.0;
		Double descuento = 0.3;
		do {
			semanas--;
			fecha = fecha.minusWeeks(1);
		} while (fecha.isAfter(LocalDate.now()));
		return precioOriginal * (descuento + (semanas * 0.2));
	}
}
