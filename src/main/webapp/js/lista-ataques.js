import Buscador from './buscador.js';

$(document).ready(function() { //Funcion principal de jquery

	$(document).on('click', '.borrar', function() { //Que pasa cuando le doy click a un objeto en la clase borrar
		if (confirm("¿Estás seguro de borrar el ataque?")) { //confirm -> tas seguro?
			var id = $(this).val();
			$.ajax({
				data: { id: id }, //info al controlador
				type: 'POST',
				url: "eliminar-ataque",
				async: true,
				//contentType: false,
				//processData: false,
				/*beforeSend: function() {
				}, */
				success: function(resultado) { //Cuando termina de ejecutar, recibe el return del controller
					if (resultado) {
						var borrar = `#ataque${id}`;
						$(borrar).remove(); //Id + value del boton
						buscador.elementosBusqueda = buscador.elementosBusqueda.not(borrar);
					}
				}
				/* error: function(error) {
				} */
			});
		}
	});

	$(document).on('click', '.modificar', function() {
		window.location.href = "modificar-ataque?id=" + $(this).val();
	});

	$(document).on('click', '.boton-nuevo', function() {
		window.location.href = "crear-ataque";
	});

	var buscador = new Buscador('.buscar', '.clear', '.trbody', 'tbody', 'vBuscado');
	$(document).on('keyup', '.buscar', function() {
		buscador.buscar(this.value);
	});

	$(document).on('click', '.clear', function() {
		buscador.limpiarBuscar(true);
	});
	$(document).keyup(e => {
		if (e.key === 'Escape' && $('.buscar').is(':focus') && $('.buscar').val() !== '') buscador.limpiarBuscar(true);
	});

	var ascendente = false;
	$(document).on('click', '.thtitle-nombre', () => {
		ordenar('#nombre');
	});
	$(document).on('click', '.thtitle-tipo', () => {
		ordenar('#tipo');
	});

	const ordenar = value => {
		var elementos = $('.trbody');
		$('tbody').html('');
		if (!ascendente) {
			ascendente = true;
			elementos.sort((a, b) => { return $(a).children(value).html().toLowerCase() > $(b).children(value).html().toLowerCase() })
				.appendTo('tbody');
		} else {
			ascendente = false;
			elementos.sort((a, b) => { return $(a).children(value).html().toLowerCase() < $(b).children(value).html().toLowerCase() })
				.appendTo('tbody');
		}
	};

	$(window).on('load', () => {
		if ($('.buscar').val() !== '') buscador.buscar($('.buscar').val());
	});
});