$(document).ready(function() {

	//Verifico si el raton está encima de usuario o logoutButton para no ocultar logoutButton
	const isHover = () => {
		if($("#usuario").is(':hover') || $(".logoutButton").is(':hover')){
			return true;
		}
	};

	//El botón logoutButton aparece al pasar por encima del nombre de usuario
	$("#usuario").hover(() => {
		$(".logoutButton").show();
	}, () => {
		setTimeout(() => {
			if (!isHover()) {
				$(".logoutButton").hide();
			}
		}, 1000);
	});

	//Comportamiento del botón logoutButton
	$(".logoutButton").mouseleave(function() {
		setTimeout(() => {
			if (!isHover()) {
				$(this).hide();
			}
		}, 1000);
	});

	$(".logoutButton").click(() => {
		window.location.href = "logout";
	});
});
