$(document).ready(function() {

	var pokemonUsuario;
	var pokemonCpu;

	//Traigo los pokemons utilizando ajax
	$.ajax({
		type: 'POST',
		url: "obtener-pokemons-ajax",
		async: false,
		//contentType: false,
		//processData: false,
		/*/beforeSend: function() {
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
	var danioUsr = pokemonUsuario.vida * 0.08;
	var danioCpu = pokemonCpu.vida * 0.08;
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
		await realizarAtaques(this.id); //Await espera a que se hagan los ataques sino hace todo de una
		await efectosDeEstado();
		activarBotones();
	});
	//para llamar a AWAIT Necesito que la funcion sea async
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
			var potencia = pokemonUsuario.ataques[idAtaque].potencia;
			var tipo = pokemonUsuario.ataques[idAtaque].tipo;
			if (tipo == pokemonUsuario.tipo) potencia *= 1.5;
			vidaPkmnCpu -= potencia;
			$("#ataqueUsuario").html("Utilizaste: " + pokemonUsuario.ataques[idAtaque].nombre);
			$("#ataqueUsuario").css('visibility', 'visible');
			if (!estadosCpu.envenenado && tipo == "VENENO") intentarEnvenenar("cpu");
			await moveProgressBar(porcVidaPkmnCpu, vidaPkmnCpu, "#progressBarCpu", "#vidaPkmnCpu", widthCpuBar);
		}
		else $("#vidaPkmnCpu").html("Perdiste :C");
	};

	//Metodo que ejecuta el ataque de la cpu
	const ataqueCpu = async () => {
		if (!endGame) {
			var ataque = Math.floor(Math.random() * pokemonCpu.ataques.length);
			var tipo = pokemonCpu.ataques[ataque].tipo;
			var potencia = pokemonCpu.ataques[ataque].potencia;
			if (tipo == pokemonCpu.tipo) potencia *= 1.5;
			vidaPkmnUsr -= potencia;
			$("#ataqueCpu").html("Ataque enemigo: " + pokemonCpu.ataques[ataque].nombre);
			$("#ataqueCpu").css('visibility', 'visible');
			if (!estadosUsr.envenenado && tipo == "VENENO") intentarEnvenenar("user");
			await moveProgressBar(porcVidaPkmnUsr, vidaPkmnUsr, "#progressBarUsr", "#vidaPkmnUsr", widthUsrBar);
		}
		else $("#vidaPkmnUsr").html("Ganaste C:");
	};

	//Metodo que verifica si un pokemon se encuentra afectado por un efecto de estado
	const efectosDeEstado = async () => {
		widthCpuBar = vidaPkmnCpu / pokemonCpu.vida * 100;
		widthUsrBar = vidaPkmnUsr / pokemonUsuario.vida * 100;
		if (primero) {
			if (estadosCpu.envenenado && estadosUsr.envenenado) {
				await danioPorEstado("cpu", danioCpu);
				await danioPorEstado("user", danioUsr);
			}
			else if (estadosCpu.envenenado) await danioPorEstado("cpu", danioCpu);
		}
		else if (!primero) {
			if (estadosCpu.envenenado && estadosUsr.envenenado) {
				await danioPorEstado("user", danioUsr);
				await danioPorEstado("cpu", danioCpu);
			}
			else if (estadosUsr.envenenado) await danioPorEstado("user", danioUsr);
		}
	}

	//Metodo que es llamado cuando quiero aplicar el daño de un efecto de estado
	const danioPorEstado = async (objetivo, potencia) => {
		if (!endGame) {
			var porcentajeARestar;
			if (objetivo == "cpu") {
				vidaPkmnCpu -= potencia;
				porcentajeARestar = pokemonCpu.vida * 0.003;
				await moveProgressBar(porcentajeARestar, vidaPkmnCpu, "#progressBarCpu", "#vidaPkmnCpu", widthCpuBar);
			}
			else {
				vidaPkmnUsr -= potencia;
				porcentajeARestar = pokemonUsuario.vida * 0.003;
				await moveProgressBar(porcentajeARestar, vidaPkmnUsr, "#progressBarUsr", "#vidaPkmnUsr", widthUsrBar);
			}
		}
		else if (vidaPkmnCpu <= 0) $("#vidaPkmnUsr").html("Ganaste C:");
		else $("#vidaPkmnCpu").html("Perdiste :C");
	};

	//Metodo que es llamado cuando un pokemon ataca a otro con un ataque de tipo veneno
	const intentarEnvenenar = name => {
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

	//Metodo asincrona que mueve la barra de vida de los pokemons y me obliga a usar promesas para esperar que finalize
	const moveProgressBar = (porcentajeARestar, vidaActual, idProgressBar, idVida, width) => {
		return new Promise(resolve => {
			setTimeout(() => {
				var elem = $(idProgressBar);
				var interval = setInterval(frame, 10);
				var vidaAnterior = $(idVida).html();		//sin el promise se seguiria ejecutando todo lo abajo
				function frame() {							//cuando tira el resolve, el await ahi dice CHETO termino, me ejecuto
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
				else $("#vidaPkmnCpu").html("Perdiste :C");
			}
		}, 500)
	};
});