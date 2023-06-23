$(document).ready(() => {

	$(window).on('load', () => resizeSprite());
	document.readyState === 'complete' && resizeSprite();

	function resizeSprite() {
		var width = $('.sprite').prop('width') * 2;
		$('.sprite').css({
			width: `${width}px`,
			display: 'block'
		});
	}

	if (loadedScripts.pokemonUsuario) return;
	else loadedScripts['pokemonUsuario'] = true;
	
	$(document).on('click', '.desbloquear', function() {
		if (confirm('Estás seguro de desbloquear el ataque? Te costará 80 puntos')) {
			$.ajax({
				data: { idUAP: this.value },
				url: `${root}desbloquear-ataque`,
				type: 'POST',
				success: result => {
					if (result === 'exito') {
						var puntos = parseInt($('#puntos').html() - 80);
						$(this).parent().siblings('.switch').show();
						$(this).parents().removeClass('bloqueado');
						$(this).parent().remove();
						$('#puntos').html(puntos);
					} else alert(result);
				}
			});
		}
	});

	$(document).on('click', '.activar', function() {
		if (this.checked) {
			activarAtaque(this, 'activar');
		} else {
			activarAtaque(this, 'desactivar');
		}
	});

	const activarAtaque = (elem, accion) => {
		var idPokemon = $('.activo').val();
		$.ajax({
			data: { idUAP: elem.value, idPokemon, accion },
			url: `${root}activar-ataque`,
			type: 'POST',
			success: result => {
				if (result === 'exito') $(elem).siblings('label').html(accion === 'activar' ? 'Activado' : 'Desactivado');
				else {
					elem.checked = accion === 'activar' ? false : true;
					alert(result);
				}
			}
		});
	}

});