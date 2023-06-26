<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
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
<meta charset="ISO-8859-1">
<title>Billetera</title>
<link rel="icon" type="image/x-icon" href="images/favicon.ico">
</head>
<body class="pokemon">
	<div class="header d-flex justify-content-center align-items-center">
         <a href="home"><img class="pokemonLogo" src="images/pokemonLogo.png" alt="pokemonLogo"></a>
     </div>
	<div class="container" style="text-align: center;">
		<h1 class="texto">Billetera generada exitosamente!</h1>
		<h2 class="texto">Su saldo es: ${billetera.saldo} </h2>
	</div>
	
	<script type="text/javascript" src="js/home.js"></script>
</body>
</html>