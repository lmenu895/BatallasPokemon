<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/gachapon-resultado.css"></link>
    <title>Gachapon Resultado</title>
</head>
<body>
    <div class="header">
        <a href="gachapon" class="gachaponLogo" ><img class="gachapon" src="images/togepi.png" alt="gachaLogo"></a>
    </div>

    <div class="resultado">
        <img class="pokeballArriba pokeball animation" src="images/pokeballArriba${monedas}.png" alt="arriba">
        <img class="pokemon animation" src="images/sprites/${pokemon.nombre}/${pokemon.imagenFrente}" alt="pokemon">
        <img class="pokeballAbajo pokeball animation" src="images/pokeballAbajo100.png" alt="abajo">
    </div>
    <h1 class="congrats animation">¡Felicidades, ${pokemon.nombre}  es tu nuevo pokemon!</h1>


    <script  type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
    <script type="text/javascript" src="js/gachapon-resultado.js"></script>
</body>
</html>