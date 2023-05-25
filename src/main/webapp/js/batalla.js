$(document).ready(function() {

	//Defino las variables que voy a usar en el combate
	var primero;
	var cambio = false;
	var pokemonsVivosUsr = 3;
	var pokemonsVivosCpu = 3;
	var nextPokemonCpu = 0;


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

	//Cambio de pokemon usuario
	const cambiarPokemonUsr = async boton => {
		$('.suplentes').prop('disabled', true);
		botonCambio = boton;
		await new Promise(resolve => {
			$(spriteUsuario).fadeOut(1000, () => {
				spriteUsuario = $('.img-usuario')[boton.value];
				$(spriteUsuario).fadeIn(1000, resolve);
			});
		});
		pokemonUsuario = pokemonsUsuario[boton.value];
		$('#nombrePkmnUsr').html(pokemonUsuario.nombre);
		$('#vidaPkmnUsr').html(pokemonUsuario.vidaActual);
		$('#vidaMaximaPkmnUsr').html(' / ' + pokemonUsuario.vida);
		$('#progressBarUsr').width(pokemonUsuario.width + '%');
		$('.ataques').each((i, ataque) => {
			$(ataque).html(pokemonUsuario.ataques[i].nombre);
		});
		if (pokemonUsuario.estados.envenenado) $('#estadoUsuario').html('Poisoned');
		else if (pokemonUsuario.estados.quemado) $('#estadoUsuario').html('Burned');
		else if (pokemonUsuario.estados.paralizado) $('#estadoUsuario').html('Paralized');
		else $('#estadoUsuario').html('');
	};

	//Cambio de pokemon cpu
	const cambiarPokemonCpu = async () => {
		nextPokemonCpu++;
		pokemonCpu = pokemonsCpu[nextPokemonCpu];
		await new Promise(resolve => {
			$(spriteCpu).fadeOut(1000, () => {
				spriteCpu = $('.img-cpu')[nextPokemonCpu];
				$(spriteCpu).fadeIn(1000, resolve);
			});
		});
		$('#nombrePkmnCpu').html(pokemonCpu.nombre);
		$('#vidaPkmnCpu').html(pokemonCpu.vidaActual);
		$('#vidaMaximaPkmnCpu').html(' / ' + pokemonCpu.vida);
		$('#progressBarCpu').width(pokemonCpu.width + '%');
		if (pokemonCpu.estados.envenenado) $('#estadoCpu').html('Poisoned');
		else if (pokemonCpu.estados.quemado) $('#estadoCpu').html('Burned');
		else if (pokemonCpu.estados.paralizado) $('#estadoCpu').html('Paralized');
		else $('#estadoCpu').html('');
	};

	$(document).on('click', '.suplentes', async function() {
		if (!pokemonUsuario.debilitado) {
			await cambiarPokemonUsr(this);
			cambio = true;
			iniciarTurno();
		} else {
			await cambiarPokemonUsr(this);
			activarBotones();
		}
	});

	//Coloco la vida de los pokemons en html porque sino hay que hacer un cast double to int en jsp
	$('#vidaPkmnUsr').html(pokemonUsuario.vida);
	$('#vidaPkmnCpu').html(pokemonCpu.vida);
	$('#vidaMaximaPkmnUsr').html(' / ' + pokemonUsuario.vida);
	$('#vidaMaximaPkmnCpu').html(' / ' + pokemonCpu.vida);

	//Detecto si el usuario presiona un ataque y comienza el intercambio de daño
	$(document).on('click', '.ataques', function() {
		iniciarTurno(this.value);
	});

	//Inicio el turno
	const iniciarTurno = async idAtaque => {
		$('.ataques').prop('disabled', true);
		$('.suplentes').prop('disabled', true);

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
		if (!cambio) {
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
			$('#ataqueUsuario').html('Utilizaste: ' + pokemonUsuario.ataques[idAtaque].nombre);
			$('#ataqueUsuario').css('visibility', 'visible');
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
		} else {
			$('#ataqueUsuario').html('Estas paralizado, no puedes atacar!');
			$('#ataqueUsuario').css('visibility', 'visible');
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
			$('#ataqueCpu').html('Ataque enemigo: ' + pokemonCpu.ataques[ataque].nombre);
			$('#ataqueCpu').css('visibility', 'visible');
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
		} else {
			$('#ataqueCpu').html('Enemigo paralizado, no puede atacar!');
			$('#ataqueCpu').css('visibility', 'visible');
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
				$('#estadoCpu').html('Poisoned');
				if (primero === undefined) primero = true;
			}
			else {
				pokemonUsuario.estados.envenenado = true;
				$('#estadoUsuario').html('Poisoned');
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
				$('#estadoCpu').html('Paralized');
				pokemonCpu.velocidad *= 0.5;
			} else {
				pokemonUsuario.estados.paralizado = true;
				$('#estadoUsuario').html('Paralized');
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
				$('#estadoCpu').html('Burned');
				$(pokemonCpu.ataques).each(function() {
					this.potencia *= 0.5;
				});
				if (primero === undefined) primero = true;
			} else {
				pokemonUsuario.estados.quemado = true;
				$('#estadoUsuario').html('Burned');
				$(pokemonUsuario.ataques).each(function() {
					this.potencia *= 0.5;
				});
				if (primero === undefined) primero = false;
			}
		}
	};

	//Metodo asincrono que mueve la barra de vida de los pokemons y me obliga a usar promesas para esperar que finalize
	const moveProgressBar = (idProgressBar, idVida, pokemon) => {
		return new Promise(resolve => {
			setTimeout(() => {
				var elem = $(idProgressBar);
				var interval = setInterval(frame, 10);
				var vidaAnterior = $(idVida).html();
				function frame() {
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
			}, 500);
		});
	};

	//Metodo que habilita los botones al finalizar el intercambio de daños
	const activarBotones = () => {
		if (pokemonsVivosUsr > 0 && pokemonsVivosCpu > 0) {
			setTimeout(() => {
				$('.ataques').prop('disabled', false);
				$('.suplentes').each(function() {
					if (this.value !== botonCambio.value && this.value !== -1) $(this).prop('disabled', false);
				});
				$('#ataqueUsuario').css('visibility', 'hidden');
				$('#ataqueCpu').css('visibility', 'hidden');
			}, 500);
		}
	};

	const pokemonDebilitado = async objetivo => {
		if (objetivo === 'cpu') {
			if (--pokemonsVivosCpu > 0) {
				$('#vidaPkmnCpu').html('Debilitado');
				await cambiarPokemonCpu();
			} else {
				var dialog = $('.game-over')[0];
				dialog.showModal()
				$('#ataqueUsuario').html('Ganaste');
			}
		} else {
			if (--pokemonsVivosUsr > 0) {
				$('#vidaPkmnUsr').html('Debilitado');
				$('.suplentes').each(function() {
					if (this.value !== botonCambio.value && this.value !== -1) $(this).prop('disabled', false);
					if (this.value === botonCambio.value) this.value = -1;
				});
			} else {
				var dialog = $('.game-over')[0];
				dialog.showModal()
				$('#ataqueUsuario').html('Perdiste');
			}
		}
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
	musica.volume = 0.02;
	reproducirDialog.show();
	$('.yes').click(() => {
		reproducirDialog.close();
		playPauseMusica();
	});
	$('.no').click(() => { reproducirDialog.close() });
	$('#slider').on('input', function() {
		musica.volume = this.value * 0.002;
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