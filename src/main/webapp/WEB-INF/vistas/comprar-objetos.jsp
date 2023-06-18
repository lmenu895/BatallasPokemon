<%@ include file="partial/header.jsp"%>
<link rel="stylesheet" href="css/comprar-objetos.css">
<title>Comprar Objetos</title>
</head>
<body>
	<div class="container">
		<form method="POST">
			<div class="d-inline-flex flex-column mb-2">
				<c:forEach items="${listaObjetos}" var="objeto">
					<label for="${objeto.nombre}">${objeto.nombre}</label>
					<c:forEach items="${listaUsuarioObjetos}" var="objetoUsuario">
						<c:if test="${objeto.id == objetoUsuario.objeto.id}">
							<span>Tienes: ${objetoUsuario.cantidad}</span>
						</c:if>
					</c:forEach>
					<input type="number" class="form-control col-3"
						id="${objeto.nombre}" name="cantidad" value="0" min="0" max="99" />
					<input type="hidden" name="idsObjetos" value="${objeto.id}" />
					<br>
				</c:forEach>
			</div>
			<button class="btn btn-warning btn-lg d-block" type="submit">Comprar</button>
		</form>
	</div>
</body>
</html>