$(document).ready(function() {
	
	var page = $('#error').length || $('#success').length ? 'datos-de-usuario' : null;
	history.replaceState($('.activo').prop('id'), '', page);
	var loading = `<div class='cargando'><img alt='cargando' src='${root}images/loading.gif'><h2>Cargando...</h2></div>`;

	$(document).on('click', '#datosUsuario', function() {
		if (!$(this).hasClass('activo')) {
			changePageState('datos-de-usuario', this.id);
			aplicarActivo(this);
		}
	});
	$(document).on('click', '#historialBatallas', function() {
		if (!$(this).hasClass('activo')) {
			changePageState('historial-de-batallas', this.id);
			aplicarActivo(this);
		}
	});
	$(document).on('click', '#listaPokemons', function() {
		if (!$(this).hasClass('activo')) {
			changePageState('lista-pokemons-usuario', this.id);
			aplicarActivo(this);
		}
	});

	$(document).on('click', '.detalles', function() {
		$('.activo').removeClass('activo');
		changePageState(`pokemon-usuario/${this.value}`, this.id);
	});

	const aplicarActivo = element => {
		$('.activo').removeClass('activo');
		$(element).addClass('activo');
	};

	$(window).bind('popstate', e => {
		var { pathname } = window.location;
		$('.contenido').load(`${pathname}?ajaxRequest=true`);
		$('.activo').removeClass('activo');
		$(`#${e.originalEvent.state}`).addClass('activo');
	});

	const changePageState = (partial, name) => {
		$('.contenido').html(loading);
		$('.contenido').load(`${root}${partial}?ajaxRequest=true`, () => {
			history.pushState(name, '', root + partial);
		});
	};
});