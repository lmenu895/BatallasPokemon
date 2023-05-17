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
				success: function(result) {
					$("#pokemon" + idPokemon).remove();
					console.log("Pokemon borrado");
				}
				/*error: function(err){
				}*/
			});
		}
	});
});