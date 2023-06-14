$(document).ready(function() {

	//Verifico si el raton está encima de usuario o logoutButton para no ocultar logoutButton
	const isHover = () => {
		if($("#usuario").is(':hover') || $(".menu-usuario").is(':hover')){
			return true;
		}
	};

	//El botón logoutButton aparece al pasar por encima del nombre de usuario
	$("#usuario").hover(() => {
		$(".menu-usuario").css('display', 'flex');
	}, () => {
		setTimeout(() => {
			if (!isHover()) {
				$(".menu-usuario").hide();
			}
		}, 1000);
	});

	//Comportamiento del botón logoutButton
	$(".menu-usuario").mouseleave(function() {
		setTimeout(() => {
			if (!isHover()) {
				$(this).hide();
			}
		}, 1000);
	});
	
	$(".batalla-texto").click(() => {
		window.location.href = "elegir-equipo";
	});
	
	
	$(".gacha").click(() => {
		window.location.href = "gachapon";
	});
	
	$(".pokedex-texto").click(() => {
		window.location.href = "pokedex";
	});
});