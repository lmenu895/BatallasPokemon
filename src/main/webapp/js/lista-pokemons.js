$(document).ready(function() {

	$(".modificar").click(function() {
		window.location.href = "modificar-pokemon?id=" + this.value;
	});
	
	$(".boton-nuevo").click(function(){
		window.location.href = "crear-pokemon";
	});

	$(".borrar").click(function() {
		if (confirm("¿Estás seguro de borrar el pokemon?")) {
			var idPokemon = this.value;
			$.ajax({
				type: 'GET',
				url: "borrar-pokemon?id=" + idPokemon,
				async: true,
				//contentType: false,
				//processData: false,
				/*/beforeSend: function() {
				},*/
				success: function(resultado) {
					$("#pokemon" + idPokemon).remove();
					console.log("Pokemon borrado");
				}
				/*error: function(error){
				}*/
			});
		}
	});

});