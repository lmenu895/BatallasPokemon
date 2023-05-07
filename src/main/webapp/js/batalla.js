/**
 * 
 */
$(document).ready(function() {

	var endGame = 0
	var usrEnvenenado = 0
	var cpuEnvenenado = 0
	var continuar = 0
	var primero = 0

	$("#ataques").on("click", "button", function() {
		console.log(continuar)
		var vidaPkmnCpu = $("#vidaPkmnCpu").html()
		var vidaPkmnUsr = $("#vidaPkmnUsr").html()
		var widthUsrBar = vidaPkmnUsr * 100 / pokemonUsuario.vida;
		var widthCpuBar = vidaPkmnCpu * 100 / pokemonCpu.vida;
		var ataque = this

		$(".ataques").prop('disabled', true)
		var velocidad = Math.floor(Math.random() * 2)

		if (velocidad == 1) {
			vidaPkmnCpu = ataqueUsuario(vidaPkmnCpu, widthCpuBar, ataque)
			if (endGame != 1) {
				vidaPkmnUsr = esperandoAtaque(vidaPkmnUsr, widthUsrBar, "user")
			}
		}
		else {
			vidaPkmnUsr = ataqueCpu(vidaPkmnUsr, widthUsrBar)
			if (endGame != 1) {
				vidaPkmnCpu = esperandoAtaque(vidaPkmnCpu, widthCpuBar, "cpu", ataque)
			}
		}
		if (endGame != 1) {
			efectosDeEstado(vidaPkmnCpu, vidaPkmnUsr)
		}
		enableButtons()
	})

	function ataqueUsuario(vidaPkmnCpu, widthCpuBar, ataque) {
		var tipo = pokemonUsuario.ataques[ataque.id].tipo
		vidaPkmnCpu -= pokemonUsuario.ataques[ataque.id].potencia
		if (vidaPkmnCpu > 0) {
			$("#vidaPkmnCpu").html(vidaPkmnCpu)
		}
		else {
			$("#vidaPkmnCpu").html("Ganaste C:")
			endGame = 1;
		}
		if (cpuEnvenenado != 1 && tipo == "VENENO") {
			envenenado("cpu")
		}
		move(vidaPkmnCpu * 100 / pokemonCpu.vida, "#progressBarCpu", widthCpuBar)
		return vidaPkmnCpu

	}

	function ataqueCpu(vidaPkmnUsr, widthUsrBar) {
		var random = Math.floor(Math.random() * pokemonCpu.ataques.length)
		var tipo = pokemonCpu.ataques[random].tipo
		vidaPkmnUsr -= pokemonCpu.ataques[random].potencia
		if (vidaPkmnUsr > 0) {
			$("#vidaPkmnUsr").html(vidaPkmnUsr)
			$("#ataqueCpu").html("Ataque enemigo: " + pokemonCpu.ataques[random].nombre)
		}
		else {
			$("#vidaPkmnUsr").html("Perdiste :C")
			endGame = 1;
		}
		if (usrEnvenenado != 1 && tipo == "VENENO") {
			envenenado("user")
		}
		move(vidaPkmnUsr * 100 / pokemonUsuario.vida, "#progressBarUsr", widthUsrBar)
		return vidaPkmnUsr
	}

	function efectosDeEstado(vidaCpu, vidaUsr) {
		var widthBarCpu = vidaCpu * 100 / pokemonCpu.vida;
		var widthBarUsr = vidaUsr * 100 / pokemonUsuario.vida;
		if (primero == 0) {
			if (cpuEnvenenado == 1)
				danioPorVeneno(widthBarCpu, vidaCpu, pokemonCpu.vida, "#progressBarCpu", "#vidaPkmnCpu", "Ganaste C:")
			if (usrEnvenenado == 1)
				danioPorVeneno(widthBarUsr, vidaUsr, pokemonUsuario.vida, "#progressBarUsr", "#vidaPkmnUsr", "Perdiste :C")
		}
		else {
			if (usrEnvenenado == 1)
				danioPorVeneno(widthBarUsr, vidaUsr, pokemonUsuario.vida, "#progressBarUsr", "#vidaPkmnUsr", "Perdiste :C")
			if (cpuEnvenenado == 1)
				danioPorVeneno(widthBarCpu, vidaCpu, pokemonCpu.vida, "#progressBarCpu", "#vidaPkmnCpu", "Ganaste C:")
		}
	}

	function envenenado(name) {
		var random;
		random = Math.floor(Math.random() * 10)
		if (random >= 6) {
			if (name == "cpu") {
				cpuEnvenenado = 1
				$("#estadoCpu").html("Poisoned")
			}
			else if (name == "user") {
				usrEnvenenado = 1
				$("#estadoUsuario").html("Poisoned")
				primeto = 1
			}

		}
		else
			return 0
	}

	function danioPorVeneno(widthBar, vida, vidaMaxima, progressBar, vidaPkmnHtml, mensaje) {
		vida -= vidaMaxima * 8 / 100
		if (vida > 0) {
			$(vidaPkmnHtml).html(vida)
		}
		else {
			$(vidaPkmnHtml).html(mensaje)
			endGame = 1;
		}
		move(vida * 100 / vidaMaxima, progressBar, widthBar)
		return vida
	}

	function move(valor, id, width) {
		var elem = $(id);
		var interval = setInterval(frame, 10);
		function frame() {
			if (width < valor) {
				clearInterval(interval)
				continuar = 1
			} else {
				width -= 0.3;
				elem.width(width + "%")
			}
		}
	}

	function enableButtons() {
		if (endGame != 1)
			$(".ataques").prop('disabled', false)
	}

	function esperandoAtaque(vida, width, objetivo, ataque) {
		var vidaPkmn
		var interval = setInterval(function() {
			if (continuar == 0) return
			clearInterval(interval)
			continuar = 0
			if (objetivo == "user")
				vidaPkmn = ataqueCpu(vida, width)
			else
				vidaPkmn = ataqueUsuario(vida, width, ataque)
			interval = setInterval(function() {
				if (continuar == 0) return
				continuar = 0
			}, 10)
			return vidaPkmn
		}, 10)
	}

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
});