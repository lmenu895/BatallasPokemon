<%@ include file="partial/header.jsp"%>
<%@ page import="java.util.ArrayList"%>
<link href="css/progress-bar.css" rel="stylesheet">
<link href="css/batalla.css" rel="stylesheet">
<title>Pocket Monster Online Battle!</title>
</head>
<body>
	<div class="container mt-5">
		<div class="gamebox">
			<div id="usuario" class="pokeUsuario">
				<div class="contenedorVidaUser">
					<div class="texto" id="nombrePkmnUsr">${pokemonsUsuario[0].nombre}</div>
					<div class="vida texto">
						<span id="vidaPkmnUsr"></span><span id="vidaMaximaPkmnUsr"></span>
						<span id="estadoUsuario"></span>
					</div>
					<div id="progressUsr" class="myProgress">
						<div id="progressBarUsr" class="myBar"></div>
					</div>
				</div>
				<c:forEach items="${pokemonsUsuario}" var="pokemon">
					<img class="imagenBatalla img-usuario"
						title="Tipo: ${pokemon.tipo}" alt=""
						src="images/sprites/${pokemon.nombre}/${pokemon.imagenDorso}">
				</c:forEach>
			</div>
			<div id="cpu" class="pokeCpu">
				<div class="contenedorVidaCpu">
					<div class="textoCpu" id="nombrePkmnCpu">${pokemonsCpu[0].nombre}</div>
					<div class="textoCpu">
						<!-- CLASE TEXTO EN HP Y NOMBRE DE LOS POKEMON PARA EL FONDO BLANCO -->
						<span id="estadoCpu"></span> <span id="vidaPkmnCpu"></span><span
							id="vidaMaximaPkmnCpu"></span>
					</div>
					<div id="progressCpu" class="myProgress">
						<div id="progressBarCpu" class="myBar"></div>
					</div>
				</div>
				<c:forEach items="${pokemonsCpu}" var="pokemon">
					<img class="imagenBatalla img-cpu" title="Tipo: ${pokemon.tipo}"
						alt=""
						src="images/sprites/${pokemon.nombre}/${pokemon.imagenFrente}">
				</c:forEach>
			</div>
		</div>
		<div class="ams" style="padding: 10px;">
			<!-- ATAQUES MOCHILA SUPLENTES xD -->
			<!-- ATAQUES DEL USUARIO -->
			<div id="ataques">
				<c:forEach items="${pokemonsUsuario[0].ataques}" var="ataque"
					varStatus="status">
					<button class='btn btn-success ataques'
						title="Tipo: ${ataque.tipo} | Potencia: ${ataque.potencia} | Precision: ${ataque.precataque}"
						style="margin: 10px 10px 0 0" value="${status.count-1}">${ataque.nombre}</button>
				</c:forEach>
			</div>
			<c:if test="${objetosUsuario != null}">
				<div class="objetos d-flex align-items-center">
					<button id="abrirMochila" class="btn btn-success ">
						<img class="img-mochila" alt="mochila"
							src="images/Mochila_RZ_(chico).png"> Mochila
					</button>
					<div class="verMochila">
						<div class="mochila">
							<c:forEach items="${objetosUsuario}" var="objeto"
								varStatus="status">
								<button class="objeto btn btn-success" value="${status.count-1}">
									<img class="img-mochila" alt="objeto"
										src="images/sprites/Objetos/${objeto.imagen}">
									${objeto.nombre}
								</button>
							</c:forEach>
						</div>
					</div>
				</div>
			</c:if>
			<div class="cambiarPokemon d-flex">
				<c:forEach items="${pokemonsUsuario}" var="pokemon"
					varStatus="status">
					<button class='btn btn-success suplentes'
						title="Tipo: ${pokemon.tipo}" style="margin-right: 10px;"
						value="${status.count-1}">
						<img class="img-suplente" alt="${pokemon.nombre}"
							src="images/sprites/${pokemon.nombre}/${pokemon.imagenFrente}">
						${pokemon.nombre}
					</button>
				</c:forEach>
			</div>
			<div class="historial"></div>
		</div>
		<dialog class="game-over"> <img class="modalV">
		<button class="btn btn-success d-block continuar"
			style="margin: auto;">Continuar</button>
		</dialog>

	</div>
	<div class="musica-fondo">
		<div class="interfazMusica">
			<span class="texto" style="margin: 0 5px 6.5px 0;">Volumen
				música y sonidos: </span> <input type="range" id="slider" value="10"
				maxlength="100"> <span class="reproducir">▶️</span>
		</div>
		<dialog class="reproducir-dialog"> <span class="d-block mb-2">¿Quieres
			activar la música de batalla?</span>
		<div class="d-flex justify-content-around">
			<button class="yes btn btn-info w-25" type="button">Sí</button>
			<button class="no btn btn-info w-25" type="button">No</button>
		</div>
		</dialog>
		<audio id="musica" src="images/pokemonSoundtrack.mp3" loop="loop"></audio>
	</div>
	<audio id="golpe" src="images/golpe.wav"></audio>
	<audio id="final" src="images/victoriaBatalla.mp3"></audio>
	<audio id="finalMalo" src="images/gameOver.wav"></audio>


	<script type="text/javascript">
		var pokemonsUsuario = JSON.parse('${pokemonsUsuarioJson}');
		var pokemonsCpu = JSON.parse('${pokemonsCpuJson}');
		var objetosUsuario = JSON.parse('${objetosUsuarioJson}');
	</script>
	<script type="text/javascript" src="js/batalla.js"></script>
</body>
</html>