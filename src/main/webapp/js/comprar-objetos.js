$(document).ready(() => {
	
	var oldValues = {};
	$('.puntos-gastados').each(function() {
		oldValues[this.id] = 0;
	});

	$(document).on('change', '.puntos-gastados', function() {
		var resultado;
		$('#total').css('visibility', 'visible');
		var total = parseInt($('#total').html());
		var precio = parseInt($(`.precio-${this.id}`).html());
		if (this.value > oldValues[this.id]) {
			resultado = total + precio;
			oldValues[this.id] = this.value;
		} else {
			resultado = total - precio;
			oldValues[this.id] = this.value;
		}
		$('#total').html(resultado);
	});
});