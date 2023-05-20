$(document).ready(function() {

	var pokemonsUsuario;
	var pokemonsCpu;

	//Traigo los pokemons utilizando ajax
	$.ajax({
		type: 'POST',
		url: 'obtener-pokemons-ajax',
		async: false,
		/*contentType: false,
		processData: false,
		beforeSend: function() {
		},*/
		success: (result) => {
			pokemonsUsuario = result.pokemonsUsuario;
			pokemonsCpu = result.pokemonsCpu;
			$(pokemonsUsuario).each((i, pokemon) => {
				$(result.ataquesUsuario[pokemon.nombre]).each((j, ataques) => {
					pokemon.ataques.push(ataques.ataque);
				});
			});
			console.log(pokemonsUsuario);
			$(pokemonsCpu).each((i, pokemon) => {
				$(result.ataquesCpu[pokemon.nombre]).each((j, ataques) => {
					pokemon.ataques.push(ataques.ataque);
				});
			});
		}
		/*error: function(error){
		}*/
	});

	var pokemonUsuario = pokemonsUsuario[0];
	var pokemonCpu = pokemonsCpu[0];

	//Defino las variables que voy a usar en el combate
	var endGame = false;
	var primero;
	pokemonUsuario['porcVida'] = pokemonUsuario.vida * 0.003;
	pokemonCpu['porcVida'] = pokemonCpu.vida * 0.003;
	pokemonUsuario['width'] = 100;
	pokemonCpu['width'] = 100;
	pokemonUsuario['vidaActual'] = pokemonUsuario.vida;
	pokemonCpu['vidaActual'] = pokemonCpu.vida;
	pokemonUsuario['estados'] = { envenenado: false, paralizado: false, quemado: false };
	pokemonCpu['estados'] = { envenenado: false, paralizado: false, quemado: false };
	pokemonUsuario['danioPorEstado'] = pokemonUsuario.vida * 0.08;
	pokemonCpu['danioPorEstado'] = pokemonCpu.vida * 0.08;

	//Coloco la vida de los pokemons en html porque sino hay que hacer un cast double to int en jsp
	$('#vidaPkmnUsr').html(pokemonUsuario.vida);
	$('#vidaPkmnCpu').html(pokemonCpu.vida);
	$('#vidaMaximaPkmnUsr').html(' / ' + pokemonUsuario.vida);
	$('#vidaMaximaPkmnCpu').html(' / ' + pokemonCpu.vida);

	//Detecto si el usuario presiona un ataque y comienza el intercambio de daño
	$('#ataques').on('click', 'button', async function() {

		$('.ataques').prop('disabled', true);

		await realizarAtaques(this.value);
		await efectosDeEstado();
		activarBotones();
	});

	//Hago el intercambio de ataques entre el pokemon del usuario y la cpu
	async function realizarAtaques(idAtaqueUsuario) {
		if (pokemonUsuario.velocidad > pokemonCpu.velocidad) {
			await ataqueUsuario(idAtaqueUsuario);
			await ataqueCpu();
		}
		else {
			await ataqueCpu();
			await ataqueUsuario(idAtaqueUsuario);
		}
	};

	//Metodo que ejecuta el ataque del usuario
	const ataqueUsuario = async (idAtaque) => {
		if (!endGame) {
			var inmovil = efectoPorParalisis(pokemonUsuario.estados.paralizado);
			if (!inmovil) {
				var potencia = pokemonUsuario.ataques[idAtaque].potencia;
				var tipo = pokemonUsuario.ataques[idAtaque].tipo;
				if (tipo == pokemonUsuario.tipo) potencia *= 1.5;
				pokemonCpu.vidaActual -= potencia;
				$('#ataqueUsuario').html('Utilizaste: ' + pokemonUsuario.ataques[idAtaque].nombre);
				$('#ataqueUsuario').css('visibility', 'visible');
				await moveProgressBar('#progressBarCpu', '#vidaPkmnCpu', pokemonCpu);
				if (!pokemonCpu.estados.envenenado && !pokemonCpu.estados.paralizado && !pokemonCpu.estados.quemado) {
					//ENVENENAR
					if (tipo == 'VENENO') intentarEnvenenar('cpu');
					//PARALISIS
					else if (tipo == 'ELECTRICO') intentarParalizar('cpu');
					//QUEMAR
					else if (tipo == 'FUEGO') intentarQuemar('cpu');
				}
			}
			else {
				$('#ataqueUsuario').html('Estas paralizado, no puedes atacar!');
				$('#ataqueUsuario').css('visibility', 'visible');
				return new Promise((resolve) => setTimeout(resolve, 1000));
			}
		}
		else $('#vidaPkmnUsr').html('Perdiste :C');
	};

	//Metodo que ejecuta el ataque de la cpu
	const ataqueCpu = async () => {
		if (!endGame) {
			var inmovil = efectoPorParalisis(pokemonCpu.estados.paralizado);
			if (!inmovil) {
				var ataque = Math.floor(Math.random() * pokemonCpu.ataques.length);
				var tipo = pokemonCpu.ataques[ataque].tipo;
				var potencia = pokemonCpu.ataques[ataque].potencia;
				if (tipo == pokemonCpu.tipo) potencia *= 1.5;
				pokemonUsuario.vidaActual -= potencia;
				$('#ataqueCpu').html('Ataque enemigo: ' + pokemonCpu.ataques[ataque].nombre);
				$('#ataqueCpu').css('visibility', 'visible');
				await moveProgressBar('#progressBarUsr', '#vidaPkmnUsr', pokemonUsuario);
				if (!pokemonUsuario.estados.envenenado && !pokemonUsuario.estados.paralizado && !pokemonUsuario.estados.quemado) {
					//ENVENENAR
					if (tipo == 'VENENO') intentarEnvenenar('user');
					//PARALISIS
					else if (tipo == 'ELECTRICO') intentarParalizar('user');
					//QUEMAR
					else if (tipo == 'FUEGO') intentarQuemar('user');
				}
			}
			else {
				$('#ataqueCpu').html('Enemigo paralizado, no puede atacar!');
				$('#ataqueCpu').css('visibility', 'visible');
				return new Promise((resolve) => setTimeout(resolve, 1000));
			}
		}
		else $('#vidaPkmnUsr').html('Ganaste C:');
	};

	//Metodo que verifica si un pokemon se encuentra afectado por un efecto de estado
	const efectosDeEstado = async () => {
		if (primero) {
			if (pokemonCpu.estados.envenenado && pokemonUsuario.estados.envenenado || pokemonCpu.estados.quemado && pokemonUsuario.estados.quemado) {
				await danioPorEstado('cpu');
				await danioPorEstado('user');
			}
			else if (pokemonCpu.estados.envenenado || pokemonCpu.estados.quemado) await danioPorEstado('cpu');
		}
		else if (!primero) {
			if (pokemonCpu.estados.envenenado && pokemonUsuario.estados.envenenado || pokemonCpu.estados.quemado && pokemonUsuario.estados.quemado) {
				await danioPorEstado('user');
				await danioPorEstado('cpu');
			}
			else if (pokemonUsuario.estados.envenenado || pokemonUsuario.estados.quemado) await danioPorEstado('user');
		}
	}

	//Metodo que es llamado si el pokemon está paralizado
	const efectoPorParalisis = (paralizado) => {
		if (paralizado) {
			var chanceDeNoAtacar = Math.floor(Math.random() * 100) + 1;
			if (chanceDeNoAtacar > 75) return true;
		}
		return false;
	}

	//Metodo que es llamado cuando quiero aplicar el daño de un efecto de estado
	const danioPorEstado = async (objetivo) => {
		if (!endGame) {
			if (objetivo == 'cpu') {
				pokemonCpu.vidaActual -= pokemonCpu.danioPorEstado;
				await moveProgressBar('#progressBarCpu', '#vidaPkmnCpu', pokemonCpu);
			}
			else {
				pokemonUsuario.vidaActual -= pokemonUsuario.danioPorEstado;
				await moveProgressBar('#progressBarUsr', '#vidaPkmnUsr', pokemonUsuario);
			}
		}
		else if (pokemonCpu.vidaActual <= 0) $('#vidaPkmnUsr').html('Ganaste C:');
		else $('#vidaPkmnUsr').html('Perdiste :C');
	};

	//Metodo que es llamado cuando un pokemon ataca a otro con un ataque de tipo veneno
	const intentarEnvenenar = (name) => {
		var random;
		random = Math.floor(Math.random() * 10) + 1;
		if (random > 7) {
			if (name == 'cpu') {
				pokemonCpu.estados.envenenado = true;
				$('#estadoCpu').html('Poisoned');
				if (primero == undefined) primero = true;
			}
			else if (name == 'user') {
				pokemonUsuario.estados.envenenado = true;
				$('#estadoUsuario').html('Poisoned');
				if (primero == undefined) primero = false;
			}
		}
	};

	//Metodo que es llamado cuando un pokemon ataca a otro con un ataque de tipo electrico
	const intentarParalizar = (name) => {
		var random;
		random = Math.floor(Math.random() * 10) + 1;
		if (random > 7) {
			if (name == 'cpu') {
				pokemonCpu.estados.paralizado = true;
				$('#estadoCpu').html('Paralized');
				pokemonCpu.velocidad *= 0.5;
			}
			else if (name == 'user') {
				pokemonUsuario.estados.paralizado = true;
				$('#estadoUsuario').html('Paralized');
				pokemonUsuario.velocidad *= 0.5;
			}
		}
	}

	//Metodo que es llamado cuando un pokemon ataca a otro con un ataque de tipo fuego
	const intentarQuemar = (name) => {
		var random;
		random = Math.floor(Math.random() * 10) + 1;
		if (random > 7) {
			if (name == 'cpu') {
				pokemonCpu.estados.quemado = true;
				$('#estadoCpu').html('Burned');
				$(pokemonCpu.ataques).each(function() {
					this.potencia *= 0.5;
				});
				if (primero == undefined) primero = true;
			}
			else if (name == 'user') {
				pokemonUsuario.estados.quemado = true;
				$('#estadoUsuario').html('Burned');
				$(pokemonUsuario.ataques).each(function() {
					this.potencia *= 0.5;
				});
				if (primero == undefined) primero = false;
			}
		}
	}

	//Metodo asincrono que mueve la barra de vida de los pokemons y me obliga a usar promesas para esperar que finalize
	const moveProgressBar = (idProgressBar, idVida, pokemon) => {
		return new Promise((resolve) => {
			setTimeout(() => {
				var elem = $(idProgressBar);
				var interval = setInterval(frame, 10);
				var vidaAnterior = $(idVida).html();
				function frame() {
					if (vidaAnterior <= pokemon.vidaActual) {
						clearInterval(interval);
						resolve();
					}
					else {
						pokemon.width -= 0.3;
						elem.width(pokemon.width + '%');
						vidaAnterior -= pokemon.porcVida;
						if (vidaAnterior > pokemon.vidaActual && vidaAnterior > 0) $(idVida).html(parseInt(vidaAnterior));
						else if (pokemon.vidaActual > 0) $(idVida).html(parseInt(pokemon.vidaActual));
						else {
							$(idVida).html(0);
							endGame = true;
						}
					}
				}
			}, 500);
		});
	};

	//Metodo que habilita los botones al finalizar el intercambio de daños
	const activarBotones = () => {
		if (!endGame) {
			setTimeout(() => {
				$('.ataques').prop('disabled', false);
				$('#ataqueUsuario').css('visibility', 'hidden');
				$('#ataqueCpu').css('visibility', 'hidden');

			}, 500);
		}
		else {
			if (pokemonCpu.vidaActual <= 0) $('#vidaPkmnUsr').html('Ganaste C:');
			else $('#vidaPkmnUsr').html('Perdiste :C');
		}
	};

	/**
	 * 	Estilos y animaciones de la vista de batalla
	 */

	
	//Comportamiento de la mochila de objetos
	var ocultaMochila = '-' + $('.mochila').css('width');
	var oculto = true;
	$('.mochila').css('left', ocultaMochila);
	$('#botonPruebas').click(() => {
		if (oculto) {
			$('.mochila').animate({ left: 0 }, 600, () => { oculto = false; });
		}
		else {
			$('.mochila').animate({ left: ocultaMochila }, 600, () => { oculto = true; });
		}
	});

	$('#botonPruebas').hover(() => {
		if (oculto) {
			$('.mochila').animate({ left: 0 }, 600, () => { oculto = false; });
		}
	}, ocultarMochilaAuto);

	$('.mochila').mouseleave(ocultarMochilaAuto);

	function ocultarMochilaAuto() {
		setTimeout(() => {
			if (!$('.mochila').is(':hover') && !$('#botonPruebas').is(':hover') && !oculto) {
				$('.mochila').animate({ left: ocultaMochila }, 600, () => { oculto = true; });
			}
		}, 1000);
	}


	//Comportamiento de la mochila de objetos
	var ocultaMochila = '-' + $('.mochila').css('width');
	var oculto = true;
	$('.mochila').css('left', ocultaMochila);
	$('#abrirMochila').click(() => {
		if (oculto) {
			$('.mochila').animate({ left: 0 }, 600, () => { oculto = false; });
		}
		else {
			$('.mochila').animate({ left: ocultaMochila }, 600, () => { oculto = true; });
		}
	});

	$('#abrirMochila').hover(() => {
		if (oculto) {
			$('.mochila').animate({ left: 0 }, 600, () => { oculto = false; });
		}
	}, ocultarMochilaAuto);

	$('.mochila').mouseleave(ocultarMochilaAuto);

	function ocultarMochilaAuto() {
		setTimeout(() => {
			if (!$('.mochila').is(':hover') && !$('#abrirMochila').is(':hover') && !oculto) {
				$('.mochila').animate({ left: ocultaMochila }, 600, () => { oculto = true; });
			}
		}, 1000);
	}

	$(window).on('load', () => {
		$('.img-suplente').each(function() {
			var width = $(this).prop('width') * 0.6;
			$(this).prop('width', width);
		});
	});

});