$(document).ready(() => {

	var gastoPorObjeto = {}

	$(document).on('input', '.puntos-gastados', function() {
		var gasto = parseInt($(this).siblings('div').children('.precio').html()) * this.value;
		$('#total').html(parseInt($('#total').html()) + gasto - (gastoPorObjeto[this.id] ?? 0));
		gastoPorObjeto[this.id] = gasto;
	});
	
});