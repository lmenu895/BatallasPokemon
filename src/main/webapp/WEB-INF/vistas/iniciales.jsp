<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Elegir Inicial</title>
	<link href="css/bootstrap.min.css" rel="stylesheet">
	<link href="css/style.css" rel="stylesheet">
		<link href="css/iniciales.css" rel="stylesheet">
	<link rel="icon" type="image/x-icon" href="images/favicon.ico">
</head>
<body class="fondo">
	<form:form action="guardar-inicial" method="POST" modelAttribute="pokemon"
			enctype="multipart/form-data">
			<div class="formA">
				<div class="forms">
					<h1 class=" text mb-3 center-text">Elija Inicial</h1>
					<div
						class="d-flex align-items-baseline justify-content-around flex-wrap pokemons marginArriba"
						style=" height: 510px; overflow-y: auto;">
						<c:forEach items="${listaPokemonComunes}" var="pokemon">
							<div
								class="d-flex flex-column align-items-center pokemon-usuario"
								id="${pokemon.nombre}" style="width: 33%;">
									<label>
										  <input type="radio" name="pokemon" value="${pokemon.id}" checked>
										  <img alt="${pokemon.nombre}"
											class="img-fluid imgPokemon mb-2"
											src="images/sprites/${pokemon.nombre}/${pokemon.imagenFrente}">
									</label>
									<h5 class="nombre-pokemon vBuscado tipo tipoP">${pokemon.tipo}</h5>
									<h4 class="nombre-pokemon vBuscado">${pokemon.nombre}</h4>
							</div>
						</c:forEach>
					</div>
					<div class="pokemons-seleccionados"></div>
				</div>
			</div>
		
			<div class="text-center">
					<button id="btn-registrarme"
					class="btn btn-lg btn-primary btn-block mb-2" Type="Submit">Elegir Pokemon</button>
			</div>
		</form:form>
		
		<c:if test="${not empty error}">
			<span id="error" style="display: none;">${error}</span>
		</c:if>
		
	<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.bundle.min.js"></script>
	<script type="text/javascript" src="js/inicial.js"></script>
</body>
</html>