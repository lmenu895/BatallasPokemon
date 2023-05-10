$(document).ready(function() {

	$.ajax({
		type: 'POST',
		url: "./obtener-pokemons-ajax",
		async: false,
		//contentType: false,
		//processData: false,
		/*/beforeSend: function() {
		},*/
		success: function(resultado) {
			pokemonUsuario = resultado.pokemonUsuario
			pokemonCpu = resultado.pokemonCpu
		}
		/*error: function(error){
		}*/
	})

	var pokemonUsuario
	var pokemonCpu
	var endGame = false
	var estadosUsr = { envenenado: false, paralizado: false }
	var estadosCpu = { envenenado: false, paralizado: false }
	var primero
	var vidaPkmnCpu = pokemonCpu.vida
	var vidaPkmnUsr = pokemonUsuario.vida
	$("#vidaPkmnUsr").html(vidaPkmnUsr)
	$("#vidaPkmnCpu").html(vidaPkmnCpu)
	$("#vidaMaximaPkmnUsr").html(" / " + vidaPkmnUsr)
	$("#vidaMaximaPkmnCpu").html(" / " + vidaPkmnCpu)


	$("#ataques").on("click", "button", function() {
		var widthUsrBar = vidaPkmnUsr * 100 / pokemonUsuario.vida;
		var widthCpuBar = vidaPkmnCpu * 100 / pokemonCpu.vida;
		const atacaUsuario = (x, y) => { ataqueUsuario(widthCpuBar, this.id, x, y) }
		const atacaCpu = (x, y) => { ataqueCpu(widthUsrBar, x, y) }

		$(".ataques").prop('disabled', true)
		var velocidad = Math.floor(Math.random() * 2)

		if (velocidad == 1) {
			atacaUsuario(atacaCpu, efectosDeEstado)
		}
		else {
			atacaCpu(atacaUsuario, efectosDeEstado)
		}
	})

	const ataqueUsuario = (widthCpuBar, ataque, siguiente, subsiguiente) => {
		if (!endGame) {
			var potencia = pokemonUsuario.ataques[ataque].potencia
			var tipo = pokemonUsuario.ataques[ataque].tipo
			if (tipo == pokemonUsuario.tipo)
				potencia *= 1.5
			var porcentajeDeVida
			var porcentajeARestar = pokemonCpu.vida * 0.003
			vidaPkmnCpu -= potencia
			porcentajeDeVida = vidaPkmnCpu * 100 / pokemonCpu.vida
			moveProgressBar(porcentajeDeVida, porcentajeARestar, vidaPkmnCpu, "#progressBarCpu", "#vidaPkmnCpu", widthCpuBar, siguiente, subsiguiente)
			$("#ataqueUsuario").html("Utilizaste: " + pokemonUsuario.ataques[ataque].nombre)
			$("#ataqueUsuario").css('visibility', 'visible')
			if (!estadosCpu.envenenado && tipo == "VENENO") {
				envenenado("cpu")
			}
		}
		else
			$("#vidaPkmnCpu").html("Perdiste :C")
	}

	const ataqueCpu = (widthUsrBar, siguiente, subsiguiente) => {
		if (!endGame) {
			var ataque = Math.floor(Math.random() * pokemonCpu.ataques.length)
			var tipo = pokemonCpu.ataques[ataque].tipo
			var potencia = pokemonCpu.ataques[ataque].potencia
			if (tipo == pokemonCpu.tipo)
				potencia *= 1.5
			var porcentajeDeVida
			var porcentajeARestar = pokemonUsuario.vida * 0.003
			vidaPkmnUsr -= potencia
			porcentajeDeVida = vidaPkmnUsr * 100 / pokemonUsuario.vida
			moveProgressBar(porcentajeDeVida, porcentajeARestar, vidaPkmnUsr, "#progressBarUsr", "#vidaPkmnUsr", widthUsrBar, siguiente, subsiguiente)
			$("#ataqueCpu").html("Ataque enemigo: " + pokemonCpu.ataques[ataque].nombre)
			$("#ataqueCpu").css('visibility', 'visible')
			if (!estadosUsr.envenenado && tipo == "VENENO") {
				envenenado("user")
			}
		}
		else
			$("#vidaPkmnUsr").html("Ganaste C:")
	}

	const efectosDeEstado = () => {
		var widthBarCpu = vidaPkmnCpu * 100 / pokemonCpu.vida;
		var widthBarUsr = vidaPkmnUsr * 100 / pokemonUsuario.vida;
		var danioUsr = pokemonUsuario.vida * 8 / 100
		var danioCpu = pokemonCpu.vida * 8 / 100
		var daniarUsr = (x, y) => { danioPorEstado("user", widthBarUsr, danioUsr, x, y) }
		var daniarCpu = (x, y) => { danioPorEstado("cpu", widthBarCpu, danioCpu, x, y) }
		if (primero) {
			if (estadosCpu.envenenado && estadosUsr.envenenado)
				daniarCpu(daniarUsr, enableButtons)
			else if (estadosCpu.envenenado)
				daniarCpu(enableButtons)
			else enableButtons()
		}
		else if (!primero) {
			if (estadosCpu.envenenado && estadosUsr.envenenado)
				daniarUsr(daniarCpu, enableButtons)
			else if (estadosUsr.envenenado)
				daniarUsr(enableButtons)
			else enableButtons()
		}
	}

	const danioPorEstado = (objetivo, width, potencia, siguiente, subsiguiente) => {
		if (!endGame) {
			var porcentajeDeVida
			var porcentajeARestar
			if (objetivo == "cpu") {
				vidaPkmnCpu -= potencia
				porcentajeDeVida = vidaPkmnCpu * 100 / pokemonCpu.vida
				porcentajeARestar = pokemonCpu.vida * 0.003
				moveProgressBar(porcentajeDeVida, porcentajeARestar, vidaPkmnCpu, "#progressBarCpu", "#vidaPkmnCpu", width, siguiente, subsiguiente)
			}
			else {
				vidaPkmnUsr -= potencia
				porcentajeDeVida = vidaPkmnUsr * 100 / pokemonUsuario.vida
				porcentajeARestar = pokemonUsuario.vida * 0.003
				moveProgressBar(porcentajeDeVida, porcentajeARestar, vidaPkmnUsr, "#progressBarUsr", "#vidaPkmnUsr", width, siguiente, subsiguiente)
			}
		}
		else if (vidaPkmnCpu <= 0) $("#vidaPkmnUsr").html("Ganaste C:")
		else $("#vidaPkmnCpu").html("Perdiste :C")
	}

	const envenenado = (name) => {
		var random;
		random = Math.floor(Math.random() * 10) + 1
		if (random > 7) {
			if (name == "cpu") {
				estadosCpu.envenenado = true
				$("#estadoCpu").html("Poisoned")
				if (primero == undefined)
					primero = true
			}
			else if (name == "user") {
				estadosUsr.envenenado = true
				$("#estadoUsuario").html("Poisoned")
				if (primero == undefined)
					primero = false
			}

		}
		else
			return 0
	}

	const moveProgressBar = (porcentajeDeVida, porcentajeARestar, vidaActual, idProgressBar, idVida, width, siguiente, subsiguiente) => {
		setTimeout(() => {
			var elem = $(idProgressBar);
			var interval = setInterval(frame, 10);
			var vidaAnterior = $(idVida).html()
			function frame() {
				if (vidaAnterior <= vidaActual) {
					clearInterval(interval)
					if (siguiente && subsiguiente) siguiente(subsiguiente)
					else if (siguiente) siguiente()
				} else {
					width -= 0.3;
					elem.width(width + "%")
					vidaAnterior -= porcentajeARestar
					if (vidaAnterior > vidaActual && vidaAnterior > 0)
						$(idVida).html(parseInt(vidaAnterior))
					else if (vidaActual > 0)
						$(idVida).html(parseInt(vidaActual))
					else {
						$(idVida).html(0)
						endGame = true
					}
				}
			}
		}, 500)
	}

	const enableButtons = () => {
		setTimeout(() => {
			if (!endGame) {
				$(".ataques").prop('disabled', false)
				$("#ataqueUsuario").css('visibility', 'hidden')
				$("#ataqueCpu").css('visibility', 'hidden')
			}
			else {
				if (vidaPkmnCpu <= 0) $("#vidaPkmnUsr").html("Ganaste C:")
				else $("#vidaPkmnCpu").html("Perdiste :C")
			}
		}, 500)
	}

	//	const esperando = (funcion, esSegundo) => {
	//		var interval = setInterval(function() {
	//			if (waiting) return
	//			clearInterval(interval)
	//			if (esSegundo) segundoMove = true
	//			waiting = true
	//			funcion()
	//			interval = setInterval(function() {
	//				if (waiting) return
	//				clearInterval(interval)
	//				waiting = true
	//			}, 10)
	//		}, 10)
	//	}

	//	function loading() {
	//		var interval = setInterval(function() {
	//			// get elem
	//			if (continuar != 0) {
	//				clearInterval(interval)
	//				console.log("loading finished " + continuar)
	//				continuar = 0
	//				return true
	//			}
	//		}, 10)
	//		return false
	//	}


	//	$.ajax({
	//            data:  {datos: datos},
	//            type:  'GET',
	//            url:   url,
	//            async: true,
	//            beforeSend: function () {
	//                
	//            },
	//            success:  function (resultado) {
	//                    $(campo).html(resultado);
	//            }
	//        });
})