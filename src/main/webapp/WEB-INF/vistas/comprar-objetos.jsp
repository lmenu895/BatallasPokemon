<%@ include file="partial/header.jsp"%>
<link rel="stylesheet" href="css/comprar-objetos.css">
<title>Comprar Objetos</title>
</head>
<body>
	<div>
		<a href="<c:url value="/home"/>"><img class="pokemon-img"
			alt="pokemon" src="<c:url value="/images/pokemonLogo.png"/>"></a>
	</div>
	<div class="container ">
		<form method="POST" class="objetos">
			<div
				class="d-flex align-items-baseline justify-content-around flex-wrap">
				<c:forEach items="${listaUsuarioObjetos}" var="usuarioObjeto">
					<div class="d-flex flex-column align-items-center"
						style="width: 33%;">
						<label style="text-transform: uppercase;"
							for="${usuarioObjeto.objeto.nombre}">${usuarioObjeto.objeto.nombre}</label>
						<br> <img alt="${usuarioObjeto.objeto.nombre}"
							class="img-fluid mb-2" style="max-height: 70px;"
							src="images/sprites/Objetos/${usuarioObjeto.objeto.imagen}">
						<br> <span>Tienes: ${usuarioObjeto.cantidad}</span>

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