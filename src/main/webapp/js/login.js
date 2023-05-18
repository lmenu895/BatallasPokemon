$(document).ready(function() {

	var partialLogin;
	var partialRegistro;

	$(".container").on("click", "#registrarme", function() {
		$("#error").remove();
		if (partialLogin == null) {
			partialLogin = $(".container").html();
			history.replaceState(partialLogin, '');
		}
		if (partialRegistro == null) {
			$(".container").load("registrar-usuario", function() {
				partialRegistro = $(".container").html();
				history.pushState(partialRegistro, '', 'registrar-usuario');
			});
		}
		else {
			$(".container").html(partialRegistro);
			history.pushState(partialRegistro, '', 'registrar-usuario');
		}
	});

	$(".container").on("click", "#login", function() {
		$(".container").html(partialLogin);
		history.pushState(partialLogin, '', 'login');
	});

	$(window).bind("popstate", (e) => {
		$(".container").html(e.originalEvent.state);
	});

	//Submit del formulario de registro y redirección a login en caso de éxito
	var working = false;
	$(".container").on("click", "#btn-registrarme", function() {
		if (!working) {
			working = true;
			$.ajax({
				data: $("form").serialize(),
				type: 'POST',
				url: "registrarme",
				success: (result) => {
					if (result.error == null) {
						alert(result.exito);
						$(".container").html(partialLogin);
						history.pushState(partialLogin, '', 'login');
					}
					else {
						alert(result.error);
					}
					working = false;
				}
			});
		}
	});
});