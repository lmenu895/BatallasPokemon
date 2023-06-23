$(document).ready(function() {
	
	console.log(loadedScripts)
	
	if(loadedScripts.datosUsuario) return;
	else loadedScripts['datosUsuario'] = true;

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
});
