package ar.edu.unlam.tallerweb1.servicios;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.payment.Payment;

import ar.edu.unlam.tallerweb1.exceptions.PagoExistenteException;
import ar.edu.unlam.tallerweb1.exceptions.PagoNoAprobadoException;

public interface ServicioPago {

	Payment verificarPago(Long payment_id) throws MPException, MPApiException, PagoExistenteException, PagoNoAprobadoException;

	void guardar(Payment payment, Long idUsuario);

	Double calcularPrecioMejoraPremium(Double precioOriginal, Long idUsuario);

}
