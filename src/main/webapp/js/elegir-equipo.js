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

	var listaPokemons = $('.pokemon-usuario');
	$(document).on('keyup', '.buscar', function() {
		var busqueda = [];
		var value = this.value;
		listaPokemons.each(function() {
			if ($(this).attr('id').toLowerCase().includes(value.toLowerCase())) {
				busqueda.push(this);
			}
		});
		$('.pokemons').html(busqueda);
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
		
		$('.buscar').val('');
	});
});