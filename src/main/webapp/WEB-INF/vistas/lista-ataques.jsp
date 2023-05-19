<%@ include file="partial/header.jsp"%>>
<title>Lista de ataques</title>

<body class="fondo">
	<div class="container">
		<button class="boton-nuevo btn btn-success mt-5">Nuevo Ataque</button>
		<table id="tablaAtaques"
			class='table table-hover table-striped mb-5 tableForm'>

			<thead>
				<tr class='text-center table-dark align-middle '>
					<th scope='col'>Nombre Ataque</th>
					<th scope='col'>Acciones</th>

				</tr>
			</thead>
			<tbody>
				<c:forEach items="${listaAtaques}" var="ataque">
					<tr class='text-center align-middle' id="ataque${ataque.id}">
						<!-- items LO QUE RECORRE , var COMO SE VA A LLAMAR CADA ITEM -->
						<td>${ataque.nombre}</td>
						<!-- ${VAR.atributo} -->
						<td><button class="borrar btn btn-danger"
								value="${ataque.id}">Borrar</button>
							<button class="modificar btn btn-info text-light"
								value="${ataque.id}">Modificar</button></td>

					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="js/lista-ataques.js"></script>
</body>
</html>