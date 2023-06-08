$(document).ready(function() {
	
	$(".tipo" ).each(function() {
  		switch($(this).text()){
			  case "VENENO":
				  $(this).css("background-color", "rgb(178, 111, 214, 0.55)");
			  break;
			  
			  case "FUEGO":
				  $(this).css("background-color", "rgb(217, 7, 0, 0.55)");
			  break;
			  
			  case "AGUA":
				  $(this).css("background-color", "rgb(120, 175, 227, 0.55)");
			  break;
			  
			  case "NORMAL":
				  $(this).css("background-color", "rgb(194, 194, 194, 0.55)");
			  break;
			  
			  case "ELECTRICO":
				  $(this).css("background-color", "rgb(240, 234, 67, 0.55)");
			  break;
			  
			  case "PLANTA":
				  $(this).css("background-color", "rgb(95, 204, 90, 0.55)");
			  break;
			  
			  case "LUCHA":
				  $(this).css("background-color", "rgb(122, 54, 33, 0.55)");
			  break;
			  
			  case "HIELO":
				  $(this).css("background-color", "rgb(110, 238, 240, 0.55)");
			  break;
			  
			  case "ACERO":
				  $(this).css("background-color", "rgb(110, 110, 110, 0.55)");
			  break;
			  
			  case "TIERRA":
				  $(this).css("background-color", "rgb(87, 52, 16, 0.55)");
			  break;
			  
			  case "DRAGON":
				  $(this).css("background-color", "rgb(56, 45, 105, 0.55)");
			  break;
			  
			  case "SINIESTRO":
				  $(this).css("background-color", "rgb(0, 0, 0, 0.85)");
				  $(this).css("color", "rgb(227, 228, 230)");
			  break;
			  
		  }
		  
	});
	
		   var imagen;
		   var clase;
		   $(".imagen-pokemon").each(function(){
					$(this).css("filter", "brightness(0%)")
				   	imagen = $(this).attr("src").split("/")[3];
				   	pokemonsUsuario.forEach(pokemon => {
					  	if(pokemon.imagenFrente == imagen){
						    $(this).css("filter", "brightness(100%)")
					    }
		 			});
	   			})
	
			$(".pnombre").each(function(){
			   	var name = $(this).text();
			   	$(this).hide()
			   	pokemonsUsuario.forEach(pokemon => {
					   	if(pokemon.nombre.toLowerCase() == name.toLowerCase()){
					    $(this).show();
					    clase = "." + pokemon.nombre;
					    $(clase).hide();
					    
				    }
		 		});  
			})
		  
	
	        $('input[type="checkbox"]').click(function(){
            if($(this).prop("checked") == true){
                $(".imagen-pokemon").each(function(){
					$(this).css("filter", "brightness(100%)")
				})
				$(".pnombre").each(function(){
					$(this).show();
				 }) 
				 $(".pquestion").each(function(){
					 $(this).hide();
				 })
            }
            
            else if($(this).prop("checked") == false){
                $(".imagen-pokemon").each(function(){
					$(this).css("filter", "brightness(0%)")
				    imagen = $(this).attr("src").split("/")[3];
				   	pokemonsUsuario.forEach(pokemon => {
					  	if(pokemon.imagenFrente == imagen){
						    $(this).css("filter", "brightness(100%)")
					    }
		 			});
	   			})
	   			$(".pquestion").each(function(){
					 $(this).show();
				 })
	   			$(".pnombre").each(function(){
					$(this).css("filter", "brightness(0%)")
				   	var name = $(this).text();
				   	$(this).hide();
				   	pokemonsUsuario.forEach(pokemon => { 
				  	if(pokemon.nombre.toLowerCase() == name.toLowerCase()){
					    $(this).show();
					    clase = "." + pokemon.nombre; 
					    $(clase).hide();   
				    }
		 		});  
			 })
				 
				 
				 
     }
   });
});
