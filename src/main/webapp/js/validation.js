$(document).ready(function() {

	$("form").submit(function(e) {

		var checked = 0

		if ($("#nombre").val() == "") {
			$(".valida-nombre-vacio").show()
			e.preventDefault(e)
		}
		else
			$(".valida-nombre-vacio").hide()

		if ($("#tipo").val() == "default") {
			$(".valida-select").show()
			e.preventDefault(e)
		}
		else
			$(".valida-select").hide()

		if ($("#vida").val() == "" || !$.isNumeric($("#vida").val())) {
			$(".valida-vida-vacia").show()
			e.preventDefault(e)
		}
		else
			$(".valida-vida-vacia").hide()

		$('.ataques').each((i, obj) => {
			if ($(obj).is(':checked'))
				checked++
		})
		if (checked < 4) {
			$(".valida-ataques").html("Seleccione m\u00EDnimo 4 ataques")
			if (checked > 0) {
				$(".valida-ataques").html("Seleccione " + (4 - checked) + " ataques m\u00E1s")
			}
			$(".valida-ataques").show()
			e.preventDefault(e)
		}
		else
			$(".valida-ataques").hide()
	})

	$("#frente").change(function() {
		var formData = new FormData();
		formData.append('imagen', this.files[0])
		console.log(formData)

		$.ajax({
			data: formData,
			type: 'POST',
			url: "procesar-imagen",
			processData: false,
			contentType: false,
			success: function(resultado) {
				console.log(resultado);
			}
		})
	})

})