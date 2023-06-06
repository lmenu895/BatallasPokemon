$(document).ready(function() {

	$(".borrar").click(function() {
		if (confirm("¿Estás seguro de borrar el pokemon?")) {
			var idPokemon = this.value;
			$.ajax({
				data: { id: idPokemon },
				type: 'POST',
				url: "borrar-pokemon",
				async: true,
				//contentType: false,
				//processData: false,
				/*/beforeSend: function() {
				},*/
				success: (result) => {
					$('#pokemon' + idPokemon).remove();
					console.log("Pokemon borrado");
				}
				/*error: function(err){
				}*/
			});
		}
	});

	var buscador = new Buscador('.buscar', '.clear', '.trbody', 'tbody', 'vBuscado');
	var regexBuscarRareza = { regex: '\/r ', clase: '.vRegexRareza' };
	$(document).on('keyup', '.buscar', function() {
		buscador.buscar(this.value, regexBuscarRareza);
	});
	$(document).on('click', '.clear', function() {
		buscador.limpiarBuscar(true);
	});
	$(document).keyup(e => {
		if (e.key === 'Escape' && $('.buscar').is(':focus') && $('.buscar').val() != '') buscador.limpiarBuscar(true);
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
		if (!ascendente) {
			ascendente = true;
			elementos.sort((a, b) => { return $(a).children(value).html().toLowerCase() > $(b).children(value).html().toLowerCase() });
		} else {
			ascendente = false;
			elementos.sort((a, b) => { return $(a).children(value).html().toLowerCase() < $(b).children(value).html().toLowerCase() });
		}
		$('tbody').html(elementos);
	};

	$(window).on('load', () => {
		if ($('.buscar').val() !== '') buscador.buscar($('.buscar').val(), regexBuscarRareza);
	});
});