$(document).ready(function() {

	$(document).on('click', '.botonPokemon', function() {
		var value = this.value;
		if ($(this).html() === 'Seleccionar') {
			$(this).html('Quitar');
			appendInputPokemonsLista(value);
		} else {
			$(this).html('Seleccionar');
			$('.input-pokemon').each(function() {
				if (this.value === value) $(this).remove();
			});
		}
	});

	var buscador = new Buscador('.buscar', '.clear', '.pokemon-usuario', '.pokemons', 'vBuscado');

	$(document).on('keyup', '.buscar', function() {
		buscador.buscar(this.value);
	});
	$(document).on('click', '.clear', function() {
		buscador.limpiarBuscar(true);
	});
	$(document).keyup(e => {
		if (e.key === 'Escape' && $('.buscar').is(':focus') && $('.buscar').val() !== '') buscador.limpiarBuscar(true);
	});

	const appendInputPokemonsLista = (value) => {
		$('.pokemons-seleccionados').append('<input type="hidden" class="input-pokemon" name="pokemonsLista" value="' + value + '" />');
	};

	/*var prioridad = 0;

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
	});*/

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

	if ($('#error').length) {
		alert($('#error').html());
	}

	//Seteo el tamaño de los sprites
	$(window).on('load', () => {
		var width;
		$('.imgPokemon').each(function() {
			width = $(this).prop('width') * 2;
			$(this).prop('width', width);
		});
		$('.imgPokemon').show();
		if ($('.buscar').val() !== '') buscador.buscar($('.buscar').val());
	});
});