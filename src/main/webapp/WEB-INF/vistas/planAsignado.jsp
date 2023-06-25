<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Roboto:wght@700&display=swap" rel="stylesheet">
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="css/homeStyles.css">
	<title>Plan asignado</title>
	<link rel="icon" type="image/x-icon" href="images/favicon.ico">
	<style>
		body {
			background-image: url("../images/fondo-plan.jpg");
			background-repeat: repeat-y;
			background-size: 100%;
			font-family: 'Roboto', sans-serif;
			overflow: hidden;
			
		}
		
		.container {
			max-width: 600px;
			margin: 0 auto;
			padding: 20px;
		}
		
		.text-center {
			text-align: center;
		}
		
		.texto {
			font-size: 24px;
			font-weight: 700;
			color: white;
			margin-top: 50px;
		}
		
		.btn {
			display: inline-block;
			padding: 10px 20px;
			background-color: #333;
			color: #fff;
			text-decoration: none;
			border-radius: 4px;
			transition: background-color 0.3s ease;
		}
		
		.btn:hover {
			background-color: #555;
		}
	</style>
</head>
<body>
	<div class="container text-center">
		<h1 class="texto">¡Ya puedes disfrutar de los beneficios de tu plan!</h1>
		<a class="btn" href="http://localhost:8080/batallas-pokemons/home" role="button">Volver</a>
	</div>
</body>
</html>