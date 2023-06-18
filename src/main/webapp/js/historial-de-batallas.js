$(document).ready(function() {

	document.title = "Historial de batallas";
	$(document).on('change', '#switch', function() {
		if (this.checked) {
			$('.debilitado').css('filter', 'brightness(1)');
		} else {
			$('.debilitado').css('filter', 'brightness(0)');
		}
	});
});