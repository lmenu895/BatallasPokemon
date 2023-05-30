<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Generar billetera</title>
<!-- Bootstrap core CSS 
		    <link href="css/bootstrap.min.css" rel="stylesheet" >-->
<!-- Bootstrap theme
		    <link href="css/bootstrap-theme.min.css" rel="stylesheet"> -->
<link href="css/estilos.css" rel="stylesheet">
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
	<body>
	
		<main class="container"> <!-- Clase de Bootstrap. Hace que los elementos no lleguen hasta el borde -->
			
			<h1 class="mt-3">Generar billetera</h1>
			
				<div class="col-md-6">			
		   Usted esta por generar una billetera virtual <br>
		   <br>
		    �Desea continuar?
		  </div>
		  <div class="col-12" style="margin-top:20px;">
		  	<a href="javascript:history.back()" role="button" class="btn btn-dark control-label mb-3">Cancelar</a>
		    <a role="button" class="btn btn-primary control-label mb-3" href="procesarRegistroBilletera">Generar</a>
		   </div>
  	
  		<%--Bloque que es visible si el elemento error no está vacío	--%>
				<%--Bloque que es visible si el elemento error no está vacío	--%>

		<c:if test="${not empty error}">
			<div class="alert alert-danger" role="alert">
				<h6>${error}</h6>

			</div>
		</c:if>
	</main>
	</body>
</html>