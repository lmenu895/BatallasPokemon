<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="css/bootstrap.min.css" rel="stylesheet">
<title>Lista de Pokemons</title>
</head>
<body>
	<div class="container">
		<c:forEach items="${listaPokemons}" var="pokemon">
			<div id="pokemon${pokemon.id}">
				<p>${pokemon.nombre}</p>
				<button class="btn btn-info modificar" id="a" value="${pokemon.id}">Modificar</button>
				<button class="btn btn-danger borrar" value="${pokemon.id}">Borrar</button>
			</div>
		</c:forEach>
	</div>
	<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="js/lista-pokemons.js"></script>
</body>
</html>