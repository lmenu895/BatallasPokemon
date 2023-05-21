<%@ include file="partial/header.jsp"%>
<%@ page import="java.util.ArrayList" %>
<link href="css/progress-bar.css" rel="stylesheet">
<link href="css/batalla.css" rel="stylesheet">
<title>Pocket Monster Online Battle!</title>
</head>
<body>
	<div class="container mt-5">
		<div id="usuario">
			<div id="nombrePkmnUsr">${pokemonsUsuario[0].nombre}</div>
			<div class="vida">
				<span id="vidaPkmnUsr"></span><span id="vidaMaximaPkmnUsr"></span><span
					id=estadoUsuario style="padding-left: 20px;"></span>
			</div>
			<div id="progressUsr" class="myProgress">
				<div id="progressBarUsr" class="myBar"></div>
			</div>
			<div id="ataqueUsuario" style="visibility: hidden;">Ataque</div>
			<div id="ataques">
				<c:forEach items="${pokemonsUsuario[0].ataques}" var="ataque"
					varStatus="status">
					<button class='btn btn-success ataques' value="${status.count-1}">${ataque.nombre}</button>
				</c:forEach>
			</div>
		</div>
		<div id="cpu">
			<div id="nombrePkmnCpu">${pokemonsCpu.nombre}</div>
			<div>
				<span id="vidaPkmnCpu"></span><span id="vidaMaximaPkmnCpu"></span><span
					id=estadoCpu style="padding-left: 20px;"></span>
			</div>
			<div id="progressCpu" class="myProgress">
				<div id="progressBarCpu" class="myBar"></div>
			</div>
			<div id="ataqueCpu" style="visibility: hidden;">Ataque</div>
		</div>
		<div class="objetos d-flex align-items-center mb-2">
			<button id="abrirMochila" class="btn btn-success ">
				<img class="img-mochila" alt="mochila"
					src="images/Mochila_RZ_(chico).png"> Mochila
			</button>
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
				<button class='btn btn-success suplentes' style="margin-right: 10px;"
					value="${status.count-1}">
					<img class="img-suplente" alt="${pokemon.nombre}"
						src="images/sprites/${pokemon.nombre}/${pokemon.imagenFrente}">
					${pokemon.nombre}
				</button>
			</c:forEach>
		</div>
	</div>
	<script type="text/javascript">
		var pokemonsUsuario = JSON.parse('${pokemonsUsuarioJson}');
		var pokemonsCpu = JSON.parse('${pokemonsCpuJson}');
	</script>
	<script type="text/javascript" src="js/batalla.js"></script>
</body>
</html>