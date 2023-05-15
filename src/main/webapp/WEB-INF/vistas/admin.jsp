<%@page import="javax.swing.text.StyledEditorKit.FontFamilyAction"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	errorPage="error.jsp" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Roboto:wght@700&display=swap"
	rel="stylesheet">
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="css/admin.css"></link>
<title>Admin</title>
<link rel="icon" type="image/x-icon" href="images/favicon.ico">
</head>
<body>
	<div>
		<a href="home"><img class="pokemon" alt="pokemon"
			src="images/pokemonLogo.png"></a>
	</div>

	<div class="container">
		<a href="lista-pokemons"><button class="button button-pokemon">VER
				LISTA DE POKEMONS</button></a> <a href="lista-ataques"><button
				class="button button-ataque">VER LISTA DE ATAQUES</button></a> <a
			href="lista-objetos"><button class="button button-ataque">VER
				LISTA DE OBJETOS</button></a>
	</div>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script>
		window.jQuery
				|| document
						.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')
	</script>
</body>
</html>