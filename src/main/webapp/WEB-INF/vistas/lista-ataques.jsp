<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">
<title>Lista de ataques</title>
<link rel="icon" type="image/x-icon" href="images/favicon.ico">
</head>
<body class="fondo">
	<div class="container">
		<button class="boton-nuevo btn btn-success mt-5">Nuevo
			Ataque</button>
		<table id="tablaAtaques"
			class='table table-hover table-striped mb-5 tableForm'>

			<thead>
				<tr class='text-center table-dark align-middle '>
					<th scope='col'>Nombre Ataque</th>
					<th scope='col'>Acciones</th>

				</tr>
			</thead>
			<tbody>
				<c:forEach items="${listaAtaques}" var="ataque">
					<tr class='text-center align-middle' id="ataque${ataque.id}">
						<!-- items LO QUE RECORRE , var COMO SE VA A LLAMAR CADA ITEM -->
						<td>${ataque.nombre}</td>
						<!-- ${VAR.atributo} -->
						<td><button class="borrar btn btn-danger"
								value="${ataque.id}">Borrar</button>
							<button class="modificar btn btn-info text-light" value="${ataque.id}">Modificar</button></td>

					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="js/lista-ataques.js"></script>
</body>
</html>