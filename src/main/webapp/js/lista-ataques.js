$(document).ready(function() { //Funcion principal de jquery

	$(".borrar").click(function() { //Que pasa cuando le doy click a un objeto en la clase borrar
		if (confirm("Estas seguro de borrar el ataque?")) { //confirm -> tas seguro?
			var id = $(this).val();
			$.ajax({
				data: {id: id}, //info al controlador
				type: 'POST',	
				url: "eliminar-ataque",
				async: true,
				//contentType: false,
				//processData: false,
				/*beforeSend: function() {
				}, */
				success: function(resultado) { //Cuando termina de ejecutar, recibe el return del controller
					if(resultado){
						$("#ataque" + id).remove(); //Id + value del boton
					}
				}
				/* error: function(error) {
				} */
			});
		}
	});
	
	$(".modificar").click(function(){
		
		window.location.href="./modificar-ataque?id=" + $(this).val();
		
	});
		
	
	
	

});