$(document).ready(function() {
	
	$(window).on('load', () => {
		var width = $('.pokemon').prop('width') * 2.5;
		$('.pokemon').prop('width', width);
		console.log('hoa')
	});
	/*var img = $('.pokeballArriba').attr('src');
	if (img == "images/pokeballArriba100.png") {
		$(".pokeballAbajo").css('bottom', '40.2%')
	}
	else if (img == "images/pokeballArriba500.png") {
		$(".pokeballAbajo").css('bottom', '40.25%')
	}
	else if (img == "images/pokeballArriba1000.png") {
		$(".pokeballAbajo").css('bottom', '40.5%')
	}
	else if (img == "images/pokeballArriba10000.png") {
		$(".pokeballAbajo").css('bottom', '40.4%')
	}*/
});
