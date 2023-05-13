<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">
<title>Elegir Equipo</title>
<link rel="icon" type="image/x-icon" href="images/favicon.ico">
</head>
<body class="pokemon">
	<div class="container d-flex justify-content-center align-items-center">
		<form:form action="guardar-equipo" method="POST" class="form"
			modelAttribute="pokemon" enctype="multipart/form-data">
			<h3 class="form-signin-heading">Nuevo Equipo</h3>
			<h3 class="fs-5 text">Seleccione qu Pokemons va a tener</h3>
			<div class="form-group lista-ataques">
			
				<c:forEach items="${listaPokemon}" var="pokemon">
					<div class="form-check form-check-inline">
						<label class="form-check-label ataques-label">${pokemon.nombre}</label>
						<input type="checkbox" class="form-check-input ataques"
							name="pokemonsLista" value="${pokemon.id}" />
					</div>
				</c:forEach>
			</div>
			
			<button id="btn-registrarme"
				class="btn btn-lg btn-primary btn-block mb-2" Type="Submit">Guardar</button>
			<c:if test="${not empty error}">
				<h4>
					<span>${error}</span>
				</h4>
				<br>
			</c:if>
		</form:form>
	</div>
	<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.bundle.min.js"></script>
	<!-- 	<script type="text/javascript" src="js/validation.js"></script> -->
</body>
</html>