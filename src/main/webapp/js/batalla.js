$(document).ready(function() {

	var pokemonUsuario;
	var pokemonCpu;

	//Traigo los pokemons utilizando ajax
	$.ajax({
		type: 'POST',
		url: "obtener-pokemons-ajax",
		async: false,
		/*contentType: false,
		processData: false,
		beforeSend: function() {
		},*/
		success: (result) => {
			pokemonUsuario = result.pokemonUsuario;
			pokemonCpu = result.pokemonCpu;
			$(result.ataquesUsuario).each(function() {
				pokemonUsuario.ataques.push(this.ataque);
			});
			$(result.ataquesCpu).each(function() {
				pokemonCpu.ataques.push(this.ataque);
			});
		}
		/*error: function(error){
		}*/
	});

	//Defino las variables que voy a usar en el combate
	var endGame = false;
	var estadosUsr = { envenenado: false, paralizado: false, quemado: false };
	var estadosCpu = { envenenado: false, paralizado: false, quemado: false };
	var primero;
	var porcVidaPkmnUsr = pokemonUsuario.vida * 0.003;
	var porcVidaPkmnCpu = pokemonCpu.vida * 0.003;
	var vidaPkmnCpu = pokemonCpu.vida;
	var vidaPkmnUsr = pokemonUsuario.vida;
	var widthUsrBar;
	var widthCpuBar;
	var danioPorEstadoUsr = pokemonUsuario.vida * 0.08;
	var danioPorEstadoCpu = pokemonCpu.vida * 0.08;
	//Coloco la vida de los pokemons en html porque sino hay que hacer un cast double to int en jsp
	$("#vidaPkmnUsr").html(vidaPkmnUsr);
	$("#vidaPkmnCpu").html(vidaPkmnCpu);
	$("#vidaMaximaPkmnUsr").html(" / " + vidaPkmnUsr);
	$("#vidaMaximaPkmnCpu").html(" / " + vidaPkmnCpu);

	//Detecto si el usuario presiona un ataque y comienza el intercambio de daño
	$("#ataques").on("click", "button", async function() {
		widthUsrBar = vidaPkmnUsr / pokemonUsuario.vida * 100;
		widthCpuBar = vidaPkmnCpu / pokemonCpu.vida * 100;

		$(".ataques").prop('disabled', true);

		await realizarAtaques(this.id);
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
			var inmovil = efectoPorParalisis(estadosUsr.paralizado);
			if (!inmovil) {
				var potencia = pokemonUsuario.ataques[idAtaque].potencia;
				var tipo = pokemonUsuario.ataques[idAtaque].tipo;
				if (tipo == pokemonUsuario.tipo) potencia *= 1.5;
				vidaPkmnCpu -= potencia;
				$("#ataqueUsuario").html("Utilizaste: " + pokemonUsuario.ataques[idAtaque].nombre);
				$("#ataqueUsuario").css('visibility', 'visible');
				await moveProgressBar(porcVidaPkmnCpu, vidaPkmnCpu, "#progressBarCpu", "#vidaPkmnCpu", widthCpuBar);
				if (!estadosCpu.envenenado && !estadosCpu.paralizado && !estadosCpu.quemado) {
					//ENVENENAR
					if (tipo == "VENENO") intentarEnvenenar("cpu");
					//PARALISIS
					else if (tipo == "ELECTRICO") intentarParalizar("cpu");
					//QUEMAR
					else if (tipo == "FUEGO") intentarQuemar("cpu");
				}
			}
			else {
				$("#ataqueUsuario").html("Estas paralizado, no puedes atacar!");
				$("#ataqueUsuario").css('visibility', 'visible');
				return new Promise((resolve) => setTimeout(resolve, 1000));
			}
		}
		else $("#vidaPkmnUsr").html("Perdiste :C");
	};

	//Metodo que ejecuta el ataque de la cpu
	const ataqueCpu = async () => {
		if (!endGame) {
			var inmovil = efectoPorParalisis(estadosCpu.paralizado);
			if (!inmovil) {
				var ataque = Math.floor(Math.random() * pokemonCpu.ataques.length);
				var tipo = pokemonCpu.ataques[ataque].tipo;
				var potencia = pokemonCpu.ataques[ataque].potencia;
				if (tipo == pokemonCpu.tipo) potencia *= 1.5;
				vidaPkmnUsr -= potencia;
				$("#ataqueCpu").html("Ataque enemigo: " + pokemonCpu.ataques[ataque].nombre);
				$("#ataqueCpu").css('visibility', 'visible');
				await moveProgressBar(porcVidaPkmnUsr, vidaPkmnUsr, "#progressBarUsr", "#vidaPkmnUsr", widthUsrBar);
				if (!estadosUsr.envenenado && !estadosUsr.paralizado && !estadosUsr.quemado) {
					//ENVENENAR
					if (tipo == "VENENO") intentarEnvenenar("user");
					//PARALISIS
					else if (tipo == "ELECTRICO") intentarParalizar("user");
					//QUEMAR
					else if (tipo == "FUEGO") intentarQuemar("user");
				}
			}
			else {
				$("#ataqueCpu").html("Enemigo paralizado, no puede atacar!");
				$("#ataqueCpu").css('visibility', 'visible');
				return new Promise((resolve) => setTimeout(resolve, 1000));
			}
		}
		else $("#vidaPkmnUsr").html("Ganaste C:");
	};

	//Metodo que verifica si un pokemon se encuentra afectado por un efecto de estado
	const efectosDeEstado = async () => {
		widthCpuBar = vidaPkmnCpu / pokemonCpu.vida * 100;
		widthUsrBar = vidaPkmnUsr / pokemonUsuario.vida * 100;
		if (primero) {
			if (estadosCpu.envenenado && estadosUsr.envenenado || estadosCpu.quemado && estadosUsr.quemado) {
				await danioPorEstado("cpu", danioPorEstadoCpu);
				await danioPorEstado("user", danioPorEstadoUsr);
			}
			else if (estadosCpu.envenenado || estadosCpu.quemado) {
				await danioPorEstado("cpu", danioPorEstadoCpu);
			}
		}
		else if (!primero) {
			if (estadosCpu.envenenado && estadosUsr.envenenado || estadosCpu.quemado && estadosUsr.quemado) {
				await danioPorEstado("user", danioPorEstadoUsr);
				await danioPorEstado("cpu", danioPorEstadoCpu);
			}
			else if (estadosUsr.envenenado || estadosUsr.quemado) await danioPorEstado("user", danioPorEstadoUsr);
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
	const danioPorEstado = async (objetivo, potencia) => {
		if (!endGame) {
			if (objetivo == "cpu") {
				vidaPkmnCpu -= potencia;
				await moveProgressBar(porcVidaPkmnCpu, vidaPkmnCpu, "#progressBarCpu", "#vidaPkmnCpu", widthCpuBar);
			}
			else {
				vidaPkmnUsr -= potencia;
				await moveProgressBar(porcVidaPkmnUsr, vidaPkmnUsr, "#progressBarUsr", "#vidaPkmnUsr", widthUsrBar);
			}
		}
		else if (vidaPkmnCpu <= 0) $("#vidaPkmnUsr").html("Ganaste C:");
		else $("#vidaPkmnUsr").html("Perdiste :C");
	};

	//Metodo que es llamado cuando un pokemon ataca a otro con un ataque de tipo veneno
	const intentarEnvenenar = (name) => {
		var random;
		random = Math.floor(Math.random() * 10) + 1;
		if (random > 7) {
			if (name == "cpu") {
				estadosCpu.envenenado = true;
				$("#estadoCpu").html("Poisoned");
				if (primero == undefined) primero = true;
			}
			else if (name == "user") {
				estadosUsr.envenenado = true;
				$("#estadoUsuario").html("Poisoned");
				if (primero == undefined) primero = false;
			}
		}
	};

	//Metodo que es llamado cuando un pokemon ataca a otro con un ataque de tipo electrico
	const intentarParalizar = (name) => {
		var random;
		random = Math.floor(Math.random() * 10) + 1;
		if (random > 7) {
			if (name == "cpu") {
				estadosCpu.paralizado = true;
				$("#estadoCpu").html("Paralized");
				pokemonCpu.velocidad *= 0.5;
			}
			else if (name == "user") {
				estadosUsr.paralizado = true;
				$("#estadoUsuario").html("Paralized");
				pokemonUsuario.velocidad *= 0.5;
			}
		}
	}

	//Metodo que es llamado cuando un pokemon ataca a otro con un ataque de tipo fuego
	const intentarQuemar = (name) => {
		var random;
		random = Math.floor(Math.random() * 10) + 1;
		if (random > 7) {
			if (name == "cpu") {
				estadosCpu.quemado = true;
				$("#estadoCpu").html("Burned");
				$(pokemonCpu.ataques).each(function() {
					this.potencia *= 0.5;
				});
				if (primero == undefined) primero = true;
			}
			else if (name == "user") {
				estadosUsr.quemado = true;
				$("#estadoUsuario").html("Burned");
				$(pokemonUsuario.ataques).each(function() {
					this.potencia *= 0.5;
				});
				if (primero == undefined) primero = false;
			}
		}
	}

	//Metodo asincrona que mueve la barra de vida de los pokemons y me obliga a usar promesas para esperar que finalize
	const moveProgressBar = (porcentajeARestar, vidaActual, idProgressBar, idVida, width) => {
		return new Promise((resolve) => {
			setTimeout(() => {
				var elem = $(idProgressBar);
				var interval = setInterval(frame, 10);
				var vidaAnterior = $(idVida).html();
				function frame() {
					if (vidaAnterior <= vidaActual) {
						clearInterval(interval);
						resolve();
					}
					else {
						width -= 0.3;
						elem.width(width + "%");
						vidaAnterior -= porcentajeARestar;
						if (vidaAnterior > vidaActual && vidaAnterior > 0) $(idVida).html(parseInt(vidaAnterior));
						else if (vidaActual > 0) $(idVida).html(parseInt(vidaActual));
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
		setTimeout(() => {
			if (!endGame) {
				$(".ataques").prop('disabled', false);
				$("#ataqueUsuario").css('visibility', 'hidden');
				$("#ataqueCpu").css('visibility', 'hidden');
			}
			else {
				if (vidaPkmnCpu <= 0) $("#vidaPkmnUsr").html("Ganaste C:");
				else $("#vidaPkmnUsr").html("Perdiste :C");
			}
		}, 500)
	};
});