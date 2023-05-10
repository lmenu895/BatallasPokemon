<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/progress-bar.css" rel="stylesheet">
<title>Pocket Monster Online Battle!</title>
</head>
<body>
	<div class="container mt-5">
		<div id="usuario">
			<div id="nombrePkmnUsr">${pokemonUsuario.nombre}</div>
			<div class="vida">
				<span id="vidaPkmnUsr"></span><span id="vidaMaximaPkmnUsr"></span><span
					id=estadoUsuario style="padding-left: 20px;"></span>
			</div>
			<div id="progressUsr" class="myProgress">
				<div id="progressBarUsr" class="myBar"></div>
			</div>
			<div id="ataqueUsuario" style="visibility: hidden;">Ataque</div>
			<div id="ataques">
				<c:forEach items="${pokemonUsuario.ataques}" var="ataque"
					varStatus="status">
					<button class='btn btn-success ataques' id="${status.count-1}"
						value="${status.count-1}">${ataque.nombre}</button>
				</c:forEach>
			</div>
		</div>
		<div id="cpu">
			<div id="nombrePkmnCpu">${pokemonCpu.nombre}</div>
			<div>
				<span id="vidaPkmnCpu"></span><span id="vidaMaximaPkmnCpu"></span><span
					id=estadoCpu style="padding-left: 20px;"></span>
			</div>
			<div id="progressCpu" class="myProgress">
				<div id="progressBarCpu" class="myBar"></div>
			</div>
			<div id="ataqueCpu" style="visibility: hidden;">Ataque</div>
		</div>
		<br>
		<button class='btn btn-danger' id="traerAPikachu">Traer a
			Pikachu</button>
		<div class="objeto-pikachu"></div>
	</div>
	<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
	<script src="js/batalla.js" type="text/javascript"></script>
</body>
</html>