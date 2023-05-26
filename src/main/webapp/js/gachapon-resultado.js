$(document).ready(function () {
	
	var img = $('.pokeballArriba').attr('src');
  	if(img == "images/pokeballArriba100.png"){
	  $(".pokeballAbajo").css('bottom', '40.2 %')
  	}
  	else if(img == "images/pokeballArriba500.png"){
	  $(".pokeballAbajo").css('bottom', '40.25 %')
  	}
  	else if(img == "images/pokeballArriba1000.png"){
	  $(".pokeballAbajo").css('bottom', '40.5%')
  	}
  	else if(img == "images/pokeballArriba10000.png"){
	  $(".pokeballAbajo").css('bottom', '40.4%')
  	}
});
