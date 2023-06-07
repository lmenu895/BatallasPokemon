<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/pokedex.css" rel="stylesheet">
<title>Pokedex</title>
<link rel="icon" type="image/x-icon" href="images/favicon.ico">
</head>
<body class="pokemon">
	<div class="header d-flex justify-content-center align-items-center">
            <a href="home"><img class="pokemonLogo" src="images/pokemonLogo.png" alt="pokemonLogo"></a>
        </div>
	<div class="container d-flex">
			<div class="formP">
				<div class="form">
					<h3 class="form-signin-heading texto-pokedex">Pokedex</h3>
					<div class="buscador">
						<input type="text" placeholder="Buscar pokemon"
							class="form-control buscar margin" /><span class="clear"></span>
					</div>
					<div class="form-switch formP">
					  <input id="checkPokedex" class="form-check-input margin" type="checkbox" role="switch" id="flexSwitchCheckDefault">
					  <label class="form-check-label texto-input" for="flexSwitchCheckDefault">Ver pokemons no obtenidos</label>
					</div>
					<div
						class="d-flex align-items-baseline justify-content-around flex-wrap pokemons">
						<c:forEach items="${listaPokemon}" var="pokemon">
							<div
								class="d-flex flex-column align-items-center pokemon-usuario"
								id="${pokemon.nombre}" style="width: 33%;">
								<img alt="${pokemon.nombre}" class="img-fluid imgPokemon mb-2 imagen-pokemon"
									src="images/sprites/${pokemon.nombre}/${pokemon.imagenFrente}">
								<h4 class="nombre-pokemon vBuscado pnombre">${pokemon.nombre}</h4>
								<h4 id="${pokemon.nombre}" class="nombre-pokemon vBuscado pquestion ${pokemon.nombre}">???</h4>
								<h5 class="nombre-pokemon vBuscado tipo">${pokemon.tipo}</h5>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
	</div>


	<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.bundle.min.js"></script>
	<script type="text/javascript" src="js/buscador.js"></script>
	<script type="text/javascript" src="js/elegir-equipo.js"></script>
	<script type="text/javascript" src="js/pokedex.js"></script>
	<script type="text/javascript">
		var pokemonsUsuario = JSON.parse('${pokemonsUsuarioJson}');
		var pokemonsLista = JSON.parse('${pokemonsListaJson}');
	</script>
	<!-- 	<script type="text/javascript" src="js/validation.js"></script> -->
</body>
</html>