$(document).ready(function() {

	//Defino las variables que voy a usar en el combate
	var primero;
	var cambio = false;
	var usoObjeto = false;
	var pokemonsVivosUsr = 3;
	var pokemonsVivosCpu = 3;
	var nextPokemonCpu = 0;
	var ganador;
	var objetosUtilizados = [];
	var startTimer = new Date().getTime();

	const TABLA_DE_TIPOS_ATAQUES = {
		FUEGO: { fuerte_contra: ['PLANTA', 'HIELO', 'ACERO'], debil_contra: ['AGUA', 'FUEGO', 'DRAGON'] },
		AGUA: { fuerte_contra: ['FUEGO', 'TIERRA'], debil_contra: ['PLANTA', 'AGUA', 'DRAGON'] },
		TIERRA: { fuerte_contra: ['FUEGO', 'VENENO', 'ACERO', 'ELECTRICO'], debil_contra: ['PLANTA'] },
		PLANTA: { fuerte_contra: ['AGUA', 'TIERRA'], debil_contra: ['FUEGO', 'VENENO', 'PLANTA', 'ACERO', 'DRAGON'] },
		ELECTRICO: { fuerte_contra: ['AGUA'], debil_contra: ['TIERRA', 'DRAGON', 'ELECTRICO', 'PLANTA'] },
		VENENO: { fuerte_contra: ['PLANTA'], debil_contra: ['ACERO', 'TIERRA', 'VENENO'] },
		NORMAL: { fuerte_contra: [], debil_contra: ['ACERO'] },
		HIELO: { fuerte_contra: ['PLANTA', 'TIERRA', 'DRAGON'], debil_contra: ['ACERO', 'FUEGO', 'AGUA', 'HIELO'] },
		ACERO: { fuerte_contra: ['HIELO'], debil_contra: ['FUEGO', 'AGUA', 'ELECTRICO', 'ACERO'] },
		DRAGON: { fuerte_contra: ['DRAGON'], debil_contra: ['ACERO'] },
		PSIQUICO: { fuerte_contra: ['VENENO', 'LUCHA'], debil_contra: ['ACERO', 'PSIQUICO', 'SINIESTRO'] },
		SINIESTRO: { fuerte_contra: ['PSIQUICO'], debil_contra: ['LUCHA', 'SINIESTRO'] },
		LUCHA: { fuerte_contra: ['NORMAL', 'LUCHA', 'SINIESTRO', 'ACERO'], debil_contra: ['PSIQUICO', 'VENENO'] },
		VAMPIRICO: { fuerte_contra: [], debil_contra: [] }
	};

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
	$('#vidaMaximaPkmnUsr').html(` / ${pokemonUsuario.vida}`);
	$('#vidaMaximaPkmnCpu').html(` / ${pokemonCpu.vida}`);

	$(document).on('click', '.suplentes', async function() {
		desactivarBotones();
		if (!pokemonUsuario.debilitado) {
			var idAtaqueCpu = generarAtaqueCpu(); //Genero el ataque de la cpu antes de cambiar de pokemon
			await cambiarPokemonUsr(this, false);
			cambio = true;
			iniciarTurno(idAtaqueCpu); //Envio el ataque de la cpu
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
		objetosUtilizados.push(objetosUsuario[this.value].efecto);
		switch (objetosUsuario[this.value].efecto) {
			case 'ANTI_VENENO':
				if (pokemonUsuario.estados.envenenado) {
					pokemonUsuario.estados.envenenado = false;
					quitarEstadosHtml();
					removerObjeto();
					await sleep(1, agregarAlHistorial, `${pokemonUsuario.nombre} ya no esta envenenado!`);
					iniciarTurno();
					return;
				}
				break;
			case 'ANTI_QUEMADURA':
				if (pokemonUsuario.estados.quemado) {
					pokemonUsuario.estados.quemado = false;
					quitarEstadosHtml();
					removerObjeto();
					await sleep(1, agregarAlHistorial, `${pokemonUsuario.nombre} ya no esta quemado!`);
					iniciarTurno();
					return;
				}
				break;
			case 'ANTI_PARALISIS':
				if (pokemonUsuario.estados.paralizado) {
					pokemonUsuario.estados.paralizado = false;
					quitarEstadosHtml();
					removerObjeto();
					await sleep(1, agregarAlHistorial, `${pokemonUsuario.nombre} ya no esta paralizado!`);
					iniciarTurno();
					return;
				}
				break;
			case 'RESTAURAR_TODO':
				quitarEstadosHtml();
				$(Object.keys(pokemonUsuario.estados)).each(function() { pokemonUsuario.estados[this] = false });
				pokemonUsuario.vidaActual = pokemonUsuario.vida;
				agregarAlHistorial(`Has usado restaurar todo en ${pokemonUsuario.nombre}`);
				await moveProgressBar('#progressBarUsr', '#vidaPkmnUsr', pokemonUsuario, true);
				removerObjeto();
				iniciarTurno();
				return;
			case 'PLUS_400HP':
				pokemonUsuario.vidaActual += 400;
				if (pokemonUsuario.vidaActual > pokemonUsuario.vida) pokemonUsuario.vidaActual = pokemonUsuario.vida;
				agregarAlHistorial(`Has usado una superpocion en ${pokemonUsuario.nombre}`);
				await moveProgressBar('#progressBarUsr', '#vidaPkmnUsr', pokemonUsuario, true);
				removerObjeto();
				iniciarTurno();
				return;
			case 'PLUS_200HP':
				pokemonUsuario.vidaActual += 200;
				if (pokemonUsuario.vidaActual > pokemonUsuario.vida) pokemonUsuario.vidaActual = pokemonUsuario.vida;
				agregarAlHistorial(`Has usado una pocion en ${pokemonUsuario.nombre}`);
				await moveProgressBar('#progressBarUsr', '#vidaPkmnUsr', pokemonUsuario, true);
				removerObjeto();
				iniciarTurno();
				return;
			case 'POTENCIA_PLUS':
				$(pokemonUsuario.ataques).each(function() {
					this.potencia *= 1.25;
				});
				removerObjeto();
				await sleep(1, agregarAlHistorial, `Ha subido el daño de ${pokemonUsuario.nombre}`);
				iniciarTurno();
				return;
		}
		usoObjeto = false;
		activarBotones();
	});

	//Detecto si el usuario presiona un ataque y comienza el intercambio de daño
	$(document).on('click', '.ataques', function() {
		desactivarBotones();
		iniciarTurno(this.value); //Envio el ataque del usuario
	});

	//Inicio el turno
	const iniciarTurno = async idAtaque => {

		await realizarAtaques(idAtaque);
		if (ganador === undefined) await efectosDeEstado();
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
			await ataqueCpu(idAtaque);
			cambio = false;
			usoObjeto = false;
		}
	};

	//Metodo que ejecuta el ataque del usuario
	const ataqueUsuario = async idAtaque => {
		var paralizado = pokemonUsuario.estados.paralizado && getRandom(100) > 75 ? true : false;
		var inmovil = pokemonUsuario.ataques[idAtaque].precataque < getRandom(100) ? true : false;
		if (!paralizado) {
			agregarAlHistorial(`Utilizaste: ${pokemonUsuario.ataques[idAtaque].nombre}`);
			if (!inmovil) {
				var eficaz;
				var critico;
				var potencia = pokemonUsuario.ataques[idAtaque].potencia;
				var tipo = pokemonUsuario.ataques[idAtaque].tipo;
				if (tipo === pokemonUsuario.tipo) potencia *= 1.5;
				if (comprobarDebilidad(tipo, pokemonCpu.tipo)) {
					potencia *= 2;
					eficaz = '¡Es muy eficaz!';
				}
				else if (comprobarDebilidad(tipo, pokemonCpu.tipo) === false) {
					potencia *= 0.5;
					eficaz = 'No es muy eficaz...';
				}
				if (getRandom(100) <= 5) {
					potencia *= 1.5;
					critico = '¡Golpe crítico!';
				}
				pokemonCpu.vidaActual -= potencia;
				await animacionAtaque('cpu');
				await moveProgressBar('#progressBarCpu', '#vidaPkmnCpu', pokemonCpu);
				if (eficaz !== undefined && critico !== undefined) {
					agregarAlHistorial(eficaz);
					await sleep(1, agregarAlHistorial, critico);
				} else if (eficaz !== undefined) await sleep(1, agregarAlHistorial, eficaz);
				else if (critico !== undefined) await sleep(1, agregarAlHistorial, critico);
				if (!pokemonCpu.estados.envenenado && !pokemonCpu.estados.paralizado
					&& !pokemonCpu.estados.quemado && !pokemonCpu.debilitado) {
					switch (tipo) {
						//ENVENENAR
						case 'VENENO':
							await intentarEnvenenar('cpu');
							break;
						//PARALISIS
						case 'ELECTRICO':
							await intentarParalizar('cpu');
							break;
						//QUEMAR
						case 'FUEGO':
							await intentarQuemar('cpu');
							break;
					}
				}
				if (tipo === 'VAMPIRICO') {
					pokemonUsuario.vidaActual += potencia * 0.5;
					if (pokemonUsuario.vidaActual > pokemonUsuario.vida) pokemonUsuario.vidaActual = pokemonUsuario.vida
					await moveProgressBar('#progressBarUsr', '#vidaPkmnUsr', pokemonUsuario, true);
				}
			} else {
				await sleep(1);
				return sleep(1, agregarAlHistorial, `Tu ${pokemonUsuario.nombre} ha fallado el ataque!`);
			}
		} else {
			return sleep(1, agregarAlHistorial, 'Estas paralizado, no puedes atacar!');
		}
	};

	//Metodo que ejecuta el ataque de la cpu
	const ataqueCpu = async idAtaque => {
		if (idAtaque === undefined) {
			var idAtaque = generarAtaqueCpu();
		}
		var paralizado = pokemonCpu.estados.paralizado && getRandom(100) > 75 ? true : false;
		var inmovil = pokemonCpu.ataques[idAtaque].precataque < getRandom(100) ? true : false;
		if (!paralizado) {
			agregarAlHistorial(`Ataque enemigo: ${pokemonCpu.ataques[idAtaque].nombre}`, 'cpu');
			if (!inmovil) {
				var eficaz;
				var critico;
				var tipo = pokemonCpu.ataques[idAtaque].tipo;
				var potencia = pokemonCpu.ataques[idAtaque].potencia;
				if (tipo === pokemonCpu.tipo) potencia *= 1.5;
				if (comprobarDebilidad(tipo, pokemonUsuario.tipo)) {
					potencia *= 2;
					eficaz = '¡Es muy eficaz!';
				}
				else if (comprobarDebilidad(tipo, pokemonUsuario.tipo) === false) {
					potencia *= 0.5;
					eficaz = 'No es muy eficaz...';
				}
				if (getRandom(100) <= 5) {
					potencia *= 1.5;
					critico = '¡Golpe crítico!';
				}
				pokemonUsuario.vidaActual -= potencia;
				await animacionAtaque('user');
				await moveProgressBar('#progressBarUsr', '#vidaPkmnUsr', pokemonUsuario);
				if (eficaz !== undefined && critico !== undefined) {
					agregarAlHistorial(eficaz, 'cpu');
					await sleep(1, agregarAlHistorial, critico, 'cpu');
				} else if (eficaz !== undefined) await sleep(1, agregarAlHistorial, eficaz, 'cpu');
				else if (critico !== undefined) await sleep(1, agregarAlHistorial, critico, 'cpu');
				if (!pokemonUsuario.estados.envenenado && !pokemonUsuario.estados.paralizado
					&& !pokemonUsuario.estados.quemado && !pokemonUsuario.debilitado) {
					switch (tipo) {
						//ENVENENAR
						case 'VENENO':
							await intentarEnvenenar('user');
							break;
						//PARALISIS
						case 'ELECTRICO':
							await intentarParalizar('user');
							break;
						//QUEMAR
						case 'FUEGO':
							await intentarQuemar('user');
							break;
					}
				}
				if (tipo === 'VAMPIRICO') {
					pokemonCpu.vidaActual += potencia * 0.5;
					if (pokemonCpu.vidaActual > pokemonCpu.vida) pokemonCpu.vidaActual = pokemonCpu.vida
					await moveProgressBar('#progressBarCpu', '#vidaPkmnCpu', pokemonCpu, true);
				}
			} else {
				await sleep(1);
				return sleep(1, agregarAlHistorial, `El ${pokemonCpu.nombre} enemigo ha fallado el ataque!`, 'cpu');
			}
		} else {
			return sleep(1, agregarAlHistorial, 'Enemigo paralizado, no puede atacar!', 'cpu');
		}
	};

	const generarAtaqueCpu = () => {
		var iAtaquesFuertes = [];
		var iAtaquesNormales = [];
		var iAtaquesDebiles = [];
		$(pokemonCpu.ataques).each((i, atk) => {
			if (comprobarDebilidad(atk.tipo, pokemonUsuario.tipo)) iAtaquesFuertes.push(i);
			else if (comprobarDebilidad(atk.tipo, pokemonUsuario.tipo) === false) iAtaquesDebiles.push(i);
			else iAtaquesNormales.push(i);
		});
		return iAtaquesNormales.length === 0 && iAtaquesDebiles.length === 0 || iAtaquesFuertes.length !== 0 && getRandom(10) <= 9 ?
			iAtaquesFuertes[getRandom(iAtaquesFuertes.length) - 1] :
			iAtaquesDebiles.length === 0 || iAtaquesNormales.length !== 0 && getRandom(10) <= 9 ?
				iAtaquesNormales[getRandom(iAtaquesNormales.length) - 1] :
				iAtaquesDebiles[getRandom(iAtaquesDebiles.length) - 1];
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

	//Metodo que es llamado cuando quiero aplicar el daño de un efecto de estado
	const danioPorEstado = async (objetivo, estado) => {
		objetivo.vidaActual -= objetivo.danioPorEstado[estado];
		if (objetivo === pokemonUsuario) await moveProgressBar('#progressBarUsr', '#vidaPkmnUsr', objetivo);
		else await moveProgressBar('#progressBarCpu', '#vidaPkmnCpu', objetivo);
	};

	const comprobarDebilidad = (tipoAtaque, tipoPokemon) => {
		if (TABLA_DE_TIPOS_ATAQUES[tipoAtaque].fuerte_contra.indexOf(tipoPokemon) > -1) return true;
		else if (TABLA_DE_TIPOS_ATAQUES[tipoAtaque].debil_contra.indexOf(tipoPokemon) > -1) return false;
	};

	//Metodo que es llamado cuando un pokemon ataca a otro con un ataque de tipo veneno
	const intentarEnvenenar = async name => {
		if (getRandom(10) > 7) {
			if (name === 'cpu') {
				pokemonCpu.estados.envenenado = true;
				$('#estadoCpu').html('PSN');
				$('#estadoCpu').css('background-color', 'purple');
				agregarAlHistorial(`${pokemonCpu.nombre} se ha envenenado!`);
				if (primero === undefined) primero = true;
			}
			else {
				pokemonUsuario.estados.envenenado = true;
				$('#estadoUsuario').html('PSN');
				$('#estadoUsuario').css('background-color', 'purple');
				agregarAlHistorial(`${pokemonUsuario.nombre} se ha envenenado!`, 'cpu');
				if (primero === undefined) primero = false;
			}
			await sleep(1);
		}
	};

	//Metodo que es llamado cuando un pokemon ataca a otro con un ataque de tipo electrico
	const intentarParalizar = async name => {
		if (getRandom(10) > 7) {
			if (name === 'cpu') {
				pokemonCpu.estados.paralizado = true;
				$('#estadoCpu').html('PAR');
				$('#estadoCpu').css('background-color', 'rgb(255, 225, 0)');
				agregarAlHistorial(`${pokemonCpu.nombre} se ha paralizado!`);
				pokemonCpu.velocidad *= 0.5;
			} else {
				pokemonUsuario.estados.paralizado = true;
				$('#estadoUsuario').html('PAR');
				$('#estadoUsuario').css('background-color', 'rgb(255, 225, 0)');
				agregarAlHistorial(`${pokemonUsuario.nombre} se ha paralizado!`, 'cpu');
				pokemonUsuario.velocidad *= 0.5;
			}
			await sleep(1);
		}
	};

	//Metodo que es llamado cuando un pokemon ataca a otro con un ataque de tipo fuego
	const intentarQuemar = async name => {
		if (getRandom(10) > 7) {
			if (name === 'cpu') {
				pokemonCpu.estados.quemado = true;
				$('#estadoCpu').html('BRN');
				$('#estadoCpu').css('background-color', 'red');
				agregarAlHistorial(`${pokemonCpu.nombre} se ha quemado!`);
				$(pokemonCpu.ataques).each(function() {
					this.potencia *= 0.5;
				});
				if (primero === undefined) primero = true;
			} else {
				pokemonUsuario.estados.quemado = true;
				$('#estadoUsuario').html('BRN');
				$('#estadoUsuario').css('background-color', 'red');
				agregarAlHistorial(`${pokemonUsuario.nombre} se ha quemado!`, 'cpu');
				$(pokemonUsuario.ataques).each(function() {
					this.potencia *= 0.5;
				});
				if (primero === undefined) primero = false;
			}
			await sleep(1);
		}
	};

	//Metodo asincrono que mueve la barra de vida de los pokemons y me obliga a usar promesas para esperar que finalize
	const moveProgressBar = async (idProgressBar, idVida, pokemon, recover) => {
		await sleep(0.6);
		return new Promise(resolve => {
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
						elem.width(`${pokemon.width}%`);
						vidaAnterior += pokemon.porcVida;
						if (vidaAnterior < pokemon.vidaActual && vidaAnterior < pokemon.vida) $(idVida).html(parseInt(vidaAnterior));
						else if (pokemon.vidaActual < pokemon.vida) $(idVida).html(parseInt(pokemon.vidaActual));
						else $(idVida).html(pokemon.vida);
					}
				} else {
					if (vidaAnterior <= pokemon.vidaActual || vidaAnterior < 0) {
						clearInterval(interval);
						resolve();
					} else {
						pokemon.width -= 0.3;
						elem.width(`${pokemon.width}%`);
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
		interval = setInterval(() => {
			if (contador === 5) {
				clearInterval(interval);
			}
			imgObjetivo.css('visibility') === 'hidden' ? imgObjetivo.css('visibility', 'visible') : imgObjetivo.css('visibility', 'hidden');
			contador++;
		}, 150);
	};

	//Metodo que habilita los botones al finalizar el intercambio de daños
	const activarBotones = async () => {
		if (pokemonsVivosUsr > 0 && pokemonsVivosCpu > 0) {
			await sleep(0.5);
			$('.ataques').prop('disabled', false);
			$('.suplentes').each(function() {
				if (this.value !== botonCambio.value && this.value !== '-1') $(this).prop('disabled', false);
			});
			$('.objeto').prop('disabled', false);
			$('#ataqueUsuario').css('visibility', 'hidden');
			$('#ataqueCpu').css('visibility', 'hidden');
		}
	};

	const desactivarBotones = () => {
		$('.ataques').prop('disabled', true);
		$('.suplentes').prop('disabled', true);
		$('.objeto').prop('disabled', true);
	};

	const agregarAlHistorial = (texto, player) => {
		var timer = new Date().getTime() - startTimer;
		var time = `${`0${Math.floor(timer / 60000)}`.slice(-2)}:${`0${Math.floor((timer % 60000) / 1000)}`.slice(-2)}`;
		player === 'cpu' ?
			$('.historial').prepend(`<li style="color: red;">${time} - ${texto}</li>`) :
			$('.historial').prepend(`<li style="color: green;">${time} - ${texto}</li>`);

	};
	const getRandom = rango => Math.floor(Math.random() * rango) + 1;

	const sleep = async (segundos, fn, ...args) => {
		var delay = segundos * 1000;
		fn && fn(...args);
		await new Promise(resolve => setTimeout(resolve, delay));
	};

	const pokemonDebilitado = async objetivo => {
		if (objetivo === 'cpu') {
			agregarAlHistorial(`${pokemonCpu.nombre} enemigo ha sido debilitado!`);
			if (--pokemonsVivosCpu > 0) {
				await cambiarPokemonCpu();
			} else {
				var dialog = $('.game-over')[0];
				!musica.paused && musica.pause();
				sonidoVictoria.play();
				$('.modalV').attr('src', "images/victoria.png");
				dialog.showModal();
				ganador = 'user';
			}
		} else {
			agregarAlHistorial(`Tu ${pokemonUsuario.nombre} ha sido debilitado!`, 'cpu');
			if (--pokemonsVivosUsr > 0) {
				$('.suplentes').each(function() {
					if (this.value !== botonCambio.value && this.value !== '-1') $(this).prop('disabled', false);
					if (this.value === botonCambio.value) this.value = -1;
				});
			} else {
				var dialog = $('.game-over')[0];
				if (!musica.paused) musica.pause();
				sonidoDerrota.play();
				$('.modalV').attr('src', "images/derrota.png");
				dialog.showModal();
				ganador = 'cpu';
			}
		}
	};

	//Cambio de pokemon usuario
	const cambiarPokemonUsr = async (boton, debilitado) => {
		botonCambio = boton;
		pokemonUsuario = pokemonsUsuario[boton.value];
		await new Promise(resolve => {
			$(spriteUsuario).fadeOut(1000, () => {
				if (debilitado) agregarAlHistorial(`Enviaste a ${pokemonUsuario.nombre}`);
				else agregarAlHistorial(`Cambiaste a ${pokemonUsuario.nombre}`);
				spriteUsuario = $('.img-usuario')[boton.value];
				$('#nombrePkmnUsr').html(pokemonUsuario.nombre);
				$('#vidaPkmnUsr').html(parseInt(pokemonUsuario.vidaActual));
				$('#vidaMaximaPkmnUsr').html(` / ${pokemonUsuario.vida}`);
				$('#progressBarUsr').width(`${pokemonUsuario.width}%`);
				$('.ataques').each((i, iAtaque) => {
					var ataque = pokemonUsuario.ataques[i];
					$(iAtaque).html(ataque.nombre);
					$(iAtaque).prop('title',
						`Tipo: ${ataque.tipo} | Potencia: ${ataque.potencia}.0 | Precision: ${ataque.precataque}.0`);
				});
				$('.img-usuario').prop('title', `Tipo: ${pokemonUsuario.tipo}`);
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
		pokemonCpu = pokemonsCpu[++nextPokemonCpu];
		await new Promise(resolve => {
			$(spriteCpu).fadeOut(1000, () => {
				agregarAlHistorial(`El enemigo envio a ${pokemonCpu.nombre}`, 'cpu');
				spriteCpu = $('.img-cpu')[nextPokemonCpu];
				$('#nombrePkmnCpu').html(pokemonCpu.nombre);
				$('#vidaPkmnCpu').html(pokemonCpu.vidaActual);
				$('#vidaMaximaPkmnCpu').html(` / ${pokemonCpu.vida}`);
				$('#progressBarCpu').width(`${pokemonCpu.width}%`);
				$('#estadoCpu').html('');
				$('#estadoCpu').css('background-color', '');
				$('.img-cpu').prop('title', `Tipo: ${pokemonCpu.tipo}`);
				$(spriteCpu).fadeIn(1000, resolve);
			});
		});
	};

	$(document).on('click', '.continuar', () => {
		var duracion = new Date().getTime() - startTimer;
		var datosPokemons = [];
		$(pokemonsUsuario).each(function() {
			var pokemon = {
				id: this.id,
				debilitado: this.debilitado,
				entrenador: 'usuario'
			};
			datosPokemons.push(pokemon);
		});
		$(pokemonsCpu).each(function() {
			var pokemon = {
				id: this.id,
				debilitado: this.debilitado,
				entrenador: 'cpu'
			};
			datosPokemons.push(pokemon);
		});

		$.ajax({
			data: {
				datosPokemons: JSON.stringify(datosPokemons),
				duracion,
				objetosUtilizados: JSON.stringify(objetosUtilizados)
			},
			type: 'POST',
			url: 'final-batalla',
			success: result => {
				window.location.href = 'home';
			}
		});
	});

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
	$('#abrirMochila').click(() =>
		oculto ? $('.mochila').animate({ left: 0 }, 600, () => oculto = false) :
			$('.mochila').animate({ left: ocultaMochila }, 600, () => oculto = true)
	);

	$('#abrirMochila').hover(() => oculto && $('.mochila').animate({ left: 0 }, 600, () => oculto = false)
		, () => ocultarMochilaAuto());

	$('.mochila').mouseleave(() => ocultarMochilaAuto());

	const ocultarMochilaAuto = async () => {
		await sleep(1);
		if (!$('.mochila').is(':hover') && !$('#abrirMochila').is(':hover') && !oculto) {
			$('.mochila').animate({ left: ocultaMochila }, 600, () => oculto = true);
		}
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
		$(spriteUsuario).show();
		$(spriteCpu).show();
	});

	var reproducirDialog = $('.reproducir-dialog')[0];
	var musica = $('#musica')[0];
	var sonidoGolpe = $('#golpe')[0];
	var sonidoVictoria = $('#final')[0];
	var sonidoDerrota = $('#finalMalo')[0];
	var slider = $('#slider')[0];
	$(window).on('load', () => {
		musica.volume = slider.value * 0.002;
		sonidoGolpe.volume = slider.value * 0.008;
		sonidoVictoria.volume = slider.value * 0.004;
		sonidoDerrota.volume = slider.value * 0.008;
	});

	reproducirDialog.show();
	$('.yes').click(() => {
		reproducirDialog.close();
		playPauseMusica();
	});
	$('.no').click(() => reproducirDialog.close());
	$(slider).on('input', function() {
		musica.volume = this.value * 0.002;
		sonidoGolpe.volume = this.value * 0.008;
		sonidoVictoria.volume = this.value * 0.004;
		sonidoDerrota.volume = this.value * 0.008;
		console.log(`Volume set to ${this.value}`);
	});
	$(window).click(() => { if (!$(reproducirDialog).is(':hover') && reproducirDialog.open) reproducirDialog.close() });
	$('.reproducir').click(() => playPauseMusica());
	$(document).keypress(e => {
		if (e.key === 'p') {
			playPauseMusica();
			reproducirDialog.open && reproducirDialog.close();
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