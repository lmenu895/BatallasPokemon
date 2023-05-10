<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Eliminar Ataque</title>
</head>
<body>

	<h1>Lista de Ataques</h1>

	<div class="container">
		<div class="lista">
			<c:forEach items="${listaAtaques}" var="ataque">
				<!-- items LO QUE RECORRE , var COMO SE VA A LLAMAR CADA ITEM -->
				<div>
					<h3>Nombre Ataque: ${ataque.nombre}</h3>
					<!-- ${VAR.atributo} -->
					<button class="borrar" value="${ataque.id}">Borrar</button>


				</div>

			</c:forEach>

		</div>









	</div>


</body>
</html>