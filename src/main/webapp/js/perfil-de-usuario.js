$(document).ready(function() {
	
	var firstState = {
		name: $('.activo').prop('id'),
		content: $('.contenido').html()
	};
	history.replaceState(firstState, '');
	var loading = `<div class='cargando'><img alt='cargando' src='${root}images/loading.gif'><h2>Cargando...</h2></div>`;

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

	$(document).on('click', '.detalles', function() {
		$('.activo').removeClass('activo');
		changePageState(`pokemon-usuario/${this.value}`, $(this).prop('id'));
	});

	const aplicarActivo = element => {
		$('.activo').removeClass('activo');
		$(element).addClass('activo');
	};

	$(window).bind('popstate', e => {
		$('.contenido').html(e.originalEvent.state.content);
		$('.activo').removeClass('activo');
		$(`#${e.originalEvent.state.name}`).addClass('activo');
	});

	const changePageState = (partial, name) => {
		$('.contenido').html(loading);
		$('.contenido').load(`${root}${partial}?ajaxRequest=true`, function() {
			var state = {
				name: name,
				content: $(this).html()
			};
			history.pushState(state, '', root + partial);
		});
	};
});