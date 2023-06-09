$(document).ready(function() {
	
	$(window).on('load', () => {
		var width = $('.pokemon').prop('width') * 2.5;
		$('.pokemon').prop('width', width);
		console.log('hoa')
	});
});

