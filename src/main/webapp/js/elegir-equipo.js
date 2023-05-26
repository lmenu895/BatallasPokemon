$(document).ready(function() {
	
	var prioridad = 0;

	$('.botonPokemon').click(function() {
		var next = $(this).next();
		if ($(this).html() === 'Seleccionar') {
			$(this).html('Quitar');
			next.prop('disabled', false);
			next.val(prioridad++ + 'p' + next.val());
		}
		else {
			$(this).html('Seleccionar');
			next.prop('disabled', true);
			next.val(next.val().substring(next.val().indexOf('p') + 1));
		}
	});

	$('.botonPokemon').each(function() {
		if (!$(this).next().prop('disabled')) {
			$(this).html('Quitar');
			prioridad++;
		}
	});
	
	$('.botonObjeto').click(function() {
		var next = $(this).next();
		if ($(this).html() === 'Seleccionar') {
			$(this).html('Quitar');
			next.prop('disabled', false);
		}
		else {
			$(this).html('Seleccionar');
			next.prop('disabled', true);
		}
	});
	
	$('.botonObjeto').each(function() {
		if (!$(this).next().prop('disabled')) {
			$(this).html('Quitar');
		}
	});
	
	if ($('#error').length){
        alert($('#error').html());
    }

	//Seteo el tamaño de los sprites
	$(window).on('load', () => {
		var width;
		$('.imgPokemon').each(function() {
			width = $(this).prop('width') * 2;
			$(this).prop('width', width);
		});
	});
});