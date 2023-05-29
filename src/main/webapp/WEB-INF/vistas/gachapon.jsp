<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/gachapon.css"></link>
    <link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css"/>
    <link
      rel="stylesheet"
      type="text/css"
      href="http://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css"
    />
    <link
      rel="stylesheet"
      type="text/css"
      href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick-theme.css"
    />

  <title>Gachapon</title>
</head>
<body>
    <div class="fixed">
        <div class="header">
            <a href="home"><img class="pokemonLogo" src="images/pokemonLogo.png" alt="pokemonLogo"></a>
    
            <h3 class="puntosUser">Tus puntos ${puntos}</h3>
        </div>
    
        <div class="container">
            <div class="center">
                    <img class="pokemon" id="pokeball" alt="pokemon" src="images/Pokebola.png">

                    <img class="pokemon" id="superball" alt="pokemon" src="images/superball.png">

                    <img class="pokemon" id="ultraball" alt="pokemon" src="images/ultraball.png">
  
                    <img class="pokemon" id="masterball" alt="pokemon" src="images/master.png">

              </div>
        </div>
    
        <form:form class="centrar" method="POST" action="gachapon-resultado">
            <input class="boton-para-tirar" type="submit" name="monedas" id="tirar" value=""></input>
        </form:form>
    
        <c:if  test="${not empty error}"><h4 class="text-center">
        <span>${error}</span>
        </h4> <br>
        </c:if>  
    </div>
    
    <script  type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
    <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
    <script type="text/javascript" src="js/gacha.js"></script>
</body>
</html>