<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
	crossorigin="anonymous">
<title>Lista de Objetos</title>
<link rel="icon" type="image/x-icon" href="images/favicon.ico">
<link href="css/objetos.css" rel="stylesheet">
</head>
<body class="fondo">
	<table class="table table-hover form">
		<thead>
			<tr>
				<th scope="col">Nombre</th>
				<th scope="col">Descripcion</th>
				<th scope="col">Efecto</th>

			</tr>
		</thead>
		<tbody>
			<c:forEach var="objetos" items="${objetos}" varStatus="status">
				<tr>
					<td><b>${objetos.nombre}</b></td>
					<td>${objetos.descripcion}</td>
					<td>${objetos.efecto}</td>
				</tr>


			</c:forEach>

		</tbody>
	</table>
</body>
</html>