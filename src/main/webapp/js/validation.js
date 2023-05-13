$(document).ready(function() {

	$("form").submit(function(e) {

		var checked = 0;

		if ($("#nombre").val() == "") {
			$(".valida-nombre-vacio").show();
			e.preventDefault(e);
		}
		else
			$(".valida-nombre-vacio").hide();

		if ($("#tipo").val() == "default") {
			$(".valida-select").show();
			e.preventDefault(e);
		}
		else
			$(".valida-select").hide();

		if ($("#vida").val() == "" || !$.isNumeric($("#vida").val())) {
			$(".valida-vida-vacia").show();
			e.preventDefault(e);
		}
		else
			$(".valida-vida-vacia").hide();
		if ($("#velocidad").val() == "" || !$.isNumeric($("#vida").val())) {
			$(".valida-velocidad-vacia").show();
			e.preventDefault(e);
		}
		else
			$(".valida-velocidad-vacia").hide();

		$('.ataques').each((i, obj) => {
			if ($(obj).is(':checked'))
				checked++;
		})
		if (checked < 4) {
			$(".valida-ataques").html("Seleccione mínimo 4 ataques");
			if (checked > 0) {
				$(".valida-ataques").html("Seleccione " + (4 - checked) + " ataques más")
			}
			$(".valida-ataques").show();
			e.preventDefault(e);
		}
		else
			$(".valida-ataques").hide();
		if ($("#frente")[0].files.length == 0 && $("#verFrente").html() == "") {
			$(".valida-sprite-frente").show();
			e.preventDefault(e);
		}
		else {
			$(".valida-sprite-frente").hide();
		}
		if ($("#dorso")[0].files.length == 0 && $("#verDorso").html() == "") {
			$(".valida-sprite-dorso").show();
			e.preventDefault(e);
		}
		else {
			$(".valida-sprite-frente").hide();
		}
	});

	$("#frente").change(function() {
		var url = URL.createObjectURL(this.files[0]);
		$(".valida-sprite-frente").hide();
		if ($("#_frente").lenght)
			$("#_frente").src = url;
		else
			$("#verFrente").html("<img id='_frente' alt='frente' src=" + url + " class='img-fluid mt-1 sprite'>");
		$("#_frente").on("load", function() {
			var width = $(this).prop("width") * 2;
			$(this).prop("width", width);
		});
	});
	$("#dorso").change(function() {
		var url = URL.createObjectURL(this.files[0]);
		$(".valida-sprite-dorso").hide();
		if ($("#_dorso").lenght)
			$("#_dorso").src = url;
		else
			$("#verDorso").html("<img id='_dorso' alt='dorso' src=" + url + " class='img-fluid mt-1 sprite'>");
		$("#_dorso").on("load", function() {
			var width = $(this).prop("width") * 2;
			$(this).prop("width", width);
		});
	});

	$(window).on("load", () => {
		var width = $("#_frente").prop("width") * 2;
		$("#_frente").prop("width", width);
		var width = $("#_dorso").prop("width") * 2;
		$("#_dorso").prop("width", width);
	});
});