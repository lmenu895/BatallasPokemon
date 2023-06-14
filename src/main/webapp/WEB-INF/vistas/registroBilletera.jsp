<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Generar billetera</title>
<link rel="icon" type="image/x-icon" href="images/favicon.ico">
<!-- Bootstrap core CSS 
		    <link href="css/bootstrap.min.css" rel="stylesheet" >-->
<!-- Bootstrap theme
		    <link href="css/bootstrap-theme.min.css" rel="stylesheet"> -->
<link href="css/billetera.css" rel="stylesheet">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
	crossorigin="anonymous"></script>
</head>
	<body class="pokemon">
		<div class="header d-flex justify-content-center align-items-center">
            <a href="home"><img class="pokemonLogo" src="images/pokemonLogo.png" alt="pokemonLogo"></a>
        </div>
		<main class="container"> <!-- Clase de Bootstrap. Hace que los elementos no lleguen hasta el borde -->
			
			<h1 class="mt-3 texto">Generar billetera</h1>
			
			<br>		
			<h3 class="texto">Usted esta por generar una billetera virtual </h3>
			<br>
			<h3 class="texto">¿Desea continuar?</h3>
		  	<div class="col-12" style="margin-top:20px;">
			  <form:form action="procesarRegistroBilletera" method="GET" class="form">
			  	<a href="javascript:history.back()" role="button" class="btn btn-lg btn-primary btn-block btn-cancelar">Cancelar</a>
						<button id="btn-registrarme"
						class="btn btn-lg btn-primary btn-block" Type="Submit">Generar</button>
			   </form:form>
		   </div>

		<c:if test="${not empty error}">
			<div class="alert alert-danger error" role="alert">
				<h6>${error}</h6>

			</div>
		</c:if>
	</main>
	</body>
</html>