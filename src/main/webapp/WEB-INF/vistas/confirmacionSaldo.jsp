<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="css/billetera.css" rel="stylesheet">
<title>Confirmacion de saldo</title>
<link rel="icon" type="image/x-icon" href="images/favicon.ico">
</head>
<body class="pokemon">
<div class="container mb-3">
	<div class="header d-flex justify-content-center align-items-center">
	      <a href="home"><img class="pokemonLogo" src="images/pokemonLogo.png" alt="pokemonLogo"></a>
	</div>
		<h1 class="texto">¡Dinero ingresado con éxito!</h1>
		<h2 class="texto">Su nuevo saldo es: ${billetera.saldo} </h2>
		<h2 class="texto">¡Podes usarlo para comprar planes en el pase de batalla! </h2>

	<c:if test="${not empty mensaje}">${mensaje}</c:if>
</div>
</body>
</html>