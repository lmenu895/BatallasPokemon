<%@ include file="partial/header.jsp"%>
<link href="css/perfil-de-usuario.css" rel="stylesheet">
<title>Perfil de usuario</title>
</head>
<body>

	<div>
		<a href="home"><img class="pokemon" alt="pokemon"
			src="images/pokemonLogo.png"></a>
	</div>
	<div class="container">
		<div class="botones-perfil">
			<button
				class="btn-perfil btn-datos<c:if test="${contenido=='datos-de-usuario'}"> activo</c:if>"
				id="datosUsuario">Datos de usuario</button>
			<button
				class="btn-perfil btn-historial<c:if test="${contenido=='historial-de-batallas'}"> activo</c:if>"
				id="historialBatallas">Historial de batallas</button>
			<button
				class="btn-perfil btn-lista<c:if test="${contenido=='lista-pokemons-usuario'}"> activo</c:if>"
				id="listaPokemons">Lista de pokemons</button>
		</div>
		<div class="contenido">
			<jsp:include page="partial/${contenido}.jsp"></jsp:include>
		</div>




	</div>
	<script type="text/javascript" src="js/perfil-de-usuario.js"></script>
</body>
</html>