$(document).ready(function() {

	document.title = "Datos de Usuario";
	$(document).on('click', '#imgPerfil', function() {
		$('#fotoPerfil').click();
	});
	$(document).on('change', '#fotoPerfil', function() {
		var formData = new FormData($('.cambiar-foto-perfil')[0]);
		$.ajax({
			url: 'cambiar-foto-perfil',
			type: 'POST',
			data: formData,
			contentType: false,
			processData: false,
			success: (result) => {
				if (result) {
					var url = URL.createObjectURL(this.files[0])
					$('#imgPerfil').prop('src', url);
				} else {
					alert('Error al cambiar la foto de perfil');
				}
			}
			/*error: function(err){
			}*/
		});
	});
	
	/*
	$('.boxCentro').css({
		filter: 'grayscale(1)',
		opacity: 0.7,
		'pointer-events': 'none',
		'user-select': 'none'
	});
	document.querySelector('.boxCentro').style = `
		filter: grayscale(1);
		opacity: 0.7;
		pointer-events: none;
		user-select: none;
	`;
	*/
	$(".boxCentro *").prop('disabled', true)
});
