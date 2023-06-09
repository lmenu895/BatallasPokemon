$(document).ready(function() {

	console.log("d");

	$('#imgPerfil').click(function() {
		$('#file').click();
	})
	$('#file').change(function() {
		var url = URL.createObjectURL(this.files[0])
		$('#imgPerfil').prop('src', url);

	})


})