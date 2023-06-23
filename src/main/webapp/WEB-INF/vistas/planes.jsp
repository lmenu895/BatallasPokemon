<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<meta charset="UTF-8">
<link href="css/billetera.css" rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
	crossorigin="anonymous">
<title>Lista de planes</title>
<link rel="icon" type="image/x-icon" href="images/favicon.ico">
</head>
<body class="pokemon">
	
	<div class="header d-flex justify-content-center align-items-center">
         <a href="home"><img class="pokemonLogo" src="images/pokemonLogo.png" alt="pokemonLogo"></a>
     </div>

	<div class="container mt-3">
		<h1 class="texto">  Hola ${usuario.usuario}, que plan queres elegir?</h1>
		<c:if test="${not empty billetera }">
			<h3 class="texto">  Tu saldo es: $${billetera.saldo}</h3> <a class="texto recargar" href="formularioSaldo">Recargar billetera</a>
		</c:if>
		<table class="table table-dark table-borderless table-striped text-center align-middle table-hover mt">
			<thead>
				<tr>
					<th scope="col">Nombre</th>
					<th scope="col">Precio</th>
					<th scope="col">Puntos</th>
					<th scope="col">Multiplicador</th>
					<th scope="col">Beneficios</th>
					<th scope="col"></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="plan" items="${planes}" varStatus="status">
					<tr>
						<td>${plan.nombre}</td>
						<td>$${plan.precio}</td>
						<td>${plan.puntos}</td>
						<td>${plan.multiplicador}</td>
						<td>${plan.beneficios}</td>
						<td>
							<c:if test ="${empty error}">
							  <a class="btn btn-primary" href="asignarplan/${plan.id}" role="button">Pagar</a>
							</c:if>
						</td>
					</tr>
				</c:forEach>

			</tbody>
		</table>

	</div>
		<c:if test="${not empty mensajeExito}">
			<div class="alert alert-success" role="alert">
				<h6>${mensajeExito}</h6>

			</div>
		</c:if>

		<c:if test="${not empty mensajeTienePlan}">
			<div class="alert alert-danger" role="alert">
				<h6>${mensajeTienePlan}</h6>

			</div>
		</c:if>
		
		<c:if test="${not empty mensaje}">
			<div class="alert alert-danger" role="alert">
				<h6>${mensaje} <a href="registroBilletera"> aqui.</a></h6>

			</div>
		</c:if>
		
		<c:if test="${not empty fondoInsuficiente}">
			<div class="alert alert-danger" role="alert">
				<h6>${fondoInsuficiente} <a href="formularioSaldo"> aqui.</a></h6>

			</div>
		</c:if>
	
</body>
</html>
