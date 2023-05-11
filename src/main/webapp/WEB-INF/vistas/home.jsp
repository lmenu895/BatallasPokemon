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

		<div>
			<div class="parte-usuario">
				<img class="pokeball" src="images/pokeballU.png" alt="pokeball">
				<button class="textUser">1000</button>
				<button class="textUser">USER</button>
			</div>
			<div class="fondo-usuario"></div>
		</div>

	</div>

	<div class="menus">

		<button class="batalla-texto">BATALLA</button>

		<button class="gacha">GACHAPON</button>

		<button class="pokedex-texto">POKEDEX</button>

		<button class="pase">PASE DE BATALLA</button>

	</div>
	<div class="gla">
		<img class="glaceon" src="images/glaceon.png" alt="glaceon">
	</div>
	<div class="tog">
		<img class="togepi" src="images/togepi.png" alt="togepi">
	</div>
	<div class="pok">
		<img class="pokedex" src="images/pokedex.png" alt="pokedex">
	</div>
	<div class="pase">
		<img class="fortnite" src="images/fortnite.png" alt="pase de batalla">
	</div>
	<a href="admin"><button class="textUser admin">Admin</button></a>
	<!-- Placed at the end of the document so the pages load faster -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script>
		window.jQuery
				|| document
						.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')
	</script>
</body>
</html>