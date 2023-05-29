<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">
<title>Elegir Equipo</title>
<link rel="icon" type="image/x-icon" href="images/favicon.ico">
</head>
<body class="pokemon">
	<div class="container d-flex">
		<form:form action="batalla" method="POST" modelAttribute="pokemon"
			enctype="multipart/form-data">
			<div class="formP">
				<div class="form">
					<h3 class="form-signin-heading">Equipo</h3>
					<h3 class="fs-5 text mb-3">Seleccione 3 Pokémons para la
						batalla</h3>
					<input type="text" placeholder="Buscar pokemon"
						class="form-control buscar" />
					<div
						class="d-flex align-items-baseline justify-content-around flex-wrap pokemons"
						style="width: 30vw; height: 510px; overflow-y: auto;">
						<c:forEach items="${listaPokemon}" var="pokemon">
							<div
								class="d-flex flex-column align-items-center pokemon-usuario"
								id="${pokemon.nombre}" style="width: 33%;">
								<img alt="${pokemon.nombre}" class="img-fluid imgPokemon mb-2"
									src="images/sprites/${pokemon.nombre}/${pokemon.imagenFrente}">
								<button type="button" style="width: 105px;"
									class="btn btn-primary botonPokemon" value="${pokemon.id}">Seleccionar</button>
								<h4 class="nombre-pokemon">${pokemon.nombre}</h4>
							</div>
						</c:forEach>
					</div>
					<div class="pokemons-seleccionados"></div>
				</div>

				<div class="form">
					<h3 class="form-signin-heading">Objetos</h3>
					<h3 class="fs-5 text mb-3">Seleccione qué objetos va a llevar
						(máximo 3)</h3>
					<div
						class="d-flex align-items-baseline justify-content-around flex-wrap"
						style="width: 30vw">
						<c:forEach items="${listaObjetos}" var="objeto">
							<div class="d-flex flex-column align-items-center"
								style="width: 33%;">
								<img alt="${objeto.nombre}" class="img-fluid mb-2"
									style="max-height: 70px;"
									src="images/sprites/Objetos/${objeto.imagen}">
								<button type="button" style="width: 105px;"
									class="btn btn-primary botonObjeto">Seleccionar</button>
								<input type="hidden" name="objetosLista" value="${objeto.id}"
									disabled />
								<h4 class="nombre-objeto">${objeto.nombre}</h4>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
			<div class="text-center">
				<button id="btn-registrarme"
					class="btn btn-lg btn-primary btn-block mb-2" Type="Submit">INICIAR
					BATALLA</button>
			</div>

		</form:form>
		<c:if test="${not empty error}">
			<span id="error" style="display: none;">${error}</span>
		</c:if>
	</div>


	<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.bundle.min.js"></script>
	<script type="text/javascript" src="js/elegir-equipo.js"></script>
	<!-- 	<script type="text/javascript" src="js/validation.js"></script> -->
</body>
</html>