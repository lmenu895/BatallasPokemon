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
		<form:form action="guardar-equipo" method="POST"
			modelAttribute="pokemon" enctype="multipart/form-data">
			<div class="formP">
				<div class="form">
					<h3 class="form-signin-heading">Nuevo Equipo</h3>
					<h3 class="fs-5 text">Seleccione qué Pokemons va a tener</h3>
					<div class="d-flex align-items-baseline justify-content-around flex-wrap" style="width: 30vw">
						<c:forEach items="${listaPokemon}" var="pokemon">
							<div class="d-flex flex-column align-items-center" style="width: 33%;">
								<img alt="${pokemon.nombre}" class="img-fluid imgPokemon"
								src="images/sprites/${pokemon.nombre}/${pokemon.imagenFrente}">
								<button type="button" style="width: 105px;" class="btn btn-primary botonPokemon">Seleccionar</button>
								<input type="hidden" name="pokemonsLista" value="${pokemon.id}"
								disabled />
								<h4>${pokemon.nombre}</h4>
							</div>
						</c:forEach>
					</div>
				<c:if  test="${not empty error}">
					<h4 class="text-center">
						<span>${error}</span>
					</h4>
					<br>
				</c:if>
			</div>
			
			<div class="form">
				<h3 class="form-signin-heading">Lista de Objetos</h3>
				<h3 class="fs-5 text">Seleccione qué objetos va a tener</h3>
				<div class="d-flex align-items-baseline justify-content-around flex-wrap" style="width: 30vw">
					<c:forEach items="${listaObjetos}" var="objeto">
						<div class="d-flex flex-column align-items-center" style="width: 33%;">
							<button type="button" style="width: 105px;" class="btn btn-primary botonPokemon">Seleccionar</button>
							<input type="hidden" name="objetosLista" value="${objeto.id}"
								disabled />
							<h4>${objeto.nombre}</h4>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
	
		<div class="text-center">
			<button id="btn-registrarme"
					class="btn btn-lg btn-primary btn-block mb-2" Type="Submit">Guardar</button>
				
		</div>

		</form:form>
	</div>
	

	<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.bundle.min.js"></script>
	<script type="text/javascript" src="js/elegir-equipo.js"></script>
	<!-- 	<script type="text/javascript" src="js/validation.js"></script> -->
</body>
</html>