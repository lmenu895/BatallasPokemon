var loadedScripts = {};

$(document).ready(function() {
	
	var page = $('#error').length || $('#success').length ? 'datos-de-usuario' : null;
	history.replaceState($('.activo').prop('id'), '', page);
	var loading = `<div class='cargando'><img alt='cargando' src='${root}images/loading.gif'><h2>Cargando...</h2></div>`;

	$(document).on('click', '#datosUsuario', function(e) {
		e.preventDefault();
		if (!$(this).hasClass('activo')) {
			changePageState('datos-de-usuario', this.id);
			aplicarActivo(this);
		}
	});
	$(document).on('click', '#historialBatallas', function(e) {
		e.preventDefault();
		if (!$(this).hasClass('activo')) {
			changePageState('historial-de-batallas', this.id);
			aplicarActivo(this);
		}
	});
	$(document).on('click', '#listaPokemons', function(e) {
		e.preventDefault();
		if (!$(this).hasClass('activo')) {
			changePageState('lista-pokemons-usuario', this.id);
			aplicarActivo(this);
		}
	});

	$(document).on('click', '.detalles', function(e) {
		e.preventDefault();
		$('.activo').removeClass('activo');
		changePageState(`pokemon-usuario/${this.value}`, this.id);
	});

	const aplicarActivo = element => {
		$('.activo').removeClass('activo');
		$(element).addClass('activo');
	};

	$(window).on('popstate', e => {
		var { pathname } = window.location;
		$('.contenido').load(`${pathname}?ajaxRequest=true`, () => {
			e.originalEvent.state === 'detalles' && resizeSprite();
		});
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