<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<link href="css/billetera.css" rel="stylesheet">
<meta charset="ISO-8859-1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
<title>Ingresar dinero</title>
<link rel="icon" type="image/x-icon" href="images/favicon.ico">
</head>
<body class="pokemon">
	<div class="header d-flex justify-content-center align-items-center">
         <a href="home"><img class="pokemonLogo" src="images/pokemonLogo.png" alt="pokemonLogo"></a>
     </div>
	<div class="container mt-5">
	<form:form action="procesarSaldo" method="POST" modelAttribute="billetera" style="text-align:center;">
		<h1 class="texto">Recargar billetera</h1>	
		<label class="texto label">Ingrese el monto</label>
		<br>
		<input class="numero" type="number" name="monto" id="saldo"/>
		<div class="d-grid gap-2 col-3 mx-auto mt-3">
		<button type="submit" class="btn-primary mb-5 button">Ingresar</button>
		</div>
	</form:form>
	<c:if test="${not empty error}">
			<div class="alert alert-danger" role="alert">
				<h6>${error}</h6>

			</div>
		</c:if>

	</div>

</body>
</html></html>