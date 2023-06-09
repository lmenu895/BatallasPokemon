$(document).ready(function() {
	
	$(window).on('load', () => {
		var width = $('.pokemon').prop('width') * 2.5;
		$('.pokemon').prop('width', width);
	});
<<<<<<< HEAD
<<<<<<< HEAD
});
=======
	
=======
>>>>>>> 9a5119065880cdacee599089b135452dfa045187
	var img = $('.pokeballArriba').attr('src');
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
	}
<<<<<<< HEAD
	
=======
>>>>>>> 9a5119065880cdacee599089b135452dfa045187
});
>>>>>>> f2f3795 (pitty y repetidos sin front)
