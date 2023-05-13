<%@ include file="partial/header.jsp"%>
<title>Lista de Pokemons</title>
</head>
<body class="pokemon">
	<div class="container">
		<button class="boton-nuevo btn btn-success mt-5">Nuevo
			Pokemon</button>
		<table id="tablaPokemons"
			class='table table-hover table-striped mb-5 tableForm'>
			<thead>
				<tr class='text-center table-dark align-middle '>
					<th scope='col'>Nombre Pokemon</th>
					<th scope='col'>Acciones</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${listaPokemons}" var="pokemon">
					<tr class='text-center align-middle' id="pokemon${pokemon.id}">
						<!-- items LO QUE RECORRE , var COMO SE VA A LLAMAR CADA ITEM -->
						<td>${pokemon.nombre}</td>
						<!-- ${VAR.atributo} -->
						<td><button class="borrar btn btn-danger"
								value="${pokemon.id}">Borrar</button>
							<button class="modificar btn btn-info text-light"
								value="${pokemon.id}">Modificar</button></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<script type="text/javascript" src="js/lista-pokemons.js"></script>
</body>
</html>