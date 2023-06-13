<%@ include file="partial/header.jsp"%>
<link href="css/style.css" rel="stylesheet">
<title>Modificar Pokemon</title>
</head>
<body class="fondo pokemon">
	<div class="container d-flex justify-content-center align-items-center">
		<form:form method="POST" class="form" modelAttribute="datosPokemon"
			enctype="multipart/form-data">
			<h3 class="form-signin-heading">${pokemon.nombre}</h3>
			<hr class="colorgraph">
			<c:if test="${not empty error}">
				<h4>
					<span>${error}</span>
				</h4>
				<br>
			</c:if>
			<div class="form-group mb-3">
				<form:label path="nombre">�Cu�l ser� el nombre del pokemon?</form:label>
				<form:input path="nombre" id="nombre" class="form-control" value="${pokemon.nombre}" />
				<div class="valida-nombre-vacio validation" style="display: none;">Ingrese
					un nombre</div>
			</div>
			<div class="mb-3" id="imagenes">
				<div class="form-group mb-3">
					<label for="imagenFrente">Ingrese un sprite de su pokemon de
						frente</label> <form:input path="imagenFrente" class="form-control" type="file" />
					<div id="verFrente" style="text-align: center;">
						<img id='_frente' alt='frente'
							src="images/sprites/${pokemon.nombre}/${pokemon.imagenFrente}"
							class='img-fluid mt-1 sprite'>
					</div>
				</div>
				<div class="form-group mb-3">
					<label for="imagenDorso">Ingrese un sprite de su pokemon de
						espaldas</label> <form:input path="imagenDorso" class="form-control" type="file" />
					<div id="verDorso" style="text-align: center;">
						<img id='_dorso' alt='dorso'
							src="images/sprites/${pokemon.nombre}/${pokemon.imagenDorso}"
							class='img-fluid mt-1 sprite'>
					</div>
				</div>
			</div>
			<div class="form-group mb-3">
				<form:select class="form-select" path="tipo" id="tipo">
					<form:option value="${pokemon.tipo}">${pokemon.tipo}</form:option>
					<form:options />
				</form:select>
				<div class="valida-select validation" style="display: none;">Seleccione
					un tipo</div>
			</div>
			<div class="form-group mb-3">
				<form:select class="form-select" path="rareza" id="rareza">
					<form:option value="${pokemon.rareza}">${pokemon.rareza}</form:option>
					<form:options />
				</form:select>
				<div class="valida-select validation" style="display: none;">Seleccione
					una rareza</div>
			</div>
			<h3 class="fs-5 text">Seleccione qu� ataques disponibles va a
				tener su pokemon</h3>
			<div class="form-group lista-ataques">
				<c:forEach items="${listaAtaques}" var="ataque" varStatus="status">
					<div class="form-check form-check-inline">
						<label for="ataquesDesbloqueados${status.count}" class="form-check-label ataques-label">${ataque.nombre}</label>
						<form:checkbox path="ataquesDesbloqueados"
							class="form-check-input ataques" value="${ataque.id}" />
					</div>
				</c:forEach>
			</div>
			<h3 class="fs-5 text">Seleccione qu� ataques bloqueados va a
				tener su pokemon</h3>
			<div class="form-group lista-ataques">
				<c:forEach items="${listaAtaques}" var="ataque" varStatus="status">
					<div class="form-check form-check-inline">
						<label for="ataquesBloqueados${status.count}" class="form-check-label ataques-label">${ataque.nombre}</label>
						<form:checkbox path="ataquesBloqueados"
							class="form-check-input ataques" value="${ataque.id}" />
					</div>
				</c:forEach>
			</div>
			<div class="valida-ataques validation mb-3" style="display: none;">Seleccione
				m�nimo 4 ataques</div>
			<div class="form-group mb-3">
				<form:label path="vida">�Cu�nta vida va a tener el pokemon?</form:label>
				<form:input class="form-control" path="vida" id="vida" value="${pokemon.vida}" />
				<div class="valida-vida-vacia validation" style="display: none;">Especifique
					cu�nta vida tendr� su pokemon</div>
			</div>
			<div class="form-group mb-3">
				<form:label path="velocidad">�Qu� velocidad tendr� su pokemon?</form:label>
				<form:input class="form-control" path="velocidad" id="velocidad" value="${pokemon.velocidad}" />
				<div class="valida-velocidad-vacia validation"
					style="display: none;">Especifique qu� velocidad tendr� su
					pokemon</div>
			</div>
			<input type="hidden" name="id" value="${pokemon.id}" />
			<button id="btn-registrarme"
				class="btn btn-lg btn-primary btn-block mb-2" Type="Submit">Modificar</button>
		</form:form>
	</div>
	<script type="text/javascript" src="js/validation.js"></script>
</body>
</html>