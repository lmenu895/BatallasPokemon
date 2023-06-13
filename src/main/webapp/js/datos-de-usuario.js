$(document).ready(function() {

	$('#imgPerfil').click(function() {
		$('#file').click();
	})
	$('#file').change(function() {
		var url = URL.createObjectURL(this.files[0])
		$('#imgPerfil').prop('src', url);

	})
	
	$(document).on('click', '.cambiar-mail', () => {
		$('.cambiar-mail-dialog')[0].showModal();
	});


})
