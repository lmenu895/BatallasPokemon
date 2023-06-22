<%@ include file="partial/header.jsp"%>
<link href="<c:url value="/css/perfil-de-usuario.css"/>" rel="stylesheet">
<title>Perfil de usuario</title>
</head>
<body>

	<div>
		<a href="<c:url value="/home"/>"><img class="pokemon-img" alt="pokemon"
			src="<c:url value="/images/pokemonLogo.png"/>"></a>
	</div>
	<div class="container">
		<div class="botones-perfil">
			<a
				class="btn-perfil btn-datos<c:if test="${contenido=='datos-de-usuario'}"> activo</c:if>"
				id="datosUsuario" href="<c:url value="/datos-de-usuario"/>">Datos de usuario</a>
			<a
				class="btn-perfil btn-historial<c:if test="${contenido=='historial-de-batallas'}"> activo</c:if>"
				id="historialBatallas" href="<c:url value="/historial-de-batallas"/>">Historial de batallas</a>
			<a
				class="btn-perfil btn-lista<c:if test="${contenido=='lista-pokemons-usuario'}"> activo</c:if>"
				id="listaPokemons" href="<c:url value="/lista-pokemons-usuario"/>">Lista de pokemons</a>
		</div>
		<div class="contenido">
			<jsp:include page="partial/${contenido}.jsp"></jsp:include>
		</div>
	</div>
	<script type="text/javascript">
		var root = <c:url value="/"/>;
	</script>
	<script type="text/javascript" src="<c:url value="/js/perfil-de-usuario.js"/>"></script>
</body>
</html>