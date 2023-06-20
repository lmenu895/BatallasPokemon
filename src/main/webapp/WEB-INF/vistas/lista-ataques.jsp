<%@ include file="partial/header.jsp"%>
<link href="css/style.css" rel="stylesheet">
<title>Lista de ataques</title>

<body class="fondo">
	<div class="container">
		<button class="boton-nuevo btn btn-success mt-5">Nuevo Ataque</button>
		<div class="buscador">
			<input type="text" placeholder="Buscar ataque"
				class="form-control buscar" /><span class="clear">X</span>
		</div>
		<table id="tablaAtaques"
			class='table table-hover table-striped mb-5 tableForm'>
			<thead>
				<tr class='text-center table-dark align-middle '>
					<th scope='col' class="thtitle thtitle-nombre">Nombre Ataque</th>
					<th scope='col' class="thtitle thtitle-tipo">Tipo</th>
					<th scope='col'>Acciones</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${listaAtaques}" var="ataque">
					<tr class='text-center align-middle trbody' id="ataque${ataque.id}">
						<!-- items LO QUE RECORRE , var COMO SE VA A LLAMAR CADA ITEM -->
						<td id="nombre" class="vBuscado">${ataque.nombre}</td>
						<!-- ${VAR.atributo} -->
						<td id="tipo" class="vBuscado">${ataque.tipo}</td>
						<td><button class="borrar btn btn-danger"
								value="${ataque.id}">Borrar</button>
							<button class="modificar btn btn-info text-light"
								value="${ataque.id}">Modificar</button></td>

					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
	<script type="module" src="js/lista-ataques.js"></script>
</body>
</html>