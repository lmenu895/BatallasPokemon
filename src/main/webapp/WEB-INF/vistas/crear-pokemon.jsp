<%@ include file="partial/header.jsp"%>
<link href="css/style.css" rel="stylesheet">
<title>Crear Pokemon</title>
<body class="pokemon">
	<div class="container d-flex justify-content-center align-items-center">
		<form:form method="POST" class="form" modelAttribute="datosPokemon"
			enctype="multipart/form-data">
			<h3 class="form-signin-heading">Nuevo Pokemon</h3>
			<hr class="colorgraph">
			<c:if test="${not empty error}">
				<h4>
					<span style="color: red;">${error}</span>
				</h4>
				<br>
			</c:if>
			<div class="form-group mb-3">
				<form:label path="nombre">¿Cuál será el nombre del pokemon?</form:label>
				<form:input path="nombre" id="nombre" class="form-control" />
				<div class="valida-nombre-vacio validation" style="display: none;">Ingrese
					un nombre</div>
			</div>
			<div class="mb-3" id="imagenes">
				<div class="form-group mb-3">
					<label for="imagenFrente">Ingrese un sprite de su pokemon
						de frente:</label>
					<form:input path="imagenFrente" class="form-control" type="file" />
					<div id="verFrente" style="text-align: center;"></div>
					<div class="valida-sprite-frente validation" style="display: none;">Se
						necesita un sprite de frente del pokemon</div>
				</div>
				<div class="form-group mb-3">
					<label for="imagenDorso">Ingrese un sprite de su pokemon de
						espaldas:</label>
					<form:input path="imagenDorso" class="form-control" type="file" />
					<div id="verDorso" style="text-align: center;"></div>
					<div class="valida-sprite-dorso validation" style="display: none;">Se
						necesita un sprite de espaldas del pokemon</div>
				</div>
			</div>
			<div class="form-group mb-3">
				<form:select class="form-select" path="tipo" id="tipo">
					<form:option value="default">Seleccione un tipo</form:option>
					<form:options />
				</form:select>
				<div class="valida-select validation" style="display: none;">Seleccione
					un tipo</div>
			</div>
			<div class="form-group mb-3">
				<form:select class="form-select" path="rareza" id="rareza">
					<form:option value="default">Seleccione rareza</form:option>
					<form:options />
				</form:select>
				<div class="valida-select validation" style="display: none;">Seleccione
					rareza</div>
			</div>
			<h3 class="fs-5 text">Seleccione qué ataques disponibles va a
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
			<h3 class="fs-5 text">Seleccione qué ataques bloqueados va a
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
				mínimo 4 ataques</div>
			<div class="form-group mb-3">
				<form:label path="vida">¿Cuánta vida va a tener el pokemon?</form:label>
				<form:input class="form-control" path="vida" id="vida" />
				<div class="valida-vida-vacia validation" style="display: none;">Especifique
					cuánta vida tendrá su pokemon</div>
			</div>
			<div class="form-group mb-3">
				<form:label path="velocidad">¿Qué velocidad tendrá su pokemon?</form:label>
				<form:input class="form-control" path="velocidad" id="velocidad" />
				<div class="valida-velocidad-vacia validation"
					style="display: none;">Especifique qué velocidad tendrá su
					pokemon</div>
			</div>
			<button id="btn-registrarme"
				class="btn btn-lg btn-primary btn-block mb-2" Type="Submit">Guardar</button>
		</form:form>
	</div>
	<script type="text/javascript" src="js/validation.js"></script>
</body>
</html>