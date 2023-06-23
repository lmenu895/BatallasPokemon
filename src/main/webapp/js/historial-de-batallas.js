$(document).ready(function() {
	
	if(loadedScripts.historial) return;
	else loadedScripts['historial'] = true;

	$(document).on('change', '#switch', function() {
		if (this.checked) {
			$('.debilitado').css('filter', 'brightness(1)');
		} else {
			$('.debilitado').css('filter', 'brightness(0)');
		}
	});
});