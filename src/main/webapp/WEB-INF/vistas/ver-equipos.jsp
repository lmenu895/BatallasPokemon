<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Equipo</title>
</head>
<body>
	<h1>Se ha creado el equipo correctamente</h1>
	<div class="container">
		<table id="tablaPokemons" class='table table-hover table-striped mb-5 tableForm'>
			<thead>
				<tr class='text-center table-dark align-middle '>
					<th scope='col'>Nombre Pokemon</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${equipo}" var="pokemon">
					<tr class='text-center align-middle' id="pokemon${pokemon.id}">
						<td>${pokemon.nombre}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>

		<table id="tablaPokemons" class='table table-hover table-striped mb-5 tableForm'>
			<thead>
				<tr class='text-center table-dark align-middle '>
					<th scope='col'>Nombre Objeto</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${objetos}" var="objeto">
					<tr class='text-center align-middle' id="pokemon${objeto.id}">
						<td>${objeto.nombre}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>