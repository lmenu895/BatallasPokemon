<%@ include file="partial/header.jsp"%>
<link href="css/progress-bar.css" rel="stylesheet">
<link href="css/batalla.css" rel="stylesheet">
<title>Pocket Monster Online Battle!</title>
</head>
<body>
	<div class="container mt-5">
		<div class="gamebox">
			<div id="usuario" class="pokeUsuario" style="left: 60px;">
				<div class="texto" id="nombrePkmnUsr">${pokemonsUsuario[0].nombre}</div>
				<div class="vida texto">
					<span id="vidaPkmnUsr"></span><span id="vidaMaximaPkmnUsr"></span><span
						id=estadoUsuario style="padding-left: 20px;"></span>
				</div>
				<div id="progressUsr" class="myProgress">
					<div id="progressBarUsr" class="myBar"></div>
				</div>
				<!-- ///////////////////////////////////////////////////////////////////////////////////////////// -->

				<!-- ///////////////////////////////////////////////////////////////////////////////////////////// -->
			</div>
			<div id="cpu" class="pokeCpu" style="height: 90px; right: 30px;">
				<div class="textoCpu" id="nombrePkmnCpu">${pokemonCpu.nombre}</div>
				<div class="textoCpu">
					<!-- CLASE TEXTO EN HP Y NOMBRE DE LOS POKEMON PARA EL FONDO BLANCO -->
					<span id="vidaPkmnCpu"></span><span id="vidaMaximaPkmnCpu"></span><span
						id=estadoCpu style="padding-left: 20px;"></span>
				</div>
				<div id="progressCpu" class="myProgress">
					<div id="progressBarCpu" class="myBar"></div>
				</div>
				<div id="ataqueCpu" style="visibility: hidden;">Ataque</div>
			</div>
		</div>

		<div class="ams">
			<!-- ATAQUES MOCHILA SUPLENTES xD -->
			<!-- ATAQUES DEL USUARIO -->
			<div id="ataqueUsuario" style="visibility: hidden;">Ataque</div>
			<div id="ataques">
				<c:forEach items="${pokemonsUsuario[0].ataques}" var="ataquePokemon"
					varStatus="status">
					<button class='btn btn-success ataques' value="${status.count-1}">${ataquePokemon.ataque.nombre}</button>
				</c:forEach>
			</div>
			<br>
			<div class="objetos d-flex align-items-center mb-2">
				<button id="abrirMochila" class="btn btn-success ">
					<img class="img-mochila" alt="mochila"
						src="images/Mochila_RZ_(chico).png"> Mochila
				</button>
				<br>
				<div class="verMochila">
					<div class="mochila">
						<button class="objeto btn btn-success">
							<img class="img-mochila" alt="mochila"
								src="images/Mochila_RZ_(chico).png"> Antídoto
						</button>
						<button class="objeto btn btn-success">
							<img class="img-mochila" alt="mochila"
								src="images/Mochila_RZ_(chico).png"> Antiparaliz
						</button>
						<button class="objeto btn btn-success">
							<img class="img-mochila" alt="mochila"
								src="images/Mochila_RZ_(chico).png"> Poción
						</button>
					</div>
				</div>
			</div>
			<div class="cambiarPokemon d-flex">
				<c:forEach items="${pokemonsUsuario}" var="pokemon"
					varStatus="status">
					<button class='btn btn-success suplente'
						style="margin-right: 10px;" value="${status.count-1}">
						<img class="img-suplente" alt="${pokemon.nombre}"
							src="images/sprites/${pokemon.nombre}/${pokemon.imagenFrente}">
						${pokemon.nombre}
					</button>
				</c:forEach>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="js/batalla.js"></script>
</body>
</html>