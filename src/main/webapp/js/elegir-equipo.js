$(document).ready(function() {

	$(".botonPokemon").click(function() {
		if ($(this).html() == "Seleccionar") {
			$(this).html("Quitar");
			$(this).next().prop("disabled", false);
		}
		else {
			$(this).html("Seleccionar");
			$(this).next().prop("disabled", true);
		}
	});

	//Seteo el tamaño de los sprites
	$(window).on("load", () => {
		var width;
		$(".imgPokemon").each(function() {
			width = $(this).prop("width") * 2;
			$(this).prop("width", width);
			console.log($(this).prop("width"));
		});
	});
});