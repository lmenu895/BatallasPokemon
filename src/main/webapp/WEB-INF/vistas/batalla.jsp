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
	<div class="container">
		<div id="usuario">
			<div id="nombrePkmnUsr">${pokemonUsuario.nombre}</div>
			<div id="vidaPkmnUsr" class="vida">${pokemonUsuario.vida}</div>
			<div id="progressUsr" class="myProgress">
				<div id="progressBarUsr" class="myBar"></div>
			</div>
			<div id=estadoUsuario></div>
			<br>
			<div id="ataques">
				<c:forEach items="${pokemonUsuario.ataques}" var="ataque" varStatus="status">
					<button class='btn btn-success ataques' id="${status.count-1}">${ataque.nombre}</button>
				</c:forEach>
			</div>
			<br>
		</div>
		<div id="cpu">
			<div id="nombrePkmnCpu">${pokemonCpu.nombre}</div>
			<div id="vidaPkmnCpu">${pokemonCpu.vida}</div>
			<div id="progressCpu" class="myProgress">
				<div id="progressBarCpu" class="myBar"></div>
			</div>
			<div id=estadoCpu></div>
			<div id="ataqueCpu"></div>
		</div>
	</div>
	<script type="text/javascript">
		var pokemonUsuario = JSON.parse('${usuarioString}')
		var pokemonCpu = JSON.parse('${cpuString}')
	</script>
	<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
	<script src="js/batalla.js" type="text/javascript"></script>
</body>
</html>