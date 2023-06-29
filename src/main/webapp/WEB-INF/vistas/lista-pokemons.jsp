<%@ include file="partial/header.jsp"%>
<link href="css/style.css" rel="stylesheet">
<title>Lista de Pokemons</title>
</head>
<body class="pokemon">
	<div style="position: absolute; width: 100%; margin-top: 1rem;">
		<a href="<c:url value="/home"/>"><img class="pokemon-img"
			alt="pokemon" src="<c:url value="/images/pokemonLogo.png"/>"></a>
	</div>
	<div class="container">
		<a class="boton-nuevo btn btn-success mt-5" href="crear-pokemon">Nuevo
			Pokemon</a>
		<div class="buscador">
			<input type="text" placeholder="Buscar pokemon"
				class="form-control buscar" /><span class="clear">X</span>
		</div>
		<table id="tablaPokemons"
			class='table table-hover table-striped mb-5 tableForm'>
			<thead>
				<tr class='text-center table-dark align-middle'>
					<th scope='col' class="thtitle thtitle-nombre">Nombre Pokemon</th>
					<th scope='col' class="thtitle thtitle-tipo">Tipo</th>
					<th scope='col'>Acciones</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${listaPokemons}" var="pokemon">
					<tr class='text-center align-middle trbody'
						id="pokemon${pokemon.id}">
						<!-- items LO QUE RECORRE , var COMO SE VA A LLAMAR CADA ITEM -->
						<td id="nombre" class="vBuscado">${pokemon.nombre}</td>
						<!-- ${VAR.atributo} -->
						<td id="tipo" class="vBuscado">${pokemon.tipo}</td>
						<td class="vRegexRareza" style="display: none;">${pokemon.rareza}</td>
						<td><button class="borrar btn btn-danger"
								value="${pokemon.id}">Borrar</button> <a
							class="modificar btn btn-info text-light"
							href="modificar-pokemon/${pokemon.id}">Modificar</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<script type="module" src="js/lista-pokemons.js"></script>
</body>
</html>