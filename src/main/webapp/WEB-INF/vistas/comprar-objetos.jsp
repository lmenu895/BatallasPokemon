<%@ include file="partial/header.jsp"%>
<link rel="stylesheet" href="css/comprar-objetos.css">
<title>Comprar Objetos</title>
</head>
<body>
	<div class="container">
		<form method="POST">
			<div class="d-inline-flex flex-column mb-2">
				<c:forEach items="${listaUsuarioObjetos}" var="usuarioObjeto">
					<div class="form-control">
						<label for="${usuarioObjeto.objeto.nombre}">${usuarioObjeto.objeto.nombre}</label>
						<span>Tienes: ${usuarioObjeto.cantidad}</span>
						<div>
							Precio: <span class="precio">${usuarioObjeto.objeto.precio}
							</span> puntos
						</div>
						<input type="number" class="form-control col-3 puntos-gastados"
							id="${usuarioObjeto.objeto.nombre}" name="cantidad" value="0"
							min="0" max="99" /> <br>
					</div>
				</c:forEach>
			</div>
			<button class="btn btn-warning btn-lg d-block" type="submit">Comprar</button>
			<div>
				Estás gastando: <span id="total">0</span> puntos
			</div>
			<span id="puntosUsuario">Tus puntos: ${puntosUsuario}</span>
		</form>
	</div>

	<script src="js/comprar-objetos.js"></script>
</body>
</html>