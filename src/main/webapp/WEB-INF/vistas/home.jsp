<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
	<head>
		<link rel="preconnect" href="https://fonts.googleapis.com">
	    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@700&display=swap" rel="stylesheet">
	    <meta charset="UTF-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	    <link rel="stylesheet" href="<c:url value ="css/home.css"/>"></link>
	    <title>Home</title>
	</head>
	<body>
		<div class="barra">
	        <div>
	            <img class="gengar" alt="gengar"  src="<c:url value="images/gengar.png"/>">
	            
	            <div class="fondo-gengar"></div>
	        </div>
	
	        <div>
	            <div class="parte-usuario">
	                <img class="pokeball" src="images/pokeballU.png" alt="pokeball">
	                <h3 class="textUser">1000</h3>
	                <h3 class="textUser">USER</h3>
	            </div>
	            <div class="fondo-usuario"></div>
	        </div>

    	</div>
   
    <div class="menus">
        <div class="menu menu-batalla">

            <button>BATALLA</button>

        </div>

        <div class="menu menu-gacha">

            <button>GACHAPON</button>

        </div>

        <div class="menu menu-pokedex">

            <button>POKEDEX</button>

        </div>

        <div class="menu menu-pase">

            <button>PASE DE BATALLA</button>

        </div>
    </div>
    <div class="gla"> <img class="glaceon" src="images/glaceon.png" alt="glaceon"></div>
    <div class="tog"><img class="togepi" src="images/togepi.png" alt="togepi"></div>    
    <div class="pok"><img class="pokedex" src="images/pokedex.png" alt="pokedex"></div>
    <div class="pase"><img class="fortnite" src="images/fortnite.png" alt="pase de batalla"></div>
    <button class="textUser admin">Admin</button>
		<!-- Placed at the end of the document so the pages load faster -->
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js" ></script>
		<script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
	</body>
</html>