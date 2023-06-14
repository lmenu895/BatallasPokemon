$(document).ready(function() {

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

	/*$(".boxCentro").css('filter', 'grayscale(1)')
	$(".boxCentro").css('opacity', '0.7')
	//$(".boxCentro").css('pointer-events', 'none')
	$(".boxCentro").css('user-select', 'none')
	$(".boxCentro *").prop('disabled', true)*/
});
