$(document).ready(function() {

	//Defino las variables que voy a usar en el combate
	var primero;
	var cambio = false;
	var usoObjeto = false;
	var pokemonsVivosUsr = 3;
	var pokemonsVivosCpu = 3;
	var nextPokemonCpu = 0;

	/*$(".game-over")[0].showModal();
	$('.modalV').attr('src',"https://fontmeme.com/permalink/230526/5007cd2b81c93c581fc044aed10e703a.png");*/

	const setVariables = pokemon => {
		pokemon['porcVida'] = pokemon.vida * 0.003;
		pokemon['width'] = 100;
		pokemon['vidaActual'] = pokemon.vida;
		pokemon['estados'] = { envenenado: false, paralizado: false, quemado: false };
		pokemon['danioPorEstado'] = { veneno: pokemon.vida * 0.08, quemadura: pokemon.vida * 0.04 };
		pokemon['debilitado'] = false;
	};

	$(pokemonsUsuario).each(function() {
		setVariables(this);
	});
	$(pokemonsCpu).each(function() {
		setVariables(this);
	});

	var pokemonUsuario = pokemonsUsuario[0];
	var pokemonCpu = pokemonsCpu[0];
	var botonCambio = $('.suplentes')[0];
	var spriteUsuario = $('.img-usuario')[0];
	var spriteCpu = $('.img-cpu')[0];
	$(botonCambio).prop('disabled', true);

	//Coloco la vida de los pokemons en html porque sino hay que hacer un cast double to int en jsp
	$('#vidaPkmnUsr').html(pokemonUsuario.vida);
	$('#vidaPkmnCpu').html(pokemonCpu.vida);
	$('#vidaMaximaPkmnUsr').html(' / ' + pokemonUsuario.vida);
	$('#vidaMaximaPkmnCpu').html(' / ' + pokemonCpu.vida);

	$(document).on('click', '.suplentes', async function() {
		desactivarBotones();
		if (!pokemonUsuario.debilitado) {
			await cambiarPokemonUsr(this, false);
			cambio = true;
			iniciarTurno();
		} else {
			await cambiarPokemonUsr(this, true);
			activarBotones();
		}
	});

	$(document).on('click', '.objeto', async function() {
		desactivarBotones();
		usoObjeto = true;
		const quitarEstadosHtml = () => {
			$('#estadoUsuario').html('');
			$('#estadoUsuario').css('background-color', '');
		};
		const removerObjeto = () => {
			objetosUsuario[this.value] = null;
			$(this).remove();
		};
		switch (objetosUsuario[this.value].efecto) {
			case 'ANTI_VENENO':
				if (pokemonUsuario.estados.envenenado) {
					pokemonUsuario.estados.envenenado = false;
					quitarEstadosHtml();
					agregarAlHistorial(pokemonUsuario.nombre + ' ya no esta envenenado!');
					removerObjeto();
					iniciarTurno();
					return;
				}
				break;
			case 'ANTI_QUEMADURA':
				if (pokemonUsuario.estados.quemado) {
					pokemonUsuario.estados.quemado = false;
					quitarEstadosHtml();
					agregarAlHistorial(pokemonUsuario.nombre + ' ya no esta quemado!');
					removerObjeto();
					iniciarTurno();
					return;
				}
				break;
			case 'ANTI_PARALISIS':
				if (pokemonUsuario.estados.paralizado) {
					pokemonUsuario.estados.paralizado = false;
					quitarEstadosHtml();
					agregarAlHistorial(pokemonUsuario.nombre + ' ya no esta paralizado!');
					removerObjeto();
					iniciarTurno();
					return;
				}
				break;
			case 'RESTAURAR_TODO':
				quitarEstadosHtml();
				//$(pokemonUsuario.ataques).each(function() { this = false });
				$(Object.keys(pokemonUsuario.estados)).each(function() { pokemonUsuario.estados[this] = false });
				pokemonUsuario.vidaActual = pokemonUsuario.vida;
				agregarAlHistorial('Has usado restaurar todo en ' + pokemonUsuario.nombre);
				await moveProgressBar('#progressBarUsr', '#vidaPkmnUsr', pokemonUsuario, true);
				removerObjeto();
				iniciarTurno();
				return;
			case 'PLUS_400HP':
				pokemonUsuario.vidaActual += 400;
				if (pokemonUsuario.vidaActual > pokemonUsuario.vida) pokemonUsuario.vidaActual = pokemonUsuario.vida;
				agregarAlHistorial('Has usado una superpocion en ' + pokemonUsuario.nombre);
				await moveProgressBar('#progressBarUsr', '#vidaPkmnUsr', pokemonUsuario, true);
				removerObjeto();
				iniciarTurno();
				return;
			case 'PLUS_200HP':
				pokemonUsuario.vidaActual += 200;
				if (pokemonUsuario.vidaActual > pokemonUsuario.vida) pokemonUsuario.vidaActual = pokemonUsuario.vida;
				agregarAlHistorial('Has usado una pocion en ' + pokemonUsuario.nombre);
				await moveProgressBar('#progressBarUsr', '#vidaPkmnUsr', pokemonUsuario, true);
				removerObjeto();
				iniciarTurno();
				return;
			case 'POTENCIA_PLUS':
				$(pokemonUsuario.ataques).each(function() {
					this.potencia *= 1.25;
				});
				agregarAlHistorial('Ha subido el daño de ' + pokemonUsuario.nombre);
				removerObjeto();
				iniciarTurno();
				return;
		}
		usoObjeto = false;
		activarBotones();
	});

	//Detecto si el usuario presiona un ataque y comienza el intercambio de daño
	$(document).on('click', '.ataques', function() {
		desactivarBotones();
		iniciarTurno(this.value);
	});

	//Inicio el turno
	const iniciarTurno = async idAtaque => {

		await realizarAtaques(idAtaque);
		await efectosDeEstado();
		if (pokemonCpu.debilitado) {
			await pokemonDebilitado('cpu');
		}
		if (pokemonUsuario.debilitado) {
			pokemonDebilitado('user');
		} else {
			activarBotones();
		}
	};

	//Hago el intercambio de ataques entre el pokemon del usuario y la cpu
	const realizarAtaques = async idAtaque => {
		if (!cambio && !usoObjeto) {
			if (pokemonUsuario.velocidad > pokemonCpu.velocidad) {
				await ataqueUsuario(idAtaque);
				if (!pokemonCpu.debilitado) await ataqueCpu();
			} else {
				await ataqueCpu();
				if (!pokemonUsuario.debilitado) await ataqueUsuario(idAtaque);
			}
		} else {
			await ataqueCpu();
			cambio = false;
			usoObjeto = false;
		}
	};

	//Metodo que ejecuta el ataque del usuario
	const ataqueUsuario = async idAtaque => {
		var inmovil = efectoPorParalisis(pokemonUsuario.estados.paralizado);
		if (!inmovil) {
			var potencia = pokemonUsuario.ataques[idAtaque].potencia;
			var tipo = pokemonUsuario.ataques[idAtaque].tipo;
			if (tipo === pokemonUsuario.tipo) potencia *= 1.5;
			if (comprobarDebilidad(tipo, pokemonCpu.tipo)) potencia *= 2;
			else if (comprobarDebilidad(tipo, pokemonCpu.tipo) === false) potencia *= 0.5;
			pokemonCpu.vidaActual -= potencia;
			agregarAlHistorial('Utilizaste: ' + pokemonUsuario.ataques[idAtaque].nombre);
			await animacionAtaque('cpu');
			await moveProgressBar('#progressBarCpu', '#vidaPkmnCpu', pokemonCpu);
			if (!pokemonCpu.estados.envenenado && !pokemonCpu.estados.paralizado && !pokemonCpu.estados.quemado) {
				switch (tipo) {
					//ENVENENAR
					case 'VENENO':
						intentarEnvenenar('cpu');
						break;
					//PARALISIS
					case 'ELECTRICO':
						intentarParalizar('cpu');
						break;
					//QUEMAR
					case 'FUEGO':
						intentarQuemar('cpu');
						break;
				}
			}
			if (tipo === 'VAMPIRICO') {
				pokemonUsuario.vidaActual += potencia * 0.5;
				if (pokemonUsuario.vidaActual > pokemonUsuario.vida) pokemonUsuario.vidaActual = pokemonUsuario.vida
				await moveProgressBar('#progressBarUsr', '#vidaPkmnUsr', pokemonUsuario, true);
			}
		} else {
			agregarAlHistorial('Estas paralizado, no puedes atacar!');
			return new Promise(resolve => setTimeout(resolve, 1000));
		}
	};

	//Metodo que ejecuta el ataque de la cpu
	const ataqueCpu = async () => {
		var inmovil = efectoPorParalisis(pokemonCpu.estados.paralizado);
		if (!inmovil) {
			var ataque = Math.floor(Math.random() * pokemonCpu.ataques.length);
			var tipo = pokemonCpu.ataques[ataque].tipo;
			var potencia = pokemonCpu.ataques[ataque].potencia;
			if (tipo === pokemonCpu.tipo) potencia *= 1.5;
			if (comprobarDebilidad(tipo, pokemonUsuario.tipo)) potencia *= 2;
			else if (comprobarDebilidad(tipo, pokemonUsuario.tipo) === false) potencia *= 0.5;
			pokemonUsuario.vidaActual -= potencia;
			agregarAlHistorial('Ataque enemigo: ' + pokemonCpu.ataques[ataque].nombre, 'cpu');
			await animacionAtaque('user');
			await moveProgressBar('#progressBarUsr', '#vidaPkmnUsr', pokemonUsuario);
			if (!pokemonUsuario.estados.envenenado && !pokemonUsuario.estados.paralizado && !pokemonUsuario.estados.quemado) {
				switch (tipo) {
					//ENVENENAR
					case 'VENENO':
						intentarEnvenenar('user');
						break;
					//PARALISIS
					case 'ELECTRICO':
						intentarParalizar('user');
						break;
					//QUEMAR
					case 'FUEGO':
						intentarQuemar('user');
						break;
				}
			}
			if (tipo === 'VAMPIRICO') {
				pokemonCpu.vidaActual += potencia * 0.5;
				if (pokemonCpu.vidaActual > pokemonCpu.vida) pokemonCpu.vidaActual = pokemonCpu.vida
				await moveProgressBar('#progressBarCpu', '#vidaPkmnCpu', pokemonCpu, true);
			}
		} else {
			agregarAlHistorial('Enemigo paralizado, no puede atacar!', 'cpu');
			return new Promise(resolve => setTimeout(resolve, 1000));
		}
	};

	//Metodo que verifica si un pokemon se encuentra afectado por un efecto de estado
	const efectosDeEstado = async () => {
		if (primero) {
			await ordenDanioPorEstado(pokemonCpu, pokemonUsuario);
		} else if (!primero) {
			await ordenDanioPorEstado(pokemonUsuario, pokemonCpu);
		}
	};

	const ordenDanioPorEstado = async (primer, segundo) => {
		if (primer.estados.envenenado && segundo.estados.envenenado) {
			await danioPorEstado(primer, 'veneno');
			await danioPorEstado(segundo, 'veneno');
		} else if (primer.estados.quemado && segundo.estados.quemado) {
			await danioPorEstado(primer, 'quemadura');
			await danioPorEstado(segundo, 'quemadura');
		} else {
			if (primer.estados.envenenado) await danioPorEstado(primer, 'veneno');
			else if (primer.estados.quemado) await danioPorEstado(primer, 'quemadura');
			if (segundo.estados.envenenado) await danioPorEstado(segundo, 'veneno');
			else if (segundo.estados.quemado) await danioPorEstado(segundo, 'quemadura');
		}
	};

	//Metodo que es llamado si el pokemon está paralizado
	const efectoPorParalisis = paralizado => {
		if (paralizado) {
			var chanceDeNoAtacar = Math.floor(Math.random() * 100) + 1;
			if (chanceDeNoAtacar > 75) return true;
		}
		return false;
	};

	//Metodo que es llamado cuando quiero aplicar el daño de un efecto de estado
	const danioPorEstado = async (objetivo, estado) => {
		objetivo.vidaActual -= objetivo.danioPorEstado[estado];
		if (objetivo === pokemonUsuario) {
			await moveProgressBar('#progressBarUsr', '#vidaPkmnUsr', objetivo);
		} else {
			await moveProgressBar('#progressBarCpu', '#vidaPkmnCpu', objetivo);
		}
	};

	const comprobarDebilidad = (tipoAtaque, tipoPokemon) => {
		switch (tipoPokemon) {
			case 'AGUA':
				if (tipoAtaque === 'PLANTA' || tipoAtaque === 'ELECTRICO') return true;
				if (tipoAtaque === 'FUEGO') return false;
				break;
			case 'FUEGO':
				if (tipoAtaque === 'AGUA') return true;
				if (tipoAtaque === 'PLANTA') return false;
				break;
			case 'VENENO':
				if (tipoAtaque === 'TIERRA') return true;
				if (tipoAtaque === 'PLANTA') return false;
				break;
			case 'TIERRA':
				if (tipoAtaque === 'AGUA' || tipoAtaque === 'PLANTA') return true;
				if (tipoAtaque === 'VENENO') return false;
				break;
			case 'ELECTRICO':
				if (tipoAtaque === 'TIERRA') return true;
				if (tipoAtaque === 'AGUA') return false;
				break;
			case 'PLANTA':
				if (tipoAtaque === 'FUEGO') return true;
				if (tipoAtaque === 'ELECTRICO') return false;
				break;
		}
	};

	//Metodo que es llamado cuando un pokemon ataca a otro con un ataque de tipo veneno
	const intentarEnvenenar = name => {
		var random;
		random = Math.floor(Math.random() * 10) + 1;
		if (random > 7) {
			if (name === 'cpu') {
				pokemonCpu.estados.envenenado = true;
				$('#estadoCpu').html('PSN');
				$('#estadoCpu').css('background-color', 'purple');
				agregarAlHistorial(pokemonCpu.nombre + ' se ha envenenado!');
				if (primero === undefined) primero = true;
			}
			else {
				pokemonUsuario.estados.envenenado = true;
				$('#estadoUsuario').html('PSN');
				$('#estadoUsuario').css('background-color', 'purple');
				agregarAlHistorial(pokemonUsuario.nombre + ' se ha envenenado!', 'cpu');
				if (primero === undefined) primero = false;
			}
		}
	};

	//Metodo que es llamado cuando un pokemon ataca a otro con un ataque de tipo electrico
	const intentarParalizar = name => {
		var random;
		random = Math.floor(Math.random() * 10) + 1;
		if (random > 7) {
			if (name === 'cpu') {
				pokemonCpu.estados.paralizado = true;
				$('#estadoCpu').html('PAR');
				$('#estadoCpu').css('background-color', 'rgb(255, 225, 0)');
				agregarAlHistorial(pokemonCpu.nombre + ' se ha paralizado!');
				pokemonCpu.velocidad *= 0.5;
			} else {
				pokemonUsuario.estados.paralizado = true;
				$('#estadoUsuario').html('PAR');
				$('#estadoUsuario').css('background-color', 'rgb(255, 225, 0)');
				agregarAlHistorial(pokemonUsuario.nombre + ' se ha paralizado!', 'cpu');
				pokemonUsuario.velocidad *= 0.5;
			}
		}
	};

	//Metodo que es llamado cuando un pokemon ataca a otro con un ataque de tipo fuego
	const intentarQuemar = name => {
		var random;
		random = Math.floor(Math.random() * 10) + 1;
		if (random > 7) {
			if (name === 'cpu') {
				pokemonCpu.estados.quemado = true;
				$('#estadoCpu').html('BRN');
				$('#estadoCpu').css('background-color', 'red');
				agregarAlHistorial(pokemonCpu.nombre + ' se ha quemado!');
				$(pokemonCpu.ataques).each(function() {
					this.potencia *= 0.5;
				});
				if (primero === undefined) primero = true;
			} else {
				pokemonUsuario.estados.quemado = true;
				$('#estadoUsuario').html('BRN');
				$('#estadoUsuario').css('background-color', 'red');
				agregarAlHistorial(pokemonUsuario.nombre + ' se ha quemado!', 'cpu');
				$(pokemonUsuario.ataques).each(function() {
					this.potencia *= 0.5;
				});
				if (primero === undefined) primero = false;
			}
		}
	};

	//Metodo asincrono que mueve la barra de vida de los pokemons y me obliga a usar promesas para esperar que finalize
	const moveProgressBar = (idProgressBar, idVida, pokemon, recover) => {
		return new Promise(resolve => {
			setTimeout(() => {
				var elem = $(idProgressBar);
				var interval = setInterval(frame, 10);
				var vidaAnterior = parseFloat($(idVida).html());
				function frame() {
					if (recover) {
						if (vidaAnterior >= pokemon.vidaActual || vidaAnterior > pokemon.vida) {
							clearInterval(interval);
							resolve();
						} else {
							pokemon.width += 0.3;
							elem.width(pokemon.width + '%');
							vidaAnterior += pokemon.porcVida;
							if (vidaAnterior < pokemon.vidaActual && vidaAnterior < pokemon.vida) $(idVida).html(parseInt(vidaAnterior));
							else if (pokemon.vidaActual < pokemon.vida) $(idVida).html(parseInt(pokemon.vidaActual));
							else {
								$(idVida).html(pokemon.vida);
							}
						}
					} else {
						if (vidaAnterior <= pokemon.vidaActual || vidaAnterior < 0) {
							clearInterval(interval);
							resolve();
						} else {
							pokemon.width -= 0.3;
							elem.width(pokemon.width + '%');
							vidaAnterior -= pokemon.porcVida;
							if (vidaAnterior > pokemon.vidaActual && vidaAnterior > 0) $(idVida).html(parseInt(vidaAnterior));
							else if (pokemon.vidaActual > 0) $(idVida).html(parseInt(pokemon.vidaActual));
							else {
								$(idVida).html(0);
								pokemon.debilitado = true;
							}
						}
					}
				}
			}, 600);
		});
	};

	const animacionAtaque = async objetivo => {
		var contador = 0;
		var imgObjetivo = objetivo === 'cpu' ? $('.img-cpu') : $('.img-usuario');
		var imgAtacante = objetivo === 'cpu' ? $('.img-usuario') : $('.img-cpu');
		for (var i = 0; i < 2; i++) {
			await new Promise(resolve => {
				imgAtacante.animate({ bottom: '20px' }, 120, () => {
					imgAtacante.animate({ bottom: 0 }, 120, resolve)
				});
			});
		}
		$('#golpe')[0].play();
		//await new Promise(resolve => {
		interval = setInterval(() => {
			if (contador === 6) {
				clearInterval(interval);
				resolve();
			}
			imgObjetivo.css('visibility') === 'hidden' ? imgObjetivo.css('visibility', 'visible') : imgObjetivo.css('visibility', 'hidden');
			contador++;
		}, 150);
		//});
	};

	//Metodo que habilita los botones al finalizar el intercambio de daños
	const activarBotones = () => {
		if (pokemonsVivosUsr > 0 && pokemonsVivosCpu > 0) {
			setTimeout(() => {
				$('.ataques').prop('disabled', false);
				$('.suplentes').each(function() {
					if (this.value !== botonCambio.value && this.value !== '-1') $(this).prop('disabled', false);
				});
				$('.objeto').prop('disabled', false);
				$('#ataqueUsuario').css('visibility', 'hidden');
				$('#ataqueCpu').css('visibility', 'hidden');
			}, 500);
		}
	};

	const desactivarBotones = () => {
		$('.ataques').prop('disabled', true);
		$('.suplentes').prop('disabled', true);
		$('.objeto').prop('disabled', true);
	};

	const agregarAlHistorial = (texto, player) => {
		if (player === 'cpu') {
			$('.historial').prepend('<li style="color: red;">' + texto + '</li>');
		} else {
			$('.historial').prepend('<li style="color: green;">' + texto + '</li>');
		}


	};

	const pokemonDebilitado = async objetivo => {
		if (objetivo === 'cpu') {
			if (--pokemonsVivosCpu > 0) {
				agregarAlHistorial(pokemonCpu.nombre + ' enemigo ha sido debilitado!');
				await cambiarPokemonCpu();
			} else {
				var dialog = $('.game-over')[0];
				if (!musica.paused) musica.pause();
				$('.modalV').attr('src', "https://fontmeme.com/permalink/230526/5007cd2b81c93c581fc044aed10e703a.png");
				dialog.showModal();
			}
		} else {
			if (--pokemonsVivosUsr > 0) {
				agregarAlHistorial('Tu ' + pokemonUsuario.nombre + ' ha sido debilitado!', 'cpu');
				$('.suplentes').each(function() {
					if (this.value !== botonCambio.value && this.value !== -1) $(this).prop('disabled', false);
					if (this.value === botonCambio.value) this.value = -1;
				});
			} else {
				var dialog = $('.game-over')[0];
				if (!musica.paused) musica.pause();
				$('.modalV').attr('src', "https://fontmeme.com/permalink/230526/1ee9f0defcfb3c531d273fd1b430e0ba.png");
				dialog.showModal();
			}
		}
	};

	//Cambio de pokemon usuario
	const cambiarPokemonUsr = async (boton, debilitado) => {
		botonCambio = boton;
		pokemonUsuario = pokemonsUsuario[boton.value];
		await new Promise(resolve => {
			$(spriteUsuario).fadeOut(1000, () => {
				if (debilitado) agregarAlHistorial('Enviaste a ' + pokemonUsuario.nombre);
				else agregarAlHistorial('Cambiaste a ' + pokemonUsuario.nombre);
				spriteUsuario = $('.img-usuario')[boton.value];
				$('#nombrePkmnUsr').html(pokemonUsuario.nombre);
				$('#vidaPkmnUsr').html(pokemonUsuario.vidaActual);
				$('#vidaMaximaPkmnUsr').html(' / ' + pokemonUsuario.vida);
				$('#progressBarUsr').width(pokemonUsuario.width + '%');
				$('.ataques').each((i, ataque) => {
					$(ataque).html(pokemonUsuario.ataques[i].nombre);
				});
				if (pokemonUsuario.estados.envenenado) {
					$('#estadoUsuario').html('PSN');
					$('#estadoUsuario').css('background-color', 'purple');
				} else if (pokemonUsuario.estados.quemado) {
					$('#estadoUsuario').html('BRN');
					$('#estadoUsuario').css('background-color', 'red');
				} else if (pokemonUsuario.estados.paralizado) {
					$('#estadoUsuario').html('PAR');
					$('#estadoUsuario').css('background-color', 'rgb(255, 225, 0)');
				} else {
					$('#estadoUsuario').html('');
					$('#estadoUsuario').css('background-color', '');
				}
				$(spriteUsuario).fadeIn(1000, resolve);
			});
		});
	};

	//Cambio de pokemon cpu
	const cambiarPokemonCpu = async () => {
		nextPokemonCpu++;
		pokemonCpu = pokemonsCpu[nextPokemonCpu];
		await new Promise(resolve => {
			$(spriteCpu).fadeOut(1000, () => {
				agregarAlHistorial('El enemigo envio a ' + pokemonCpu.nombre, 'cpu');
				spriteCpu = $('.img-cpu')[nextPokemonCpu];
				$('#nombrePkmnCpu').html(pokemonCpu.nombre);
				$('#vidaPkmnCpu').html(pokemonCpu.vidaActual);
				$('#vidaMaximaPkmnCpu').html(' / ' + pokemonCpu.vida);
				$('#progressBarCpu').width(pokemonCpu.width + '%');
				$('#estadoCpu').html('');
				$('#estadoCpu').css('background-color', '');
				$(spriteCpu).fadeIn(1000, resolve);
			});
		});
	};

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 	Estilos y animaciones de la vista de batalla
	 */

	//Comportamiento de la mochila de objetos

	var ocultaMochila;
	var oculto = true;
	$(window).on('load', () => {
		ocultaMochila = '-' + $('.mochila').css('width');
		$('.mochila').css('left', ocultaMochila);
	});
	$('#abrirMochila').click(() => {
		oculto ? $('.mochila').animate({ left: 0 }, 600, () => oculto = false)
			: $('.mochila').animate({ left: ocultaMochila }, 600, () => oculto = true);
	});

	$('#abrirMochila').hover(() => {
		if (oculto) $('.mochila').animate({ left: 0 }, 600, () => oculto = false);
	}, ocultarMochilaAuto);

	$('.mochila').mouseleave(ocultarMochilaAuto);

	function ocultarMochilaAuto() {
		setTimeout(() => {
			if (!$('.mochila').is(':hover') && !$('#abrirMochila').is(':hover') && !oculto) {
				$('.mochila').animate({ left: ocultaMochila }, 600, () => oculto = true);
			}
		}, 1000);
	}

	$(window).on('load', () => {
		$('.img-suplente').each(function() {
			var width = $(this).prop('width') * 0.6;
			$(this).prop('width', width);
		});
		$('.imagenBatalla').each(function() {
			var width = $(this).prop('width') * 2;
			$(this).css('width', width + 'px');
		});
	});

	var reproducirDialog = $('.reproducir-dialog')[0];
	var musica = $('#musica')[0];
	var golpe = $('#golpe')[0];
	musica.volume = 0.02;
	golpe.volume = 0.08;
	reproducirDialog.show();
	$('.yes').click(() => {
		reproducirDialog.close();
		playPauseMusica();
	});
	$('.no').click(() => { reproducirDialog.close() });
	$('#slider').on('input', function() {
		musica.volume = this.value * 0.002;
		golpe.volume = this.value * 0.008;
		console.log("Volume set to ", this.value);
	});
	$(window).click(() => { if (!$(reproducirDialog).is(':hover') && reproducirDialog.open) reproducirDialog.close() });
	$('.reproducir').click(function() {
		playPauseMusica();
	});
	$(document).keypress(e => {
		if (e.key === 'p') {
			playPauseMusica();
			if (reproducirDialog.open) reproducirDialog.close();
		}
	});

	const playPauseMusica = () => {
		var boton = $('.reproducir');
		if (musica.paused) {
			musica.play();
			boton.html('⏸️');
		}
		else {
			musica.pause();
			boton.html('▶️');
		}
	};
});