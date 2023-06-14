$(document).ready(function() {

	var primerEstado = {
		name: $('.activo').prop('id'),
		content: $('.contenido').html()
	};
	history.replaceState(primerEstado, '');
	var loading = "<div class='cargando'><img alt='cargando' src='images/loading.gif'><h2>Cargando...</h2></div>"

	$(document).on('click', '#datosUsuario', function() {
		if (!$(this).hasClass('activo')) {
			changePageState('datos-de-usuario', $(this).prop('id'));
			aplicarActivo(this);
		}
	});
	$(document).on('click', '#historialBatallas', function() {
		if (!$(this).hasClass('activo')) {
			changePageState('historial-de-batallas', $(this).prop('id'));
			aplicarActivo(this);
		}
	});
	$(document).on('click', '#listaPokemons', function() {
		if (!$(this).hasClass('activo')) {
			changePageState('lista-pokemons-usuario', $(this).prop('id'));
			aplicarActivo(this);
		}
	});

	const aplicarActivo = element => {
		$('.activo').removeClass('activo');
		$(element).addClass('activo');
	};

	$(window).bind("popstate", e => {
		$(".contenido").html(e.originalEvent.state.content);
		$('.activo').removeClass('activo');
		$('#' + e.originalEvent.state.name).addClass('activo');
	});

	const changePageState = async (partial, name) => {
		$('.contenido').html(loading);
		if (primerEstado.name !== name) {
			$('.contenido').load(partial, function() {
				var state = {
					name: name,
					content: $(this).html()
				};
				history.pushState(state, '', partial);
			});
		} else {
			$('.contenido').html(primerEstado.content);
			history.pushState(primerEstado, '', partial);
		}
	};
});