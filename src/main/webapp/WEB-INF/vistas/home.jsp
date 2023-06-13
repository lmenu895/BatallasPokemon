<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Roboto:wght@700&display=swap"
	rel="stylesheet">
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="css/homeStyles.css"></link>
<title>Home</title>
<link rel="icon" type="image/x-icon" href="images/favicon.ico">
</head>
<body>
	<div class="barra">
		<div>
			<img class="pokemon" alt="pokemon" src="images/pokemonLogo.png">
			<div class="fondo-pokemon"></div>
		</div>
		<div class="user">
			<div class="userProfile">
				<div class="fondo-usuario"></div>
				<img class="pokeball" src="images/pokeballU.png" alt="pokeball">
				<button class="textUser">${usuario.puntos}</button>
				<div class="username">
					<a href="datos-de-usuario" id="usuario" class="textUser">${usuario.usuario}</a>
					<div class="menu-usuario">
						<a href="datos-de-usuario" class="datos-usuario">Mi perfil</a> <a
							href="historial-de-batallas" class="historial-batallas">Historial
							de batallas</a> <a href="lista-pokemons-usuario"
							class="lista-pokemons">Mis pokemons</a> <a href="logout"
							class="logoutButton">Logout</a>
					</div>
				</div>
				<a href="mostrarBilletera">Billetera</a>
			</div>
		</div>
	</div>

	<div class="menus">
		<div
			class="pok<c:if test="${sessionScope.principiante}"> disabled</c:if>">
			<img class="glaceon" src="images/glaceon.png" alt="glaceon">
			<button class="batalla-texto"
				<c:if test="${sessionScope.principiante}">disabled
				title="Pruebe el GACHAPON para obtener sus primeros 3 pokemons y empezar a jugar!!!"</c:if>>BATALLA</button>
		</div>
		<div class="pok">
			<img class="togepi" src="images/togepi.png" alt="togepi">
			<button class="gacha">GACHAPON</button>
		</div>
		<div class="pok">
			<img class="pokedex" src="images/pokedex.png" alt="pokedex">
			<button id="pokedex" class="pokedex-texto">POKEDEX</button>
		</div>
		<div class="pok">
			<img class="fortnite" src="images/fortnite.png" alt="pase de batalla">
			<button class="pase">PASE DE BATALLA</button>
		</div>
	</div>
	<!-- Placed at the end of the document so the pages load faster -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script>
		window.jQuery
				|| document
						.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')
	</script>
	<script type="text/javascript" src="js/home.js"></script>
</body>
</html>